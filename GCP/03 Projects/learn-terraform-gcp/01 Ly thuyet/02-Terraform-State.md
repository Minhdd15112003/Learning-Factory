---
status: Partial
tags: [terraform, state, backend, review]
sr-due: 2026-06-11
sr-interval: 1
sr-ease: 250
---

# Terraform State

> Tham chiếu: [[01-Ban-chat-cua-Terraform]] | [[COMMANDS]]

---

## 1. Terraform State là gì?

File `terraform.tfstate` là "cuốn sổ cái" (ledger) của [[Terraform]]. Nó lưu trữ ánh xạ (mapping) giữa những gì bạn mô tả trong code `.tf` và những tài nguyên đang thực sự tồn tại trên [[GCP]].

**Tại sao Terraform cần nó?**

[[Terraform]] không dựa vào tên resource để nhận biết. Khi bạn chạy `terraform apply`, Terraform không hỏi GCP "bucket tên này có tồn tại không?" — nó đọc file state để biết mình đã từng tạo gì, với ID gì, ở đâu. Nếu không có state, Terraform là một công cụ mù: nó không biết hiện trạng thế giới là gì, nên nó sẽ cố gắng tạo mới tất cả.

**Cấu trúc của file state (đơn giản hóa):**

File `terraform.tfstate` là một file JSON. Nó chứa:
- Tên logical của resource trong code (ví dụ: `google_storage_bucket.my_bucket`)
- ID thực tế của tài nguyên đó trên GCP (ví dụ: tên bucket trên GCS)
- Các thuộc tính hiện tại của tài nguyên đó

```json
{
  "resources": [
    {
      "type": "google_storage_bucket",
      "name": "my_bucket",
      "instances": [
        {
          "attributes": {
            "id": "ten-bucket-thuc-te-tren-gcs",
            "location": "US"
          }
        }
      ]
    }
  ]
}
```

---

## 2. Thí nghiệm "Mất trí nhớ": Điều gì xảy ra khi mất State?

**Kịch bản:** Bạn đổi tên hoặc xóa file `terraform.tfstate` rồi chạy `terraform apply` lại.

**Kết quả:** Lỗi `Conflict 409` từ phía GCP.

**Tại sao?**

Terraform đọc State, thấy trống rỗng (không có resource nào), nên nó kết luận: "Chưa có gì hết, tôi cần tạo mới tất cả." Nó gửi lệnh tạo bucket đến GCP. GCP nhận ra bucket với tên đó đã tồn tại (do lần trước tạo rồi) và từ chối với lỗi `409 Conflict` — "Tên này đã được đăng ký, không thể tạo trùng."

Đây là misconception phổ biến: Terraform không dựa vào GCP để kiểm tra hiện trạng. Nó tin tưởng State hơn. Nếu State nói "chưa có", Terraform tin ngay.

**Cách cứu:** Lệnh `[[terraform import]]`

```bash
terraform import google_storage_bucket.my_bucket ten-bucket-thuc-te-tren-gcs
```

Lệnh này nói với Terraform: "Tài nguyên `google_storage_bucket.my_bucket` trong code của tao đã tồn tại trên GCP với ID là `ten-bucket-thuc-te-tren-gcs`. Hãy đọc thông tin của nó về và ghi vào State."

Sau khi import, State đã có thông tin, và `terraform plan` sẽ thấy trạng thái mong muốn khớp với thực tế — không còn tạo trùng nữa.

---

## 3. Local State và những vấn đề của nó

Mặc định, Terraform lưu State ở local: file `terraform.tfstate` nằm ngay trong thư mục chứa code `.tf` của bạn.

**Nhược điểm của Local State:**

| Vấn đề | Giải thích |
|---|---|
| Khó làm nhóm | Nơi có 2 người cùng apply, ai giữ file state? Người còn lại làm việc trên state cũ. |
| Dễ mất | Máy hư, xóa nhầm, hay quên commit — State biến mất, [[Terraform]] mất trí nhớ. |
| Chứa dữ liệu nhạy cảm | File JSON có thể chứa password, connection string, private key của các tài nguyên. Nếu commit lên Git, dữ liệu này bị lộ. |

---

## 4. Remote Backend với [[GCS]]

Giải pháp là chuyển State ra khỏi máy cá nhân và lưu trên một Remote Backend. Với [[GCP]], backend phổ biến nhất là [[GCS]] (Google Cloud Storage).

**Cách cấu hình backend GCS (HCL):**

