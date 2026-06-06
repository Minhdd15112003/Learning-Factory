# Cấu hình kết nối và yêu cầu phiên bản

terraform {
  required_version = "~> 1.5"
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "~> 3.6"
    }
  }
}

# Provider "google" - sử dụng ADC (Application Default Credentials)
# -> Terraform sẽ tự động đọc credentials từ lệnh gcloud auth application-default login
provider "google" {
  region = "asia-southeast1"  # Singapore
}
