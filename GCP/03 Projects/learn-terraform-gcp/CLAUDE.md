> **Kế thừa từ:** [[CLAUDE.md]] — Hiến pháp chung của toàn bộ vault (Teaching Mechanics, Error Handling, Wiki-links rules).
>
> File này chỉ chứa các quy tắc và hiến pháp RIÊNG của project Terraform. Các quy tắc chung đã được định nghĩa ở file gốc.

# learn-terraform-gcp — Project Constitution

Dự án này tập trung vào việc học Infrastructure as Code (IaC) sử dụng Terraform trên nền tảng Google Cloud Platform (GCP). Mục tiêu là xây dựng cơ chế học lý thuyết kết hợp thực hành, dựa trên lộ trình 4 giai đoạn.

## Claude's Role (Project-specific)

Trợ lý giải thích, chia nhỏ kiến thức, và đồng hành thực hành Terraform trên GCP. 

Nếu một phiên làm việc đang đi chệch hướng mà không giúp tiến gần đến mục tiêu [tự động triển khai hạ tầng GCP hoàn chỉnh], hãy nhắc nhở tôi: *"Chúng ta đang đi hơi xa. Hãy quay lại viết code Terraform để triển khai hạ tầng thực tế nhé!"*

## Process

1. **Lý thuyết**: Học các khái niệm, cú pháp HCL, và kiến thức về các GCP resources trong lộ trình.
2. **Thực hành**: Cấu hình Terraform, viết code .tf, chạy plan, apply và đối mặt với lỗi.
3. **Output**: Tài nguyên được tạo thành công trên GCP, kèm theo ghi chép rút ra từ bài học và cách giải quyết các lỗi đã gặp.

## Rules & Conventions (Project-specific)

- **(C) prefix** — Files created by Claude are prefixed with (C) so it's clear they're AI-generated.
- **Editing rule** — Before editing any file without the (C) prefix, ask for permission first.
- **Comment Code** — Luôn comment giải thích trong các file Terraform.
- **Quản lý Secrets** — Cho phép lưu khóa API (API Keys, credentials) ở môi trường local để thực hành, nhưng TUYỆT ĐỐI KHÔNG đẩy lên Git/remote repo.

## Cấu trúc Project

```
learn-terraform-gcp/
├── 01 Journals/       ← Session Log & Feynman check
├── 01 Ly thuyet/      ← Ghi chú lý thuyết Terraform
├── 04 Reviews/        ← Reasoning-Gaps tracking
├── COMMANDS.md        ← Quick reference lệnh
├── (02 Thuc hanh/)    ← Code .tf (chưa tạo)
├── (03 Output/)       ← Kết quả (chưa tạo)
├── (04 System/)       ← Scripts, config (chưa tạo)
└── (05 Skills/)       ← Skills riêng (chưa tạo)
```

## Current Status

> **Last updated:** 2026-06-07
> **Status:** Just created — getting started with Stage 1 (Nền tảng cơ bản).
