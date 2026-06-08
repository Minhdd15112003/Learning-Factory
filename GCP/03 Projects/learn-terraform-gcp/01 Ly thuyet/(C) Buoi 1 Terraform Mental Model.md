# (C) Buổi 1: Mental Model của Terraform

> **Tóm tắt bằng 1 câu:** Terraform là một công cụ dùng để "khai báo" (declare) hạ tầng mong muốn dưới dạng code, sau đó nó tự động biến code đó thành hiện thực (provision) và lưu lại "bản snapshot" của hạ tầng gọi là [[State]].

## 1. Tư duy khác biệt: Imperative vs Declarative

Trước Terraform, con người quản lý hạ tầng theo cách **Imperative** — nghĩa là bạn PHẢI nói CHO MÁY BIẾT *CÁCH LÀM* từng bước một:

| Imperative (truyền thống) | Declarative (Terraform) |
|---------------------------|------------------------|
| "Tạo VPC network, sau đó tạo subnet, sau đó gắn firewall rule" | "Tôi muốn một VPC network với các thông số X, Y, Z" |
| Bạn là **người ra lệnh** (commander) — phải nhớ từng step | Bạn là **người mô tả** (describers) — Terraform tự tìm cách làm |
| Ví dụ: chạy `gcloud compute networks create vpc-1` rồi `gcloud compute networks subnets create` | Ví dụ: viết file `.tf` khai báo `resource "google_compute_network" "vpc_1" {}` |

💡 **Sự khác biệt then chốt:** Với imperative, bạn phải biết *thứ tự* các bước. Với declarative, bạn chỉ cần biết *mục tiêu cuối cùng* là gì.

> **Ẩn dụ (Analogy):** Imperative giống như chỉ đường từng khúc cho một người lái xe: "rẽ trái ở ngã tư thứ 3, đi thẳng 200m, rẽ phải..." Declarative giống như đưa cho họ một tấm bản đồ có đánh dấu điểm đến — họ tự tìm đường.

## 2. Vòng đời một resource trong Terraform

Tưởng tượng bạn là một kiến trúc sư. Bạn muốn xây một căn nhà:

```
Bước 1: Thiết kế bản vẽ (Code .tf)
         ↓
Bước 2: Trình bày ý tưởng (terraform plan — xem trước)
         ↓
Bước 3: Xây dựng (terraform apply — thi công)
         ↓
Bước 4: Lưu sổ đỏ (State file — bản ghi chính thức)
```

Vai trò của từng bước:
| Bước | Command | Ý nghĩa |
|------|---------|---------|
| Thiết kế | Viết `.tf` file | Bạn mô tả resource và cấu hình của nó |
| Trình bày | `terraform plan` | Terraform đọc code, so sánh với State, cho bạn xem NHỮNG GÌ NÓ SẮP LÀM (không làm thật) |
| Xây dựng | `terraform apply` | Terraform gọi API (GCP, AWS, ...) để tạo/sửa/xóa resource thật |
| Lưu sổ đỏ | `terraform.tfstate` | Terraform ghi lại trạng thái thực tế của hạ tầng vào file này |

### 🧠 Mối quan hệ 3 chiều

Hãy hiểu thật kỹ mối quan hệ này — nó là gốc rễ của 90% lỗi bạn sẽ gặp:

```
┌──────────────────────┐          ┌──────────────────────┐
│   Code (.tf files)   │◄────────►│    GCP API (Cloud)   │
│  "Cái tôi muốn"      │  apply   │  "Cái đang chạy"     │
└──────────┬───────────┘          └──────────┬───────────┘
           │                                  │
           │          compare & sync          │
           │                                  │
           ▼          ┌──────────┐            ▼
                      │  State   │
                      │ (.tfstate)│
                      │ "Cái tôi │
                      │  biết"   │
                      └──────────┘
```

- **Code → State:** Khi bạn chạy `terraform apply`, Terraform đọc code và ghi vào State (liệt kê resource ID, thuộc tính).
- **State → GCP API:** Terraform dùng State để biết resource nào cần tạo (mới), cần sửa (thay đổi), hay cần xóa (trong code không còn nữa).
- **State ↔ GCP (thực tế):** Khi bạn chạy `terraform plan`, Terraform refresh State với API và so sánh.

> ⚡ **Điểm dễ nhầm lẫn:** Nếu ai đó vào GCP Console và xóa một resource bằng tay (click chuột), Terraform KHÔNG BIẾT. Code vẫn còn resource đó, State vẫn còn record, nhưng resource thật đã biến mất. Đây là *drift* (trôi dạt). Terraform sẽ bối rối và bạn sẽ phải dùng lệnh `terraform refresh` hoặc `import` để giải quyết.

