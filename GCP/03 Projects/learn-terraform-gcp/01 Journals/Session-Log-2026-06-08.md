# Session Log: 2026-06-08

> **Tham chiếu:** [[03 Projects/learn-terraform-gcp/CLAUDE.md|Constitution]] | [[03 Projects/learn-terraform-gcp/04 Reviews/Reasoning-Gaps.md|Reasoning Gaps]]

## Mục tiêu buổi học
- [x] Bắt đầu Stage 1: Hiểu về bản chất của Terraform và Declarative approach.
- [x] Cài đặt môi trường cơ bản để chạy Terraform với Google Cloud.

## Quá trình (Claude làm Reasoning Engine)
*Ghi chú nhanh các khái niệm, lệnh, hoặc luồng suy nghĩ. Dùng Claude để hỏi Socratic.*
- Sự khác biệt giữa việc tự click/viết script và việc sử dụng Terraform: Khả năng tự động hóa, giảm thiểu rủi ro con người.
- Phân biệt **Imperative** (mô tả How - dễ lỗi, khó bảo trì) và **Declarative** (mô tả What - code là Single Source of Truth).
- Thực hành triển khai Bucket đầu tiên trên GCP, nhận diện và xử lý thành công ràng buộc bảo mật `uniformBucketLevelAccess` của Organization Policy.
- Kiểm chứng thực tế tính chất **Idempotency** thông qua lệnh `terraform apply` lần 2 (No changes).

## Explain-back (Feynman Check)
*Dành 5 phút tự giải thích lại bằng ngôn ngữ của mình (không copy paste của Claude).*

- **uniform_bucket_level_access:** để yêu cầu được phân quyền cho toàn bucket.
- **Idempotency:** chạy terraform apply 100 lần chỉ tạo ra 1 cái.

## Misconceptions & Gaps (Ghi nhận lỗi)
*Hôm nay mình đã nhầm lẫn/hiểu sai điều gì?*

- *(Không có gap lớn — lỗi `uniformBucketLevelAccess` đã fix và hiểu được nguyên nhân)*

*(Nếu có gap lớn, hãy copy sang file [[Reasoning-Gaps]])*
