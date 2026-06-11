# Skill: brain-setup

**Trigger:** user types `/brain-setup`

**Purpose:** Create a new **brain** — a top-level folder inside this single shared vault. The new brain inherits the whole framework automatically (the base `CLAUDE.md`, `05 Skills/`, `.obsidian/` plugins incl. Spaced Repetition, `.claude/`) because all of that lives at the vault/git root and is shared. brain-setup therefore does NOT copy plugins, skills, or config — it only scaffolds the new domain's folders and its own thin `CLAUDE.md`.

**Language reminder:** Procedure text is English (AI-facing). Questions to the user in Step 1 may be phrased in Vietnamese (the user reads them live). Generated theory/journal content is Vietnamese; the brain `CLAUDE.md` is English.

---

## Step 0 — Locate the vault root

The vault/git root is the PARENT of the current brain (your cwd). Example: cwd `.../gcp-document/GCP` → vault root `.../gcp-document`. New brains are created as direct children of the vault root, as siblings of `GCP/`.

Do NOT create a new `.obsidian`, `.claude`, `.claudian`, or `05 Skills` for the brain — these are shared at the vault root and resolve automatically.

---

## Step 1 — Interview (Brain Identity)

Ask ONE question at a time, in order, waiting for each answer:

1. **Name:** "Brain mới tên là gì? Ví dụ: `Tieng-Anh`, `Learn-Golang`. Tên này thành tên thư mục."
2. **Ultimate goal:** "Mục tiêu tối thượng của brain này là gì? Ví dụ: 'TOEIC 650+', 'Trở thành Backend Dev bằng Go'."
3. **Weaknesses / blind spots:** "Điểm yếu hoặc điểm mù của mày trong lĩnh vực này là gì?"
4. **Planned sub-projects:** "Có dự định tạo sub-project nào trong này không? (có thể để trống)"

Keep the four answers for Step 3.

---

## Step 2 — Create the brain folder structure

```bash
VAULT_ROOT="C:/Users/minhdd_resolve/Desktop/WorkSpace/gcp-document"
BRAIN="$VAULT_ROOT/[BRAIN-NAME]"
mkdir -p \
  "$BRAIN/00 Notes" \
  "$BRAIN/01 Journals" \
  "$BRAIN/02 Chess Moves (Long-Term Planning)" \
  "$BRAIN/03 Projects" \
  "$BRAIN/04 Reviews"
```

Replace `[BRAIN-NAME]` with the Step 1 Q1 answer. Note: no `05 Skills/` folder (shared at root).

---

## Step 3 — Write the brain's `CLAUDE.md` (thin, English)

The brain inherits the base constitution automatically, so this file is short — only domain identity, goals, structure, projects. Create `$BRAIN/CLAUDE.md`:

```markdown
# [BRAIN-NAME] — Brain Constitution

> Inherits the shared framework from `../CLAUDE.md` (auto-merged on launch). This file holds only [DOMAIN]-specific identity, goals, structure, and projects. For the Learning Mode Contract, Knowledge Model, Spaced Repetition, Language Conventions, and Operational Protocols, see the base constitution one level up.

## Domain & Purpose
This brain stores and builds [DOMAIN] knowledge toward: [ULTIMATE-GOAL]. Claude is a Learning-mode tutor here (see the base contract). Replies to the user are in Vietnamese.

## Goals (summary — full doc in `GOALS.md`, Vietnamese)
- Goal: [ULTIMATE-GOAL]
- Now: Starting from zero — first session not yet run.
- Mode: own pace, no time pressure unless noted.

## Folder Structure (this brain)
\```
[BRAIN-NAME]/
├── CLAUDE.md       ← this file (domain)
├── GOALS.md        ← goals & roadmap (Vietnamese)
├── 00 Notes/       ← cross-cutting theory notes
├── 01 Journals/    ← session logs + Session-Log-Template
├── 02 Chess Moves (Long-Term Planning)/
├── 03 Projects/    ← sub-projects (each with its own CLAUDE.md)
└── 04 Reviews/     ← Reasoning-Gaps.md + weekly reviews
\```
Shared framework (.obsidian, .claude, .claudian, 05 Skills/) lives at the vault root, one level up.

## Who I Am (domain-specific)
Weaknesses / blind spots in [DOMAIN]: [WEAKNESSES]. (Universal user traits are in the base constitution.)

## Weekly Update
> **Last updated:** [TODAY]
- What's working:
- What's not working:
- What I'm sitting on / need to decide:
- What I'm feeling pulled toward:
- Any deadlines or time-sensitive things:

## Projects & Overviews
[SUB-PROJECTS: add a short status line for each once created via /new-project; if none yet, write "None yet."]
```

