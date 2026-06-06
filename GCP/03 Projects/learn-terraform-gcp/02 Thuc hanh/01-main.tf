# ============================================================
# Resource 1: Random ID suffix (để tạo tên bucket duy nhất)
# ============================================================
resource "random_id" "bucket_suffix" {
  byte_length = 4
}

# ============================================================
# Resource 2: Google Cloud Storage Bucket
# ============================================================
resource "google_storage_bucket" "my_first_bucket" {
  # Kết hợp tên với random suffix để đảm bảo tên không bị trùng
  name     = "learn-terraform-gcp-${random_id.bucket_suffix.hex}"
  location = "ASIA"  # Multi-region: ASIA

  # Tuỳ chọn: tắt uniform bucket-level access (mặc định là true)
  # uniform_bucket_level_access = true
}

# Output để hiển thị tên bucket sau khi apply
output "bucket_name" {
  value = google_storage_bucket.my_first_bucket.name
}
