# Terraform Project — Context & Rules

> **CRITICAL DIRECTIVE FOR CLAUDE:**
> Before following any rules in this file, you MUST first read and strictly adhere to the global rules, teaching mechanics (Socratic, Feynman, etc.), and boundaries defined in the root file: `../../../CLAUDE.md`.
> The rules below are SUPPLEMENTARY and specific ONLY to this Terraform project.

## Project Purpose
Dự án học Infrastructure as Code (IaC) sử dụng Terraform trên GCP. Lộ trình từ cơ bản (hiểu HCL) đến nâng cao (quản lý state, module, CI/CD).

## 🛠️ Terraform Specific Rules (Project-level Constitution)

1. **Rule of Least Privilege:** Khi viết Terraform code tạo IAM, luôn luôn nhắc nhở về rule of least privilege (không cấp quyền Editor/Owner bừa bãi).
2. **State Management Warning:** Bất cứ khi nào cấu hình `backend "gcs"`, phải cảnh báo về State Lock và State Corruption.
3. **No Direct Console Changes:** Trừ khi là debug, tuyệt đối khuyên không sửa tay trên GCP Console vì sẽ làm lệch (drift) Terraform state.
4. **Code Commenting:** Luôn giải thích các block `resource`, `data`, `module` bằng comment tiếng Việt trong file `.tf`.
5. **Secret Handling:** Nhắc nhở KHÔNG đẩy file `.tfvars` hoặc service account JSON credentials lên remote repo.

## Local Folder Structure
- `01 Journals/` — Ghi chép học tập, Feynman check.
- `01 Ly thuyet/` — Các note lý thuyết.
- `04 Reviews/` — Reasoning Gaps tracking.
- `COMMANDS.md` — Các lệnh Terraform cần nhớ nhanh.
- *(Các folder thực hành Code sẽ được tạo khi bắt đầu vào lab)*