Fill placeholders: `[BRAIN-NAME]` (Q1), `[ULTIMATE-GOAL]` (Q2), `[WEAKNESSES]` (Q3), `[SUB-PROJECTS]` (Q4), `[DOMAIN]` (subject implied by the name), `[TODAY]` (current date YYYY-MM-DD).

---

## Step 4 — Seed the Vietnamese GOALS.md and templates

**`$BRAIN/GOALS.md`** (Vietnamese): write the ultimate goal, current position (chưa bắt đầu), and a rough roadmap based on the interview. Use `[[wiki-links]]` for key concepts.

**`$BRAIN/01 Journals/Session-Log-Template.md`** (Vietnamese):
```markdown
# Session Log: {{date}}

> **Tham chiếu:** [[[BRAIN-NAME]/CLAUDE.md|Constitution]] | [[[BRAIN-NAME]/04 Reviews/Reasoning-Gaps.md|Reasoning Gaps]]

## Baseline Check (đầu buổi)
*Claude hỏi 1 câu về concept của buổi trước. Ghi lại:*
- Câu hỏi: 
- Trả lời của mình: 
- Đánh giá: [ ] Mechanism-level  [ ] Result-only  [ ] Không nhớ

## Mục tiêu buổi học
- [ ] 

## Quá trình (Claude làm Reasoning Engine)
*Ghi chú nhanh khái niệm, lệnh, hoặc luồng suy nghĩ.*

- 

## Explain-back (Feynman Check)
*Tự giải thích lại bằng ngôn ngữ của mình.*

- 

## Misconceptions & Gaps (Ghi nhận lỗi)
- 
```

**`$BRAIN/04 Reviews/Reasoning-Gaps.md`**:
```markdown
# Reasoning Gaps Log

> Logs significant misconceptions and reasoning failures discovered during learning sessions.
> Nơi ghi lại các hiểu lầm và lỗ hổng suy luận phát hiện trong quá trình học.

## Active Gaps

(Trống)

## Resolved Gaps

(Trống)

---

> Note to Claude: when a session reveals a significant misconception, log it here in Vietnamese; if it is substantial, promote it into a theory note (you author it) so the SR plugin schedules it. Reviewing happens via /learn-continue, not the plugin UI.
```

---

## Step 5 — Register the brain

1. Open the base `$VAULT_ROOT/CLAUDE.md`, find the `Brains:` list under *Vault Architecture*, and add a line: `- \`[BRAIN-NAME]/\` — [one-line domain description].`
2. Open `$VAULT_ROOT/README.md` and add the new brain to the architecture tree in section 1.

---

## Step 6 — Handover

Tell the user (in Vietnamese):

"Brain mới `[BRAIN-NAME]` đã tạo xong trong vault (`gcp-document/[BRAIN-NAME]/`). Không cần cài plugin hay config gì thêm — mọi thứ dùng chung ở root.

Để bắt đầu:
1. Mở terminal trong brain mới:
   ```
   cd "[VAULT_ROOT]/[BRAIN-NAME]"
   claude
   ```
2. Gõ `/learn-continue` để Claude nạp bối cảnh và mở buổi học đầu tiên.

Trong Obsidian (đã mở vault ở `gcp-document/`), brain mới hiện ra như một thư mục — plugin Spaced Repetition tự nhận các note có tag `review`."

**Post-handover check:** `ls "[VAULT_ROOT]/[BRAIN-NAME]"` to confirm the folders + files exist. Report only if something is missing.
