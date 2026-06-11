---
status: Exposed
tags: [gcp, iam, review]
sr-due: 2026-06-11
sr-interval: 1
sr-ease: 250
---

# IAM Role vs IAM Policy trên GCP

> Ghi chú lý thuyết — Claude viết sau khi khai thác khái niệm trong đối thoại.
> Trạng thái: `Exposed` — cần Feynman check để nâng lên `Partial` hoặc `Understood`.

---

## 1. IAM Role — "Được làm những gì?"

[[IAM Role]] là một tập hợp các **permissions** (quyền truy cập cụ thể vào các API/resource).

Role tự nó KHÔNG cấp quyền cho bất kỳ ai. Nó chỉ là một "bản mô tả quyền". Quyền chỉ có hiệu lực khi Role được gán (bind) vào một chủ thể cụ thể thông qua một [[IAM Policy]].

### Ba loại Role trong GCP

| Loại | Mô tả | Ví dụ |
|---|---|---|
| **Basic Role** (Cơ bản) | Phạm vi rộng, áp dụng toàn bộ project. Tồn tại trước IAM. | `roles/owner`, `roles/editor`, `roles/viewer` |
| **Predefined Role** (Định sẵn) | Tạo bởi Google, phạm vi theo từng service. Luôn được cập nhật khi service thêm API mới. | `roles/storage.admin`, `roles/compute.instanceAdmin` |
| **Custom Role** (Tùy chỉnh) | Do người dùng tự tạo, ghép các permissions lẻ theo nhu cầu tổ chức. | `projects/my-project/roles/myCustomRole` |

**Nguyên tắc least privilege:** Luôn chọn Role có phạm vi nhỏ nhất đáp ứng được yêu cầu, tránh dùng Basic Role trong môi trường production.

---

## 2. IAM Policy — "Ai được giữ Role nào, trên Resource nào?"

[[IAM Policy]] (chính xác là **IAM allow policy**) là một tài liệu JSON được gán vào một resource. Nó chứa một danh sách các **bindings**, mỗi binding có dạng:

```json
{
  "bindings": [
    {
      "role": "roles/storage.admin",
      "members": [
        "user:alice@example.com",
        "serviceAccount:my-sa@my-project.iam.gserviceaccount.com",
        "group:devs@example.com"
      ]
    }
  ]
}
```

**Công thức ghi nhớ:**

```
Policy = { (Member + Role) } áp lên một Resource
```

- **Member / Principal**: user, serviceAccount, group, domain, allUsers, allAuthenticatedUsers.
- **Role**: bất kỳ Role nào (Basic, Predefined, hoặc Custom).
- **Resource**: có thể là Organization, Folder, Project, hoặc resource cụ thể (bucket, VM...).

---

## 3. So sánh nhanh

| Khía cạnh | [[IAM Role]] | [[IAM Policy]] |
|---|---|---|
| Chứa gì? | Danh sách permissions | Danh sách bindings (Member + Role) |
| Trả lời câu hỏi | "Được làm gì?" | "Ai được làm gì, ở đâu?" |
| Gắn vào Resource? | Không | Có — Policy gắn trực tiếp vào resource |
| Tự nó có hiệu lực? | Không | Có — hiệu lực ngay khi được set |

---

## 4. Kế thừa theo Resource Hierarchy

[[Resource Hierarchy]] trong GCP có cấu trúc phân cấp:

```
Organization
  Folder
    Project
      Resource (VM, Bucket, ...)
```

**Quy tắc kế thừa:** Policy ở cấp cha được **kế thừa xuống** tất cả cấp con. Policy ở cấp con KHÔNG thể thu hẹp quyền đã cấp ở cấp cha.

Ví dụ: nếu `alice@example.com` được gán `roles/viewer` tại Organization, thì Alice tự động có quyền viewer trên mọi Project, mọi Folder, mọi resource bên trong — dù Project đó có set Policy riêng hay không.

**Hệ quả quan trọng:** Người có quyền quản trị cấp cao hơn (Organization Admin) có thể leo thang quyền xuống bất kỳ resource nào. Đây là lý do cấu hình [[IAM]] ở cấp Organization cần được kiểm soát chặt.

---

## 5. Ví dụ minh họa: gán quyền trên một Cloud Storage Bucket

Tình huống: cho phép `bob@example.com` quản trị một bucket cụ thể, không cấp quyền ra ngoài bucket đó.

**Bước 1 — Chọn Role:** `roles/storage.admin` (Predefined Role) — gồm các permissions như `storage.objects.create`, `storage.objects.delete`, `storage.buckets.update`...

**Bước 2 — Tạo binding trong Policy của bucket:**

```json
{
  "bindings": [
    {
      "role": "roles/storage.admin",
      "members": ["user:bob@example.com"]
    }
  ]
}
```

**Kết quả:** Bob có toàn quyền trên bucket này. Bob KHÔNG có quyền trên các bucket khác trong cùng Project (vì Policy chỉ gắn ở cấp bucket, không phải Project).

**Lệnh gcloud tương đương:**

```bash
gcloud storage buckets add-iam-policy-binding gs://my-bucket \
  --member="user:bob@example.com" \
  --role="roles/storage.admin"
```

---

## 6. Câu hỏi tự kiểm (Socratic)

1. Nếu một [[Service Account]] được gán `roles/compute.instanceAdmin` ở cấp **Project**, nhưng một VM cụ thể trong project đó lại có Policy riêng gán `roles/compute.viewer` cho cùng service account đó — service account này có thể start/stop VM đó không? Tại sao?

2. Tại sao `roles/editor` (Basic Role) lại được coi là nguy hiểm trong môi trường production, dù nó "tiện lợi" hơn Predefined Role? Hãy nghĩ về mặt principle of least privilege.

3. Nếu bạn cần cấp quyền cho một nhóm 50 developer cùng truy cập vào một tập hợp bucket, bạn nên dùng Member loại nào trong Policy binding? Tại sao không nên liệt kê 50 user một?

---

> **Note to Claude:** Status stays `Exposed` until the user passes a Feynman check (mechanism-level explanation of the Role/Policy distinction and inheritance). Grade with the SR algorithm in `CLAUDE.md`; update frontmatter after the check.
