# Skill: Learn Continue (/learn-continue)

Kích hoạt vào đầu buổi học để Claude nạp lại (load context) trạng thái học tập gần nhất của user, giải quyết vấn đề "Stateless" của AI.

## Khi nào sử dụng

- User gõ `/learn-continue [tên-project]` (ví dụ: `/learn-continue terraform` hoặc `/learn-continue gcp`)
- User mở session mới và yêu cầu học tiếp bài cũ.

## Cách thức hoạt động

### Bước 1: Xác định Hardcoded Path
- Nếu arg là `terraform`: Path = `03 Projects/learn-terraform-gcp/`
- Nếu arg là `gcp` hoặc trống: Path = root `.`

### Bước 2: Đọc Context
Sử dụng công cụ (Bash/Read) để quét thư mục:
1. Đọc phần **Current Status** trong file `CLAUDE.md` của thư mục tương ứng.
2. Tìm và đọc file md **mới nhất** trong thư mục `01 Journals/` của project đó (chính là Session Log của buổi trước).
3. Đọc file `04 Reviews/Reasoning-Gaps.md` để xem user đang mắc kẹt ở đâu.

### Bước 3: Due Review Check (Ôn tập ngắt quãng)
Dựa vào file `Reasoning-Gaps.md` vừa đọc, lọc ra những khái niệm có ngày `Review: YYYY-MM-DD` nhỏ hơn hoặc bằng ngày hôm nay.
- Nếu có khái niệm cần ôn: Thông báo "Có N khái niệm cần ôn hôm nay". Thực hiện Test nhanh bằng câu hỏi Socratic (không gợi ý). Tùy vào câu trả lời của user để quyết định giữ nguyên hay nâng Knowledge State, tính lại `next_review`, và lưu vào `Reasoning-Gaps.md`.
- Nếu không có: Bỏ qua bước này.

### Bước 4: Phân tích & Đề xuất Bài Mới
Dựa trên những thông tin vừa nạp ở Bước 2, Claude sẽ:
- Tóm tắt lại: *"Hôm trước mày đang làm đến X, có gặp vấn đề ở Y..."*
- Gợi ý bài tiếp theo: *"Hôm nay mình sẽ đi tiếp phần Z để giải quyết vấn đề đó nhé. Sẵn sàng chưa?"*

---

## Lưu ý cho Claude
- KHÔNG yêu cầu user cung cấp lại thông tin. Đây là trách nhiệm của bạn phải tự động đọc các file log cũ để build lại memory cho session mới.
- Luôn giữ thái độ của một "người hướng dẫn đồng hành", không phải một bot mới khởi động lại.