Trong file `main.tf` hoặc một file riêng `backend.tf`, thêm khối `terraform { backend "gcs" { ... } }`:

```hcl
terraform {
  # Khai báo Provider phiên bản
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
  }

  # Cấu hình Backend: Lưu State trên GCS thay vì local
  backend "gcs" {
    # Tên GCS bucket sẽ chứa file State
    # Bucket này phải được tạo tay TRƯỚC khi chạy terraform init
    bucket = "ten-bucket-chua-state-cua-ban"

    # Đường dẫn thư mục bên trong bucket
    # Giúp tổ chức nếu nhiều project dùng chung 1 bucket
    prefix = "terraform/state"
  }
}
```

**Lưu ý quan trọng:** Bucket dùng để chứa State (`ten-bucket-chua-state-cua-ban`) KHÔNG được quản lý bởi Terraform (không được khai báo như 1 resource trong code). Nó phải được tạo bằng tay (hoặc bằng một script riêng) trước khi chạy `terraform init`. Nếu để Terraform tự tạo bucket chứa chính State của nó, sẽ có vấn đề "con gà - quả trứng": Terraform cần State để biết nó đã tạo bucket chứa-State chưa, nhưng nó chưa có State...

Sau khi cấu hình, chạy `terraform init` để Terraform kết nối và migrate State lên GCS.

---

## 5. State Locking: Chống Race Condition

**Vấn đề:** Trong team, A và B cùng chạy `terraform apply` cùng lúc. Cả hai đều đọc State, tính toán plan, rồi cùng ghi State với những thay đổi của riêng mình. Kết quả: State bị corrupt, tài nguyên trên GCP không khớp với State.

**Giải pháp — State Locking:**

Khi sử dụng Remote Backend (GCS), [[Terraform]] tự động kích hoạt khóa State (lock) ngay khi bắt đầu một lệnh thay đổi (`plan`, `apply`, `destroy`). Cơ chế này được GCS hỗ trợ thông qua `object locking` (GCS backend dùng chính object trên bucket làm lock).

Quá trình:
1. A chạy `terraform apply`. Terraform ghi một file lock vào GCS (`default.tflock`).
2. B cũng chạy `terraform apply`. Terraform kiểm tra GCS, thấy file lock, báo lỗi: "Error acquiring the state lock. Another operation is in progress."
3. A hoàn thành. Terraform xóa file lock.
4. B thử lại và thành công.

Kết quả: Chỉ có 1 người được ghi State tại một thời điểm. Race Condition không xảy ra.

---

## 6. Tại sao GCS tốt hơn Git để chứa State?

| Tiêu chí | Git (sai lầm) | GCS Remote Backend (đúng) |
|---|---|---|
| Bảo mật | File `.tfstate` JSON bị push lên repo, lộ API key, password | State nằm trên GCS, truy cập kiểm soát bởi [[IAM]] |
| Locking | Không có — 2 người merge conflict trên State | Có cơ chế lock tích hợp sẵn |
| Đồng bộ | Thủ công — phải commit, push, pull | Tự động — mỗi lệnh terraform đều đọc/ghi lên GCS trực tiếp |
| Lịch sử phiên bản | Dùng Git commits (không phù hợp) | GCS hỗ trợ Object Versioning (giữ lại các phiên bản State cũ) |

---

## Câu hỏi tự kiểm (Spaced Repetition)

1. Nếu bạn có một GCS bucket đã tồn tại trên GCP nhưng file `terraform.tfstate` không có thông tin gì về nó, điều gì sẽ xảy ra khi bạn chạy `terraform apply`? Và bạn sẽ sửa như thế nào?

2. Khối `backend "gcs"` được khai báo bên trong khối `terraform {}`. Theo bạn, khi nào Terraform đọc cấu hình này — lúc chạy `terraform plan` hay lúc chạy `terraform init`? Tại sao?

3. State Locking giải quyết Race Condition bằng cách nào cụ thể? File lock được ghi ở đâu, và nó bị xóa khi nào?

---

> **Trạng thái hiện tại:** `Partial` — Cần pass Feynman check ở mức cơ chế (mechanism-level) về cấu hình backend thực tế: tại sao phải tạo bucket chứa State bằng tay, `terraform init` làm gì với khối `backend "gcs"`, và GCS lock hoạt động như thế nào. Khi giải thích được 3 điều này mà không cần xem lại note, trạng thái sẽ được nâng lên `Understood`.
