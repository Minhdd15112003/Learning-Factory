# Skill: Brain Setup (/brain-setup)

Tạo ra một Vault học tập mới (một "cỗ máy học tập" mới) nằm ngang hàng (cùng cấp) với thư mục hiện tại, kế thừa toàn bộ Framework phương pháp học (Socratic, Feynman, Day Update...) nhưng RỖNG nội dung.

## Khi nào sử dụng

- Khi user nói `/brain-setup`
- Khi user muốn tạo một môi trường học tập hoàn toàn mới (VD: Học Golang, Học Tiếng Anh) tách biệt với Vault hiện tại.

## Cách thức hoạt động

### Bước 1: Phỏng vấn thu thập thông tin (Vault Identity)
Hỏi user tuần tự các câu hỏi sau để định hình Vault mới:
1. **Tên Vault mới là gì?** (Ví dụ: "Learn-Golang", "English-Mastery") -> Dùng làm tên thư mục.
2. **Mục tiêu tối thượng của Vault này là gì?** (Ví dụ: "Trở thành Backend Dev bằng Go", "Giao tiếp tiếng Anh trôi chảy").
3. **Mày tự đánh giá điểm yếu/điểm mù của mày trong lĩnh vực này là gì?** (Để Claude biết cách chỉnh đốn).
4. **Mày có dự định tạo các Sub-projects nào trong này không?** (Ví dụ: Trong Golang sẽ có project "Build-API").

### Bước 2: Tạo Cấu trúc Thư mục mới
Xác định thư mục cha của thư mục hiện tại. (Ví dụ CWD là `.../gcp-document/GCP`, thì thư mục cha là `.../gcp-document/`).
Tạo thư mục mới: `.../gcp-document/[Tên-Vault-Mới]`

Tạo cấu trúc bên trong Vault mới bằng Bash:
```bash
# Path: .../gcp-document/[Tên-Vault-Mới]
mkdir -p "00 Notes" "01 Journals" "02 Chess Moves (Long-Term Planning)" "03 Projects" "04 Reviews" "05 Skills"
```

### Bước 3: Clone Framework (Các công cụ cốt lõi)
Copy các file nền tảng từ Vault hiện tại sang Vault mới (sử dụng Bash):
1. **Skills:** Copy toàn bộ thư mục `05 Skills/` hiện tại sang Vault mới. (Giữ nguyên `day-update.md`, `learn-continue.md`, `weekly-update.md`, `new-project.md`).
2. **Templates:** Copy `01 Journals/Session-Log-Template.md` và `04 Reviews/Reasoning-Gaps.md` sang Vault mới.

### Bước 4: Khởi tạo "Hiến pháp" (CLAUDE.md) mới
Tạo một file `CLAUDE.md` mới tinh cho Vault mới. Giữ lại phần **Teaching Mechanics** và **Operational Protocols** của Vault này, nhưng thay thế các thông tin về GCP bằng thông tin thu thập từ Bước 1.

```markdown
# [Tên-Vault-Mới] — Claude Context File

[Mục tiêu tối thượng từ Bước 1, Câu 2]

## Who I Am & My Purpose
Tôi tập trung vào việc lưu trữ và học hỏi kiến thức về [Chủ đề]. 
Tôi kiên quyết nói không với sự trì hoãn.

## Claude's Purpose in This Level
- Tổ chức kiến thức [Chủ đề] một cách logic.
- Giải thích các khái niệm khó hiểu.
- Ép tôi vào khuôn khổ học tập liên tục.

## Claude's Rules & Boundaries
- Giao tiếp thẳng thắn, trực diện, không vòng vo.
- Ép tôi dùng tiếng Anh khi có thể (Nếu chủ đề liên quan).
- **Error Handling:** Phân tích lỗi gốc trước khi đưa giải pháp.
- **Graph Networking:** Tự động dùng Wiki-links `[[Khái Niệm]]`.

## Claude's Teaching & Interaction Mechanics
[COPY TOÀN BỘ PHẦN NÀY TỪ CLAUDE.md CỦA GCP SANG, BAO GỒM Socratic, Scaffolding, Feynman, Day-update...]

## Folder Structure
[Cấu trúc folder giống GCP]

## My Strengths & Weaknesses
**Weaknesses:**
[Câu trả lời từ Bước 1, Câu 3]

## My Goals & Current Progress
**Mục tiêu:** [Câu 2]

## Weekly Update
> **Last updated:** [Ngày hôm nay]
- What's working: 
- What's not working: 
```

### Bước 5: Bàn giao
Thông báo cho user rằng Vault mới đã được tạo thành công.
Hướng dẫn user: "Mày hãy mở một terminal mới, `cd` vào thư mục `../[Tên-Vault-Mới]` và chạy `claude` để bắt đầu học nhé!"
