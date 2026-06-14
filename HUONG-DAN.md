# Hướng Dẫn Sử Dụng

Cách dùng vault học hằng ngày. Tất cả gói trong **3 lệnh**: `/learn`, `/done`, `/new-learn`.

---

## 1. Khởi động (chọn 1 trong 2 cách)

**Cách A — trong Obsidian (Claudian plugin):** mở panel Claudian, gõ lệnh kèm **tên môn** (plugin luôn đứng ở gốc vault):
```
/learn DevOps
```

**Cách B — terminal (CLI):** vào thẳng thư mục môn rồi chạy `claude`, sau đó gõ lệnh **không cần tên**:
```
cd "C:\Users\minhdd_resolve\Desktop\WorkSpace\Learning-Factory\DevOps"
claude
/learn
```

Hai cách cho kết quả y hệt. Cách A tiện hơn khi đang đọc note trong Obsidian.

---

## 2. Vòng lặp một buổi học

```
/learn [môn]   →   (học)   →   /done
   ↑ mở đầu                    ↑ đóng & lưu
```

Chỉ cần nhớ: **mở bằng `/learn`, đóng bằng `/done`.** Quên `/done` thì kiến thức hôm đó không vào lịch ôn.

---

## 3. Ba lệnh

### `/learn [môn]` — bắt đầu / học tiếp
Khi gõ, Claude tự động (im lặng, không báo cáo dài dòng):
1. Nạp context của môn (log buổi trước, các lỗ hổng đã ghi).
2. **Ôn các note đến hạn** — hỏi Socratic từng note `sr-due <= hôm nay` (tối đa 3 note/buổi), tự chấm Easy/Good/Hard và dời lịch ôn.
3. **Môn mới chưa có note** → chạy **đánh giá đầu vào**: hỏi vài câu để định vị bạn đang ở đâu trên roadmap, rồi mới bắt đầu.
4. **Dạy bài mới** — mở bằng một câu hỏi/tình huống. Với cơ chế hoàn toàn mới, Claude **giới thiệu ngắn 2-3 câu trước** rồi mới hỏi (không bắt bạn tự đoán từ số 0).

### `/done` — kết thúc & lưu
Gõ khi muốn dừng buổi học. Claude sẽ:
1. **Phỏng vấn Feynman** 1-2 câu về concept hôm nay (hỏi "cơ chế thế nào", không hỏi "là gì").
2. Cập nhật trạng thái (`Understood` chỉ khi bạn giải thích được cơ chế) + lịch ôn trong note.
3. Ghi Session Log, cập nhật lỗ hổng (Reasoning-Gaps) và Current Status.

### `/new-learn [tên]` — tạo môn học mới
Hỏi ngắn (tên, mục tiêu, điểm yếu, roadmap thô), rồi dựng thư mục môn mới + file mục tiêu. Xong, gõ `/learn [tên]` để bắt đầu (buổi đầu sẽ chạy đánh giá đầu vào).

---

## 4. Một buổi học diễn ra thế nào

Mỗi topic đi theo **Lý thuyết → Thực hành → Output**, và Claude **không nhảy sang concept mới** khi concept hiện tại chưa làm thực hành:

- **Lý thuyết** (`00 Ly thuyet/`): học qua đối thoại Socratic. Khi bạn giải thích được cơ chế (đạt `Understood`), **Claude mới viết note lý thuyết** cho bạn — bạn không phải tự viết.
- **Thực hành** (`01 Thuc hanh/`): Claude đề xuất một việc cụ thể (chạy 1 lệnh, viết 1 đoạn `.tf`/`.yaml`), bạn làm, Claude đọc kết quả và sửa lỗi cùng.
- **Output** (`02 Output/`): ghi lại kết quả / hạ tầng đã dựng (nếu có).

---

## 5. Mẹo "lái" buổi học

- Bí quá, muốn được giải thích → nói **"giải thích trước đi"**.
- Claude hỏi vòng vo → nói **"Socratic"** để quay về một câu hỏi dẫn dắt gọn.
- Mệt / không muốn nghi thức → cứ nói thẳng, Claude sẽ trả lời tắt và gợi ý nghỉ.
- Muốn ôn mà không học bài mới → vào `/learn`, làm xong phần ôn rồi nói "hôm nay chỉ ôn thôi".

---

## 6. Knowledge State (mức độ hiểu)

| Status | Ý nghĩa |
|---|---|
| `Exposed` | Mới nghe qua |
| `Partial` | Hiểu một phần, nói được kết quả |
| `Understood` | Pass Feynman — giải thích được cơ chế |
| `Mastered` | Ôn đúng 3 lần liên tiếp |

Lên `Understood` bắt buộc giải thích được "cơ chế / tại sao". Lịch ôn (spaced repetition) chạy tự động qua `/learn` — bạn không cần bấm gì trong plugin.

---

## 7. File trong mỗi môn

| File / thư mục | Là gì |
|---|---|
| `GOALS.md` | Lộ trình + mục tiêu (tiếng Việt — bạn sửa tự do) |
| `CLAUDE.md` | Nhận dạng + trạng thái hiện tại của môn |
| `00 Ly thuyet/` | Note lý thuyết + lịch ôn |
| `01 Thuc hanh/` | Thực hành |
| `02 Output/` | Kết quả |
| `03 Journals/` | Log mỗi buổi |
| `04 Reviews/` | Reasoning-Gaps (ghi lỗ hổng) |

Xem thêm `README.md` (kiến trúc) và `SETUP.md` (cài đặt máy mới).
