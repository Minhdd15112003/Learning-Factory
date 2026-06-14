# Mục Tiêu & Lộ Trình — GCP

## Mục Tiêu Chính

Thi đậu chứng chỉ [[Associate Cloud Engineer]] (ACE) — nền tảng để làm việc thực sự với hạ tầng GCP ở quy mô sản xuất.

---

## Vị Trí Hiện Tại

- **Khởi động lại từ đầu — ngày 12/06/2026.**
- Đang học khoá "Essential Google Cloud Infrastructure: Core Services" — phần [[Filestore]].
- Xây lại nền tảng GCP từ số 0; theo nhịp riêng, ưu tiên hiểu sâu.

> Ghi chú: [[Terraform]] giờ là **môn học riêng** (`Terraform-GCP/`), không còn nằm trong GCP. Lộ trình Terraform xem `Terraform-GCP/GOALS.md`.

---

## Chiến Lược Học

- Học lý thuyết ở nhà, tận dụng môi trường công ty để thực hành thực tế trên GCP thật.
- Không có áp lực thời gian cứng — học theo nhịp riêng, ưu tiên hiểu sâu hơn chạy nhanh.
- Mỗi khái niệm phải đạt tối thiểu `Understood` (pass Feynman) trước khi sang phần tiếp theo.

---

## Lộ Trình GCP (ACE) — thô, tinh chỉnh sau mỗi buổi

### Giai đoạn 1 — Nền tảng môi trường
- [ ] [[GCP Project]], billing, [[Resource Hierarchy]] (Organization → Folder → Project)
- [ ] [[IAM]]: role (primitive / predefined / custom), [[Service Account]]
- [ ] Công cụ: Console, [[gcloud]] CLI, Cloud Shell

### Giai đoạn 2 — Core Services (đang học)
- [ ] Storage: [[Cloud Storage]], [[Filestore]], [[Persistent Disk]]
- [ ] Database: [[Cloud SQL]], [[Cloud Spanner]], [[Firestore]]
- [ ] Networking cơ bản: [[VPC]], subnet, firewall, [[Cloud Load Balancing]]

### Giai đoạn 3 — Compute & triển khai
- [ ] [[Compute Engine]]: máy ảo, instance group, autoscaling
- [ ] [[GKE]], [[Cloud Run]], [[App Engine]] — khi nào dùng cái nào
- [ ] Deploy + quản lý vận hành

### Giai đoạn 4 — Vận hành & bảo mật
- [ ] [[Cloud Monitoring]], [[Cloud Logging]]
- [ ] IAM nâng cao, security best practices
- [ ] Ôn đề ACE + thi thử

---

## Cột Mốc & Đo Tiến Độ

Tiến độ đo qua Knowledge State của từng khái niệm, không phải số bài hoàn thành:

| Knowledge State | Nghĩa | Điều kiện chuyển |
|---|---|---|
| `Exposed` | Mới nghe qua | — |
| `Partial` | Hiểu một phần, nói được kết quả | — |
| `Understood` | Pass Feynman — giải thích được cơ chế | Trả lời đúng "Tại sao / Nó làm thế nào?" |
| `Mastered` | Ôn đúng 3 lần liên tiếp | 3 review liên tiếp đạt Easy/Good |

Lịch ôn theo frontmatter `sr-due / sr-interval / sr-ease`; gap ghi trong `GCP/04 Reviews/Reasoning-Gaps.md`.

Mốc có ý nghĩa: khi toàn bộ khái niệm cốt lõi của một giai đoạn đạt `Understood` trở lên, mới coi giai đoạn đó hoàn thành.
