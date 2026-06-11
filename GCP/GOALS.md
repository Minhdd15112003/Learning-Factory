# Mục Tiêu & Lộ Trình

## Mục Tiêu Chính

Thi đậu chứng chỉ [[Associate Cloud Engineer]] (ACE) — nền tảng để làm việc thực sự với hạ tầng GCP ở quy mô sản xuất.

---

## Vị Trí Hiện Tại

- Khóa học: "Essential Google Cloud Infrastructure: Core Services"
- Đang học đến phần: [[Filestore]]
- Trạng thái tổng thể: đang xây nền tảng lý thuyết GCP song song với dự án thực hành Terraform.

---

## Chiến Lược Học

- Học lý thuyết ở nhà, tận dụng môi trường công ty để thực hành thực tế trên GCP thật.
- Không có áp lực thời gian cứng — học theo nhịp riêng, ưu tiên hiểu sâu hơn chạy nhanh.
- Mỗi khái niệm phải đạt Knowledge State tối thiểu `Understood` (pass Feynman check) trước khi chuyển sang phần tiếp theo. Xem thêm: hệ thống [[Spaced Repetition]] trong vault.

---

## Dự Án Song Song

### [[learn-terraform-gcp]] — `03 Projects/learn-terraform-gcp/`

Học [[Terraform]] + [[GCP]] theo hướng [[Infrastructure as Code]] (IaC) để tự động hóa triển khai hạ tầng. Mục tiêu dài hạn: có khả năng thiết kế và vận hành hạ tầng tự động hóa hoàn toàn trên GCP.

---

## Lộ Trình Terraform — 4 Giai Đoạn

### Stage 1 — Nền Tảng (Đang tiến hành)

Hiểu bản chất của [[IaC]] và cách Terraform hoạt động ở mức cơ chế:

- [[Declarative vs Imperative]] — tại sao khai báo trạng thái mong muốn thay vì viết từng bước.
- [[Terraform State]] — State lưu ở đâu, dùng để làm gì, tại sao mất State là nguy hiểm.
- [[Terraform Backend]] — lưu State từ xa, cơ sở để làm việc nhóm.
- Vòng lặp cốt lõi: `terraform init` / `plan` / `apply` / `destroy`.

### Stage 2 — Quản Lý Resource & Networking trên GCP

- Tạo và quản lý các resource GCP cơ bản bằng Terraform: [[Compute Engine]], [[Cloud Storage]], [[VPC]], subnet, firewall rules.
- Hiểu sơ đồ phụ thuộc (dependency graph) mà Terraform tự tính.

### Stage 3 — Modules, Variables, Tái Sử Dụng

- [[Terraform Modules]] — đóng gói hạ tầng thành block tái sử dụng.
- [[Input Variables]] và [[Output Values]] — truyền tham số vào module, lấy kết quả ra.
- Tổ chức code `.tf` theo cấu trúc có thể bảo trì lâu dài.

### Stage 4 — Nâng Cao

- [[Remote State]] và chia sẻ State giữa các team.
- Tích hợp Terraform vào [[CI/CD]] pipeline.
- [[Terraform Workspace]] — quản lý nhiều môi trường (dev / staging / prod) từ cùng một codebase.

---

## Cột Mốc & Đo Tiến Độ

Tiến độ được đo qua Knowledge State của từng khái niệm, không phải số bài hoàn thành:

| Knowledge State | Nghĩa | Điều kiện chuyển |
|---|---|---|
| `Exposed` | Mới nghe qua | — |
| `Partial` | Hiểu một phần, nói được kết quả | — |
| `Understood` | Pass Feynman check — giải thích được cơ chế | Trả lời đúng câu "Tại sao / Nó làm thế nào?" |
| `Mastered` | Ôn đúng 3 lần liên tiếp không trượt | 3 lần review liên tiếp đạt Easy/Good |

Lịch ôn tập được quản lý tự động qua [[Spaced Repetition]] plugin trong Obsidian. Trạng thái từng note được ghi trong frontmatter `status:` và theo dõi gap trong `GCP/04 Reviews/Reasoning-Gaps.md`.

Mốc thực sự có ý nghĩa: khi toàn bộ các khái niệm cốt lõi của một Stage đạt `Understood` trở lên, mới coi Stage đó hoàn thành.
