# Skill: New Learning Project (/new-learning)

Tạo một nhánh học thuật mới (ví dụ: learn-kubernetes, learn-golang) dưới ô dù của mục tiêu chính.

## How It Works

1. Nhân bản thư mục `03 Projects/(LEARNING TEMPLATE)` thành folder mới.
2. Phỏng vấn user (tối đa 4 câu) để lấy thông tin.
3. Thay thế các biến `{{PROJECT_NAME}}` và `{{PROJECT_FOLDER}}` trong các file đã nhân bản.
4. Cập nhật `CLAUDE.md` ở root để nhận diện nhánh mới.

## Interview Questions (Hỏi từng câu một)

Sử dụng `AskUserQuestion` (hoặc phỏng vấn trực tiếp) để thu thập:

1. **Tên thư mục project là gì?** (Dùng cho folder name, ví dụ: `learn-kubernetes`)
2. **Tên hiển thị của dự án học tập này?** (Ví dụ: `Kubernetes Mastery`)
3. **Mục tiêu của nhánh học này là gì?** (Khoảng 1-2 câu, học để làm gì?)
4. **Vai trò cụ thể của Claude trong project này là gì?** (Ví dụ: "Giải thích các khái niệm về K8s, hướng dẫn viết file YAML")

## After the Interview

### Step 1: Duplicate the template
```bash
cp -R "03 Projects/(LEARNING TEMPLATE)" "03 Projects/[Tên thư mục]"
```

### Step 2: Cập nhật các file trong Project mới
Dùng tool Edit/Write để thay thế toàn bộ các placeholder trong các file mới tạo:
- Thay `{{PROJECT_FOLDER}}` bằng tên thư mục (câu 1).
- Thay `{{PROJECT_NAME}}` bằng tên hiển thị (câu 2).
- Điền [Mô tả ngắn gọn về dự án học tập này] bằng câu trả lời 3.
- Điền [Mô tả vai trò của Claude trong dự án này] bằng câu trả lời 4.

*Lưu ý: Template đã có sẵn `CLAUDE.md`, `Reasoning-Gaps.md`, `Session-Log-Template.md` với các link kế thừa (Inheritance) đã được set up sẵn.*

### Step 3: Thêm các thư mục rỗng chuẩn
Tạo thêm các folder nếu cần (dựa trên lộ trình học của user, nhưng mặc định tạo thêm `02 Thuc hanh`, `03 Output`, `05 Skills`).

### Step 4: Cập nhật Root CLAUDE.md
Thêm thông tin project mới vào phần **My Current Projects & Overviews** ở file `CLAUDE.md` gốc để hệ thống nhận diện.

```markdown
### [Tên hiển thị] — `03 Projects/[Tên thư mục]/`
**Status:** Just created
[Mục tiêu của nhánh học]
```

### Step 5: Xác nhận
Thông báo cho user biết hệ thống đã sẵn sàng và họ có thể bắt đầu học bằng lệnh `/learn-continue [Tên thư mục]`.
