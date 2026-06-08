# (C) Claude generated this file
# 1. Khai báo Provider - nói cho Terraform biết mình dùng Google Cloud
provider "google" {
  project = "learning-498713" # Thay bằng ID project của bạn
  region  = "asia-southeast1" # Singapore - cho gần Việt Nam
}

# 2. Khai báo Resource - cái mình muốn tạo
resource "google_storage_bucket" "my_first_bucket" {
  name          = "my-first-terraform-bucket-12345" # Tên bucket phải toàn cầu duy nhất, thêm số ngẫu nhiên cho chắc
  location      = "ASIA-SOUTHEAST1"
  force_destroy = true # Cho phép xóa bucket ngay cả khi có file bên trong (dùng để học cho tiện)

  public_access_prevention = "enforced" # Chặn truy cập công khai cho an toàn
}
