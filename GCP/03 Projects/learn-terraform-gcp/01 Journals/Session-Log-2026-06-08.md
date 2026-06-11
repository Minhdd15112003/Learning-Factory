# Session Log: 2026-06-08

> **Tham chiếu:** [[GCP/03 Projects/learn-terraform-gcp/CLAUDE.md|Constitution]] | [[GCP/03 Projects/learn-terraform-gcp/04 Reviews/Reasoning-Gaps.md|Reasoning Gaps]]

## Mục tiêu buổi học
- [x] Bắt đầu Stage 1: Hiểu về bản chất của Terraform và Declarative approach.
- [x] Cài đặt môi trường cơ bản để chạy Terraform với Google Cloud.
- [x] Tìm hiểu về Terraform State và tầm quan trọng của file `.tfstate`.
- [x] Hiểu về Remote Backend (GCS) và cơ chế State Locking.

## Quá trình (Claude làm Reasoning Engine)
- Sự khác biệt giữa việc tự click/viết script và việc sử dụng Terraform: Khả năng tự động hóa, giảm thiểu rủi ro con người.
- Phân biệt **Imperative** (mô tả How) và **Declarative** (mô tả What - Single Source of Truth).
- Thực hành triển khai Bucket đầu tiên trên GCP, xử lý ràng buộc `uniformBucketLevelAccess`.
- **Terraform State:** Phát hiện file `terraform.tfstate` là "cuốn sổ cái" ánh xạ giữa Code và thực tế.
- **Thử nghiệm "Mất trí nhớ":** Đổi tên file state dẫn đến lỗi `Conflict 409` khi `apply` vì Terraform cố tạo lại tài nguyên đã tồn tại.
- **Giải pháp:** Lệnh `terraform import` để cứu vãn khi mất state.
- **Remote State:** Sử dụng GCS làm Backend để làm việc nhóm, hỗ trợ **State Locking** (ngăn chặn Race Condition) và bảo mật dữ liệu nhạy cảm tốt hơn Git.

## Explain-back (Feynman Check)
- **uniform_bucket_level_access:** để yêu cầu được phân quyền cho toàn bucket.
- **Idempotency:** chạy terraform apply 100 lần chỉ tạo ra 1 cái.
- **Terraform State:** Lưu lại những service nào đã được cài, nếu không có nó Terraform không biết cái nào đã setup, cái nào chưa.
- **Tại sao dùng GCS thay vì Git:** Tránh lộ thông tin nhạy cảm (API Keys, Passwords) trong file JSON và hỗ trợ cơ chế khóa file (Locking) khi có người đang sửa.

## Misconceptions & Gaps (Ghi nhận lỗi)
- Ban đầu nhầm lẫn Terraform dựa vào tên bucket để nhớ (thực tế dựa vào file State).
- Đã thông suốt về cơ chế đồng bộ tự động của Remote Backend.

## Knowledge State
- [[01-Ban-chat-cua-Terraform|Bản chất Terraform (Declarative vs Imperative)]]: `Understood`
- [[02-Terraform-State|Terraform State (Local vs Remote)]]: `Partial` (Cần review lại cơ chế backend thực tế)
