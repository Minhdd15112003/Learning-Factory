# Mục tiêu — Terraform-GCP

## Mục tiêu tối thượng
Thành thạo **Infrastructure as Code với [[Terraform]] trên [[GCP]]**: đọc hiểu HCL, tự viết `.tf`, triển khai hạ tầng thật trên GCP, và xử lý được lỗi khi `apply`.

## Vị trí hiện tại
- Stage 1 đang làm. Đã hiểu [[Declarative]] vs [[Imperative]], deploy được [[Cloud Storage]] bucket đầu tiên, xử lý xong lỗi Organization Policy chặn [[Uniform Bucket-Level Access]].
- Đang củng cố: [[Terraform State]] (local vs remote, state locking).

## Lộ trình thô (4 stage — tinh chỉnh sau mỗi tuần)

### Stage 1 — Nền tảng
- [x] [[Declarative]] vs [[Imperative]]
- [x] Bucket đầu tiên qua `terraform apply`
- [ ] [[Terraform State]]: local vs [[GCS backend]], state locking
- [ ] Variable, output, data source

### Stage 2 — Cấu trúc code
- [ ] Module hoá
- [ ] Quản lý nhiều môi trường (workspace / thư mục)
- [ ] Remote state + locking trên GCS

### Stage 3 — Hạ tầng thật
- [ ] VPC, subnet, firewall bằng Terraform
- [ ] Compute Engine / GKE cơ bản
- [ ] IAM, service account bằng code

### Stage 4 — Vận hành (stretch)
- [ ] CI cho Terraform (chạy `plan` trên pull request)
- [ ] Xử lý drift, `terraform import` resource có sẵn
- [ ] Kỷ luật an toàn: luôn xem `plan` trước `apply`, không hardcode secret
