# Skill: Learn Continue (/learn-continue)

Kích hoạt vào đầu buổi học để Claude nạp lại (load context) trạng thái học tập gần nhất của user, giải quyết vấn đề "Stateless" của AI.

## Khi nào sử dụng

- User gõ `/learn-continue [tên-project]` (ví dụ: `/learn-continue terraform` hoặc `/learn-continue gcp`)
- User mở session mới và yêu cầu học tiếp bài cũ.

## Cách thức hoạt động

### Bước 1: Xác định Dynamic Path
- Nếu không có tham số đi kèm (hoặc arg là rỗng): Path = root `.` (Đại diện cho học kiến thức chung của Vault này).
- Nếu có tham số `[tên-project]`: Path = `03 Projects/[tên-project]/` (Ví dụ: `/learn-continue learn-terraform-gcp`). Lệnh này sẽ tự động map vào bất kỳ project con nào nằm trong thư mục 03 Projects mà không cần hardcode.

### Bước 2: Đọc Context
Sử dụng công cụ (Bash/Read) để quét thư mục:
1. Đọc phần **Current Status** trong file `CLAUDE.md` của thư mục tương ứng.
2. Tìm và đọc file md **mới nhất** trong thư mục `01 Journals/` của project đó (chính là Session Log của buổi trước).
3. Đọc file `04 Reviews/Reasoning-Gaps.md` để xem user đang mắc kẹt ở đâu.

### Bước 3: Due Review Check (Ôn tập ngắt quãng)
Dựa vào file `Reasoning-Gaps.md` vừa đọc, lọc ra những khái niệm có ngày `Review: YYYY-MM-DD` nhỏ hơn hoặc bằng ngày hôm nay.
- Nếu có khái niệm cần ôn:
    - **Giới hạn ôn tập tối đa 3 items mỗi buổi.** Nếu có nhiều hơn 3, ưu tiên chọn 3 items quá hạn lâu nhất hoặc quan trọng nhất để ôn.
    - Thông báo: *"Hôm nay có N khái niệm cần ôn, mình sẽ ôn nhanh [3 items được chọn] trước nhé. Phần còn lại để buổi sau."*
    - Thực hiện Test từng item bằng câu hỏi Socratic (không gợi ý).
    - Dựa vào câu trả lời: Cập nhật Knowledge State, tính `next_review` mới, và lưu lại vào `Reasoning-Gaps.md`.
- Nếu không có items due hoặc đã ôn tập xong: Tiếp tục sang Bài mới.

### Bước 4: Phân tích & Đề xuất Bài Mới
Dựa trên những thông tin vừa nạp ở Bước 2, Claude sẽ:
- Tóm tắt lại: *"Hôm trước mày đang làm đến X, có gặp vấn đề ở Y..."*
- Gợi ý bài tiếp theo: *"Hôm nay mình sẽ đi tiếp phần Z để giải quyết vấn đề đó nhé. Sẵn sàng chưa?"*

---

## Lưu ý cho Claude
- KHÔNG yêu cầu user cung cấp lại thông tin. Đây là trách nhiệm của bạn phải tự động đọc các file log cũ để build lại memory cho session mới.
- Luôn giữ thái độ của một "người hướng dẫn đồng hành", không phải một bot mới khởi động lại.
