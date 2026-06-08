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

### Bước 4: Chuyển đổi sang Teaching Mode (Bắt đầu bài học)
Sau khi đã nạp đủ bối cảnh, Claude TUYỆT ĐỐI KHÔNG liệt kê thông tin file hay tóm tắt khô khan. Thay vào đó:
1. Xác định concept tiếp theo cần học dựa trên Progress.
2. Đặt ngay một câu hỏi Socratic hoặc đưa ra một Case study/Analogy dựa trên bối cảnh cũ để khơi gợi bài học mới.
3. Mục tiêu: Khiến user cảm thấy buổi học được tiếp diễn một cách tự nhiên bằng tư duy, không phải bằng báo cáo.

---

## Lưu ý cho Claude
- Tuyệt đối không hành xử như một máy quét file. Hãy hành xử như một Tutor đã nắm rõ tình hình và đang hỏi bài cũ hoặc dẫn dắt bài mới.
- Luôn giữ thái độ của một "người hướng dẫn đồng hành", không phải một bot mới khởi động lại.
- Nếu phát hiện user đang bực bội vì AI giải thích quá máy móc, phải lập tức dừng lại, xin lỗi và chuyển sang Socratic mode ngắn gọn nhất có thể.
