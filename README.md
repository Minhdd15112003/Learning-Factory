# Learning Factory — Hệ thống học tập thích nghi

> **Mục đích:** Biến Claude từ một cái máy trả lời câu hỏi thành một **gia sư cá nhân** có trí nhớ dài hạn, phương pháp dạy học khoa học, và kỷ luật thép.

> **Cài đặt (clone về máy mới):** xem `SETUP.md`. Lưu ý: `.obsidian/` không nằm trong git, nên người tải về phải cài lại plugin (Spaced Repetition; Claudian là tùy chọn).

---

## 1. Kiến trúc: MỘT vault, NHIỀU môn học (phẳng)

Toàn bộ `Learning-Factory/` là **một** vault Obsidian và **một** project Claude (git root). Mỗi chủ đề học là một **môn** — một thư mục cấp 1, ngang hàng nhau. Không phân tầng, không "project con".

```
Learning-Factory/                ← vault + git root
├── CLAUDE.md                ← Hiến pháp dùng chung (framework dạy học)
├── .obsidian/  .claude/  .claudian/   ← config + plugin + 3 lệnh, dùng chung
│
├── GCP/                     ← môn "Google Cloud" (Mục tiêu: ACE)
├── Terraform-GCP/           ← môn "Terraform trên GCP" (IaC)
├── DevOps/                  ← môn "DevOps / Kubernetes"
│        ├── CLAUDE.md       ← nhận dạng + trạng thái (mỏng)
│        ├── GOALS.md        ← mục tiêu + lộ trình (tiếng Việt)
│        ├── 00 Theory/      ← note lý thuyết + lịch ôn (SR)
│        ├── 01 Practice/    ← thực hành
│        ├── 02 Output/      ← kết quả
│        ├── 03 Journals/    ← log mỗi buổi
│        └── 04 Reviews/     ← Reasoning-Gaps
│
└── (môn khác, ...)          ← tạo bằng /new-learn
```

**Kế thừa:** khi chạy Claude trong một môn, `CLAUDE.md` gốc + `CLAUDE.md` của môn được **gộp tự động**. Mỗi buổi học chỉ đụng vào MỘT môn — không đọc/ghi chéo sang môn khác.

> **Mở vault trong Obsidian ở thư mục `Learning-Factory/`** (không phải `GCP/`). Mọi môn nằm chung một graph, dùng chung plugin Spaced Repetition.

---

## 2. Ba lệnh vận hành

### Bắt đầu / tiếp tục buổi học
```
/learn [tên-môn]
```
Claude: nạp log + Reasoning-Gaps + lịch ôn (sr-due), ôn Socratic các note đến hạn (tối đa 3, tự chấm), rồi mở bài mới bằng câu hỏi dẫn dắt. Môn mới chưa có note → chạy **đánh giá đầu vào** theo roadmap rồi mới dạy. Mỗi topic đi theo **Lý thuyết → Thực hành → Output**.

### Kết thúc / lưu buổi học
```
/done
```
Cuối buổi: phỏng vấn Feynman, chấm điểm, cập nhật lịch ôn (SR) trong frontmatter note, ghi Session Log + Current Status.

### Tạo một môn mới
```
/new-learn [tên]
```
Phỏng vấn ngắn (tên, mục tiêu, điểm yếu, roadmap thô), rồi dựng thư mục môn mới ngang hàng + `CLAUDE.md` mỏng + `GOALS.md`. Không cần cài lại gì — dùng chung ở root.

---

## 3. Quy ước ngôn ngữ & xưng hô

| Loại file | Ngôn ngữ |
|---|---|
| `CLAUDE.md`, các lệnh trong `.claude/skills/` | Tiếng Anh (cho AI hiểu chính xác) |
| Câu trả lời chat | Tiếng Việt — Claude gọi **"bạn"**, tự xưng **"mình"** |
| Note lý thuyết, Session Log, `GOALS.md`, review | Tiếng Việt (bạn đọc lại để ôn) |
| `Reasoning-Gaps.md` | Mô tả tiếng Việt + cấu trúc tiếng Anh |

---

## 4. Knowledge State

| Status | Ý nghĩa |
|---|---|
| `Exposed` | Mới nghe qua |
| `Partial` | Hiểu một phần, nói được kết quả |
| `Understood` | Pass Feynman — giải thích được cơ chế |
| `Mastered` | Ôn đúng 3 lần liên tiếp |

Lên `Understood` bắt buộc giải thích được "cơ chế / tại sao". Review trượt (Hard) trên note `Understood` sẽ tụt về `Partial`.

Lịch ôn lưu ở frontmatter (`sr-due / sr-interval / sr-ease`), nhưng việc ôn chạy **qua Claude** ở `/learn` — Claude tự chấm theo Feynman, không bấm Easy/Good/Hard trong plugin.

---

## 5. Bắt đầu nhanh

1. Mở Obsidian, mở vault ở `Learning-Factory/`. Bật plugin **Spaced Repetition** rồi reload.
2. Chạy Claude theo MỘT trong hai cách:
   - **CLI:** mở terminal trong môn muốn học rồi chạy `claude`:
     ```
     cd "C:\Users\minhdd_resolve\Desktop\WorkSpace\Learning-Factory\GCP"
     claude
     ```
     Rồi gõ `/learn` (không cần tên — cwd đã là môn).
   - **Plugin Claudian (trong Obsidian):** plugin luôn lấy gốc vault làm cwd, nên nêu tên môn: `/learn GCP`.

---

## 6. Lưu ý vận hành

- **Luôn gõ `/done` trước khi tắt.** Quên thì kiến thức hôm đó không vào lịch ôn.
- **Không sợ hỏi sai.** Claude cố tình để bạn vấp lỗi (error-driven learning) để nhớ lâu hơn.
- **Claude "kỹ thuật" quá** thì nói _"Socratic"_; còn bí quá thì nói _"giải thích trước đi"_ — với cơ chế mới Claude sẽ giới thiệu ngắn rồi mới hỏi.

---

## 7. Triết lý

```
Bạn            = Learning Orchestrator (điều phối, chọn chủ đề)
Claude         = Reasoning Engine (suy luận, đặt câu hỏi)
Obsidian Vault = Persistent Memory (bộ nhớ dài hạn)
```

Bạn không cần một AI trả lời nhanh. Bạn cần một AI **nhớ bạn sai ở đâu, và ép bạn không được quên.**