[[tu tuong state la su that va tai day la cach xu ly drift]]

## 3. Provider — Bridge giữa Terraform và GCP

Terraform là một công cụ **đa nền tảng** (multi-cloud). Giao tiếp với từng cloud provider (GCP, AWS, Azure) thông qua một **Plugin** gọi là **Provider**.

```
┌──────────────────────────────────────────────────┐
│                   Terraform Core                  │
│  (Engine — biết cách đọc .tf, quản lý State)      │
└────┬──────────────┬──────────────┬───────────────┘
     │              │              │
     ▼              ▼              ▼
┌──────────┐  ┌──────────┐  ┌──────────┐
│ GCP      │  │ AWS      │  │ Azure    │
│ Provider │  │ Provider │  │ Provider │
└────┬─────┘  └──────────┘  └──────────┘
     │
     ▼
┌──────────────┐
│ GCP API      │
│ (create VPC, │
│  VM, Bucket)│
└──────────────┘
```

- **Provider = 1 plugin** giúp Terraform biết *cách gọi API của GCP*.
- Mỗi Resource của GCP đều nằm trong một Provider và có dạng:
  ```
  resource "google_PROVIDER_SERVICE" "TÊN_RIÊNG" {
    ...
  }
  ```
  Ví dụ: `resource "google_storage_bucket" "my_bucket" {}`
- Provider lấy credentials của bạn (thông qua `gcloud auth`, hoặc service account key) để gọi GCP API.

> **Câu hỏi dẫn dắt (Socratic check):** Nếu Terraform chỉ là một cái máy declarative, và Provider là plugin cho GCP — vậy theo bạn, khi bạn cần thêm **một resource về DNS** (cloud DNS zone) thì bạn sẽ làm gì? Có phải cài thêm Provider nào không?

## 4. Hai file bắt buộc cho mọi dự án Terraform

Trên thực tế, một dự án Terraform tối thiểu cần **2 file**:

### `main.tf` — Code của bạn (Resources)
```hcl
# Khai báo resource GCP
resource "google_storage_bucket" "data_lake" {
  name          = "my-gcp-bucket-12345"
  location      = "US"
  storage_class = "STANDARD"
}
```

### `provider.tf` — Cấu hình Provider (hoặc khai báo trong `terraform {}` block)
```hcl
terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
  }
}

provider "google" {
  project = "my-project-id"
  region  = "us-central1"
}
```

> Bạn cũng có thể gộp 2 file này làm 1, nhưng **best practice** là tách riêng cho dễ quản lý, theo dõi.

## 5. Nguyên lý "State is the single source of truth"

Đây là câu cửa miệng của bất kỳ Terraform developer nào:

> **State là một file JSON (terraform.tfstate) chứa ID của tất cả resource bạn đã tạo, kèm thuộc tính của chúng.**

Nếu bạn mất file này, Terraform sẽ "quên" những gì nó đã tạo. Hậu quả:
- Nếu bạn chạy `terraform destroy`, nó... không destroy được gì (vì không biết có gì để destroy).
- Nếu bạn chạy `terraform apply` lại, nó cố gắng tạo lại resource mới (trùng tên → crash).
- Giải pháp: Lưu State ở xa (remote state) trên GCS hoặc Terraform Cloud.

> **Nhưng đừng lo.** Với buổi học hôm nay, chúng ta bắt đầu với local state (file nằm ngay trong thư mục). Đến bài sau, mới chuyển sang remote state.

---

## ✅ Checkpoint: Bạn hiểu đến đâu rồi?

Trước khi sang phần Thực hành, hãy tự kiểm tra nhanh:

1. **Imperative vs Declarative:** Khi bạn nói "Tôi muốn có 1 VM, loại e2-medium, chạy Ubuntu" — đây là imperative hay declarative?
2. **Thứ tự thực hiện:** Cái nào diễn ra trước, `terraform apply` hay `terraform init`?
3. **Provider:** Provider của GCP tên là gì? Cú pháp resource cho một GCS Bucket là gì?
4. **State:** Ai tạo ra file `terraform.tfstate`? (đáp án có trong hình trên)

*(Trả lời các câu hỏi này bằng tiếng Việt trước khi chuyển sang thực hành nhé!)*

---

> **Wiki-links:** [[IaC]] | [[HCL]] | [[State]] | [[GCP Provider]] | [[Terraform Core vs Provider]]
