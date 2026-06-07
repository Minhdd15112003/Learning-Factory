# Skill: Day Update (/day-update)

Kích hoạt vào cuối buổi học để tổng hợp toàn bộ quá trình, thực hiện Feynman check và đóng log ngày.

## Khi nào sử dụng

- User gõ `/day-update` hoặc nói "kết thúc buổi học", "tổng kết hôm nay"

## Cách thức hoạt động

### Bước 1: Xác định phạm vi (Scope)
Xác định user đang học ở project nào (Terraform hay GCP gốc) dựa trên context hiện tại hoặc hỏi user.

### Bước 2: Tổng hợp Incremental Logs
Đọc file Session Log của ngày hôm nay trong thư mục `01 Journals/` tương ứng. Tóm tắt các checkpoint đã ghi được trong quá trình học.

### Bước 3: Phỏng vấn Feynman (Chốt kiến thức)
Đặt **1-2 câu hỏi sâu** về những gì quan trọng nhất đã học hôm nay. Yêu cầu user tự giải thích bằng ngôn ngữ của họ. Không được tự ý tóm tắt thay user ở bước này.

### Bước 4: Cập nhật Reasoning Gaps
Nếu trong buổi học có phát hiện lỗ hổng kiến thức mới, nhắc user cập nhật (hoặc xin phép cập nhật hộ) vào file `04 Reviews/Reasoning-Gaps.md`.

### Bước 5: Đóng Log & Cập nhật Status
1. Append câu trả lời Feynman của user vào cuối file Session Log.
2. Cập nhật phần **Current Status** trong file `CLAUDE.md` tương ứng (ghi rõ đã học đến đâu).
3. Thông báo cho user buổi học đã được lưu an toàn.

---

## Lưu ý cho Claude

- Tuyệt đối không tự ý viết thay user phần "Explain-back". Đây là bước quan trọng để user "ngấm" kiến thức.
- Luôn bọc các thuật ngữ bằng `[[Wiki-links]]` khi ghi log.
