---
status: Partial
tags: [terraform, iac, declarative]
---

# Bản chất của Terraform

## 1. Tại sao lại dùng Terraform?

Khởi điểm, việc tạo tài nguyên bằng tay (Click console) hoặc viết script Bash (gcloud CLI) bộc lộ nhược điểm khi hệ thống lớn lên:
- Dễ thao tác sai lầm (Human error).
- Khó theo dõi lịch sử thay đổi.
- **Quan trọng nhất:** Rất khó để thay đổi/cập nhật hệ thống cũ nếu chỉ dùng script (Imperative).

## 2. Điểm khác biệt cốt lõi: Imperative vs Declarative

- **Imperative (Bash Script, gcloud CLI):** Mô tả **LÀM THẾ NÀO (How)**. Bạn phải viết từng bước một (Tạo VPC -> Tạo Subnet 1 -> Tạo Subnet 2). Khi cần thêm Subnet 3, bạn phải tự nhớ trạng thái hệ thống và cẩn thận chạy đúng lệnh thêm mới, lỡ chạy lại script cũ sẽ báo lỗi.
- **Declarative ([[Terraform]]):** Mô tả **MUỐN GÌ (What)**. Bạn khai báo trạng thái mong muốn cuối cùng ("Tôi muốn có 1 VPC và 3 Subnet"). Terraform sẽ tự động so sánh trạng thái hiện tại (ví dụ đang có 2 subnet) với mong muốn (3 subnet), từ đó tính toán ra rằng: *"Chỉ cần tạo thêm 1 subnet nữa"*. Mọi thứ được ghi nhận trên một tệp code, tường minh, dễ đọc, dễ nhớ.

## 3. Lợi ích khi lưu trữ dạng Code

Như đã rút ra từ bài học:
> "Tại vì nếu mọi thứ đã ở trên một đoạn code thì mọi thứ sẽ tường minh, dễ đọc, dễ nhớ và không bao giờ quên những gì mình đang làm."

Khi hạ tầng là code (Infrastructure as Code - IaC), bạn có thể đọc hạ tầng như đọc văn bản, đưa nó vào Git để quản lý version (lưu lại lịch sử ai đã sửa gì, vào lúc nào).
