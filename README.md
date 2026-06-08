# 📚 Learning Factory — Hệ thống học tập thích nghi

> **Mục đích:** Biến AI (Claude) từ một cái máy trả lời câu hỏi thành một **người gia sư cá nhân** có trí nhớ dài hạn, có phương pháp dạy học khoa học, và có kỷ luật thép.

---

## 1. Kiến trúc tổng thể

```
WorkSpace/gcp-document/
│
├── GCP/              ← Vault học Google Cloud (Mục tiêu: ACE)
├── Tieng-Anh/        ← Vault học Tiếng Anh (Mục tiêu: TOEIC 650+)
└── v.v...            ← Tạo thêm bằng /brain-setup
```

**Nguyên tắc:** Mỗi Vault là một "cỗ máy học tập" độc lập. Mỗi lần bạn mở một Vault, Claude sẽ biến thành một Tutor có nhân cách riêng cho chủ đề đó.

---

## 2. Ba lệnh để vận hành toàn bộ hệ thống

### 🟢 Bắt đầu / Tiếp tục buổi học
```
/learn-continue [tên-project]
```

**Cách dùng:**
- `/learn-continue` — Không tham số, học kiến thức chính của Vault (VD: GCP).
- `/learn-continue learn-terraform-gcp` — Học project con Terraform.
- `/learn-continue learn-golang` — Nếu có project con Golang.

**Cơ chế (Implicit Execution — Chạy ngầm, không báo cáo khô khan):**
1. Claude scan bộ nhớ: file log, file Reasoning-Gaps.
2. Kiểm tra Due Reviews (khái niệm sắp quên). Nếu có tối đa 3 items, test bằng câu hỏi Socratic.
3. Ngay lập tức chuyển sang Teaching Mode bằng câu hỏi dẫn dắt.

> ⚠️ **Lưu ý:** Nếu bạn quên gõ lệnh này và hỏi thẳng, Claude vẫn sẽ tự động chạy bước 1-2 trước khi trả lời (do cơ chế Session Re-entry trong Hiến pháp).

---

### 🟡 Tổng kết / Lưu lại buổi học
```
/day-update
```
Gõ vào cuối buổi. Claude sẽ:
1. Hỏi 1-2 câu Feynman để bạn tự chốt kiến thức.
2. Ghi lại vào Session Log.
3. Cập nhật lịch ôn tập cho các khái niệm đã học (Spaced Repetition).

---

### ⚡ Tạo một Vault học tập hoàn toàn mới
```
/brain-setup
```
Claude sẽ phỏng vấn 4 câu hỏi, sau đó tự động tạo một thư mục mới cùng cấp với `GCP/` và copy toàn bộ framework (skills, templates, hiến pháp) sang.

---

## 3. File cốt lõi trong mỗi Vault

| File | Vai trò | Ai dùng |
|---|---|---|
| `CLAUDE.md` | Hiến pháp của Vault — định nghĩa cách dạy và luật lệ | Claude (bắt buộc đọc) |
| `01 Journals/Session-Log.md` | Nhật ký buổi học, ghi chép Feynman | Bạn + Claude |
| `04 Reviews/Reasoning-Gaps.md` | Theo dõi lỗ hổng kiến thức + lịch ôn tập | Claude đọc để review |
| `05 Skills/day-update.md` | Lệnh tổng kết cuối ngày | Claude đọc và thực thi |
| `05 Skills/learn-continue.md` | Lệnh nạp bộ nhớ đầu buổi | Claude đọc và thực thi |
| `05 Skills/brain-setup.md` | Lệnh tạo Vault mới | Claude đọc và thực thi |

---

## 4. Knowledge State (Trạng thái hiểu biết)

Mỗi khái niệm trong `Reasoning-Gaps.md` được gắn một trạng thái:

| Status | Ý nghĩa | Lịch ôn tiếp theo |
|---|---|---|
| `[Exposed]` | Mới nghe qua, chưa hiểu | 1 ngày |
| `[Partial]` | Hiểu một nửa, còn vấp | 3 ngày |
| `[Understood]` | Pass Feynman check, giải thích trôi chảy | 7 ngày |
| `[Mastered]` | Review đúng 3 lần liên tiếp | 14+ ngày |

Nếu review sai → giảm 1 bậc.

---

## 5. Start Guide (Lần đầu vào Vault mới)

1. Mở terminal: `cd "C:\Users\minhdd_resolve\Desktop\WorkSpace\gcp-document\[Tên-Vault]"`
2. Gõ: `claude`
3. Gõ: `/learn-continue` để bắt đầu.

Không cần config gì thêm. Mọi thứ đã được tự động hóa.

---

## 6. Lưu ý vận hành

- **Luôn gõ `/day-update` trước khi tắt chat.** Nếu quên, kiến thức buổi học hôm đó sẽ không được lưu vào lịch ôn tập.
- **Không sợ hỏi sai.** Phương pháp Error-driven learning của Claude cố tình để bạn mắc lỗi để nhớ lâu hơn.
- **Nếu Claude trở nên "kỹ thuật" quá mức**, hãy nói: *"Socratic"* — nó sẽ hiểu và đặt câu hỏi dẫn dắt lại.

---

## 7. Triết lý đằng sau

```
Con người (Bạn)       = Learning Orchestrator (điều phối, chọn chủ đề)
AI (Claude)            = Reasoning Engine (suy luận, đặt câu hỏi)
Obsidian Vault         = Persistent Memory (bộ nhớ dài hạn)
```

Bạn không cần một AI trả lời nhanh. Bạn cần một AI **nhớ mày sai ở đâu, và ép mày không được quên.**
