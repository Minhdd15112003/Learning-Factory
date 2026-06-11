# COMMANDS.md

Bảng tra nhanh lệnh & skill cho project Terraform: dùng khi cần tra cú pháp nhanh mà không cần mở tài liệu chính.

---

## Terraform Core Commands

| Command | Giải thích |
|---|---|
| `terraform init` | Khởi tạo working directory: tải provider plugin và khởi động backend state. Chạy đầu tiên khi clone repo hoặc thêm provider mới. |
| `terraform fmt` | Tự động định dạng lại các file `.tf` theo chuẩn HCL canonical style. Nên chạy trước khi commit. |
| `terraform validate` | Kiểm tra cú pháp và tính hợp lệ logic của cấu hình (không kết nối API). Không xác nhận resource có tồn tại trên cloud hay không. |
| `terraform plan` | Tạo execution plan: so sánh state hiện tại với cấu hình mong muốn, liệt kê các thay đổi sẽ xảy ra (create/update/destroy). Không thực thi. |
| `terraform apply` | Thực thi execution plan: tạo/sửa/xóa resource thực tế trên GCP. Mặc định hỏi xác nhận trước khi chạy. |
| `terraform destroy` | Xóa toàn bộ resource được quản lý bởi cấu hình hiện tại. Nguy hiểm — luôn xem plan trước. |
| `terraform state list` | Liệt kê tất cả resource đang được theo dõi trong state file. |
| `terraform state show <resource>` | Hiển thị chi tiết thuộc tính của một resource cụ thể trong state (ví dụ: `terraform state show google_compute_instance.vm`). |
| `terraform import <resource> <id>` | Đưa một resource đã tồn tại trên GCP vào quản lý của Terraform state mà không tạo lại nó. |
| `terraform output` | In ra giá trị của tất cả output variable đã khai báo trong cấu hình. |
| `terraform refresh` | Đồng bộ state file với trạng thái thực tế của resource trên GCP (deprecated trong v1.x — thay bằng `terraform apply -refresh-only`). |

---

## gcloud Auth Context

| Command | Giải thích |
|---|---|
| `gcloud auth application-default login` | Tạo Application Default Credentials (ADC) trên máy local. [[Terraform GCP Provider]] dùng ADC này để xác thực với GCP API khi chạy `terraform plan` và `terraform apply`. Chạy lệnh này một lần duy nhất sau khi cài gcloud SDK. |

---

## Vault Skills (defined in root `05 Skills/`)

Các skill dưới đây được định nghĩa tại thư mục `05 Skills/` của root vault. Gọi tên skill để Claude tự load hướng dẫn chi tiết từ file tương ứng.

| Skill | Giải thích |
|---|---|
| `/learn-continue` | Bắt đầu buổi học: nạp lại context, kiểm tra các note đến hạn ôn tập (sr-due), chạy Socratic review, sau đó mở bài mới. |
| `/day-update` | Kết thúc buổi học: phỏng vấn Feynman để chấm điểm, cập nhật SR schedule trong frontmatter các note, ghi log vào Session Log. |
