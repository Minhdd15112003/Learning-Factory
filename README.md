# Learning Factory — Hệ thống học tập thích nghi

> **Mục đích:** Biến Claude từ một cái máy trả lời câu hỏi thành một **gia sư cá nhân** có trí nhớ dài hạn, phương pháp dạy học khoa học, và kỷ luật thép.

> **Cài đặt (clone về máy mới):** xem `SETUP.md` — hướng dẫn viết cho một AI agent tự cài. Lưu ý quan trọng: `.obsidian/` không nằm trong git, nên người tải về phải cài lại plugin (Spaced Repetition; Claudian là tùy chọn). `SETUP.md` xử lý việc đó cùng việc chỉnh đường dẫn và chọn giữ/đặt lại nội dung.

---

## 1. Kiến trúc: MỘT vault, NHIỀU "brain"

Toàn bộ `Learning-Factory/` là **một** vault Obsidian và **một** project Claude (git root). Mỗi chủ đề học là một **brain** — một thư mục cấp 1 bên trong, có hiến pháp riêng nhưng dùng chung hạ tầng.

```
Learning-Factory/                ← vault + git root
├── CLAUDE.md                ← Hiến pháp NỀN (framework dạy học, dùng chung)
├── .obsidian/  .claude/  .claudian/   ← config + plugin dùng chung
├── 05 Skills/               ← các skill dùng chung (learn-continue, day-update...)
│
├── GCP/                     ← brain "Google Cloud" (Mục tiêu: ACE)
│   ├── CLAUDE.md            ← hiến pháp riêng domain GCP
│   └── 03 Projects/learn-terraform-gcp/   ← project con (có CLAUDE.md riêng)
│
├── terraform-gcp/           ← brain "terraform-gcp" (Mục tiêu: Sử dụng thành thạo Terraform kết hợp GCP)
│   └── CLAUDE.md            ← hiến pháp riêng domain terraform-gcp
│
└── (Tieng-Anh/, ...)        ← brain khác, tạo bằng /brain-setup
```

**Nguyên tắc kế thừa (quan trọng):** khi bạn chạy Claude trong một brain, các file `CLAUDE.md` được **gộp tự động từ gốc xuống**: NỀN → brain → project con. Cha truyền luật xuống con, không phải copy tay. Một "brain" và một "project con" về bản chất chỉ là thư mục ở các tầng khác nhau, đều có `CLAUDE.md` riêng.

> **Mở vault trong Obsidian ở thư mục `Learning-Factory/`** (không phải `GCP/`). Tất cả brain nằm trong một graph, dùng chung plugin (gồm Spaced Repetition).

---

## 2. Ba lệnh vận hành

### Bắt đầu / Tiếp tục buổi học

```
/learn-continue [tên-project-con]
```

- `/learn-continue` — học kiến thức chính của brain đang đứng (theo thư mục bạn chạy `claude`).
- `/learn-continue learn-terraform-gcp` — vào project con.

Claude sẽ: nạp log + Reasoning-Gaps + lịch ôn (sr-due), hỏi 1 câu baseline, test Socratic các note đến hạn (tối đa 3), rồi mở bài mới bằng câu hỏi dẫn dắt — không báo cáo khô khan.

### Tổng kết / Lưu buổi học

```
/day-update
```

Gõ cuối buổi: phỏng vấn Feynman, chấm điểm, cập nhật lịch ôn (SR) trong frontmatter các note, ghi Session Log.

### Tạo một brain mới

```
/brain-setup
```

Phỏng vấn 4 câu, rồi tạo một **thư mục brain mới** cùng cấp với `GCP/` (cùng vault). Không cần cài lại plugin hay copy config — đã dùng chung ở root.

---

## 3. Quy ước ngôn ngữ

| Loại file                               | Ngôn ngữ                               |
| --------------------------------------- | -------------------------------------- |
| `CLAUDE.md`, `05 Skills/*`              | Tiếng Anh (cho AI hiểu chính xác)      |
| Câu trả lời chat                        | Tiếng Việt                             |
| Note lý thuyết, Session Log, `GOALS.md` | Tiếng Việt (bạn đọc lại để ôn)         |
| Weekly review (`04 Reviews/`)           | Tiếng Việt                             |
| `Reasoning-Gaps.md`                     | Mô tả tiếng Việt + cấu trúc tiếng Anh  |
| `COMMANDS.md`                           | Lệnh tiếng Anh + giải thích tiếng Việt |

---

## 4. Knowledge State

| Status       | Ý nghĩa                                     |
| ------------ | ------------------------------------------- |
| `Exposed`    | Mới nghe qua                                |
| `Partial`    | Hiểu một phần, nói được kết quả             |
| `Understood` | Pass Feynman check — giải thích được cơ chế |
| `Mastered`   | Ôn đúng 3 lần liên tiếp                     |

Lên cấp `Understood` bắt buộc giải thích được "cơ chế/tại sao". Review trượt (Hard) trên note `Understood` sẽ tụt về `Partial`.

Lịch ôn do **plugin Obsidian Spaced Repetition** giữ (frontmatter `sr-due / sr-interval / sr-ease`), nhưng việc ôn chạy **qua Claude** ở `/learn-continue` — Claude tự chấm theo Feynman, không bấm Easy/Good/Hard trong plugin.

---

## 5. Start Guide

1. Mở Obsidian, mở vault ở thư mục `Learning-Factory/`. Bật plugin **Spaced Repetition** (nếu chưa) rồi reload.
2. Chạy Claude theo MỘT trong hai cách:
   - **CLI (ổn định):** mở terminal trong brain muốn học rồi chạy `claude`:
     ```
     cd "C:\Users\minhdd_resolve\Desktop\WorkSpace\Learning-Factory\GCP"
     claude
     ```
   - **Plugin Claudian (trong Obsidian):** chạy Claude ngay trong Obsidian. Plugin luôn lấy _gốc vault_ (`Learning-Factory/`) làm thư mục làm việc (không trỏ vào folder con được), nên khi gọi hãy nêu tên brain: ví dụ `/learn-continue GCP`. Claude sẽ tự đọc `GCP/CLAUDE.md` và dùng path có tiền tố `GCP/`.
     Dù chạy cách nào, ngữ cảnh làm việc phải là folder brain để skill-path (`01 Journals/`, `04 Reviews/`...) và kế thừa CLAUDE.md hoạt động đúng.
3. Gõ `/learn-continue` để bắt đầu.

---

## 6. Lưu ý vận hành

- **Luôn gõ `/day-update` trước khi tắt.** Quên thì kiến thức hôm đó không vào lịch ôn.
- **Không sợ hỏi sai.** Claude cố tình để bạn vấp lỗi (error-driven learning) để nhớ lâu hơn.
- **Claude "kỹ thuật" quá** thì nói _"Socratic"_ — nó quay lại đặt câu hỏi dẫn dắt.

---

## 7. Triết lý

```
Bạn            = Learning Orchestrator (điều phối, chọn chủ đề)
Claude         = Reasoning Engine (suy luận, đặt câu hỏi)
Obsidian Vault = Persistent Memory (bộ nhớ dài hạn)
```

Bạn không cần một AI trả lời nhanh. Bạn cần một AI **nhớ bạn sai ở đâu, và ép bạn không được quên.**
