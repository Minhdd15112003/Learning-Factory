# Terraform & HCL (HashiCorp Configuration Language) Cơ Bản

## 1. Cú pháp HCL (HashiCorp Configuration Language)

Terraform sử dụng ngôn ngữ HCL để khai báo hạ tầng. Cú pháp của HCL rất đơn giản và dễ đọc. Nó dựa trên các **Blocks**, **Arguments**, và **Expressions**.

Cấu trúc chung:
```hcl
<BLOCK_TYPE> "<BLOCK_LABEL_1>" "<BLOCK_LABEL_2>" {
  # Block body
  <IDENTIFIER> = <EXPRESSION> # Argument
}
```

## 2. Các Blocks Quan Trọng Nhất

### a) `terraform` block
Dùng để cấu hình bản thân Terraform (yêu cầu phiên bản, khai báo providers cần dùng, cấu hình backend lưu state).
```hcl
terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
  }
}
```

### b) `provider` block
Nơi bạn cấu hình plugin để Terraform giao tiếp với các cloud provider (GCP, AWS, Azure...).
```hcl
provider "google" {
  project = "your-project-id"
  region  = "asia-southeast1" # Singapore
}
```

### c) `resource` block
Đây là "trái tim" của Terraform. Dùng để tạo các tài nguyên cụ thể (VM, VPC, Bucket...).
- **Block type:** `resource`
- **Block label 1 (Resource Type):** Tên tài nguyên theo định nghĩa của provider (vd: `google_storage_bucket`).
- **Block label 2 (Local Name):** Tên định danh (ID) để bạn gọi/tham chiếu tài nguyên này ở nơi khác trong code Terraform. Không ảnh hưởng đến tên thật trên GCP.

```hcl
resource "google_storage_bucket" "my_bucket" {
  name     = "unique-bucket-name-12345"
  location = "ASIA"
}
```
*-> Để gọi đến bucket này ở nơi khác, ta dùng cú pháp: `google_storage_bucket.my_bucket.name`*

## 3. Vòng Đời Lệnh Terraform (Terraform Workflow)

Quy trình chuẩn khi làm việc với Terraform luôn gồm 4 bước:

1. **`terraform init`**: Khởi tạo thư mục làm việc. Tải về các provider plugins (như plugin cho Google Cloud) được định nghĩa trong file.
2. **`terraform plan`**: (Dry run) - So sánh code của bạn với state hiện tại và cho bạn biết **chính xác những gì sẽ được thay đổi** (tạo mới `+`, sửa `~`, xóa `-`).
3. **`terraform apply`**: Thực thi những thay đổi đã thấy ở bước `plan`. Nó sẽ cập nhật file `terraform.tfstate`.
4. **`terraform destroy`**: Phá bỏ (xóa) toàn bộ các tài nguyên đang được quản lý bởi Terraform trong thư mục đó.
