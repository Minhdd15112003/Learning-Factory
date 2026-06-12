# Skill: New Project

Create a new sub-project inside the CURRENT brain's `03 Projects/` by interviewing the user, then scaffolding the folder tree, the project `CLAUDE.md`, and `COMMANDS.md` from scratch. No template copy — every project is built fresh and consistent with the learning-vault structure.

**Context (Option A architecture):** This vault is one shared Obsidian vault / git project at the root. Each top-level folder is a **brain** (e.g. `GCP/`). A sub-project lives under a brain's `03 Projects/`. Run this skill from inside the brain (your cwd is the brain). The shared framework — `.obsidian/` plugins, `.claude/`, `05 Skills/`, and the base `CLAUDE.md` — lives at the vault root and is inherited automatically. A sub-project therefore needs NO config, NO plugin install, and NO `05 Skills/` of its own.

**CLAUDE.md inheritance is three levels:** base (`<vault-root>/CLAUDE.md`) → brain (`<brain>/CLAUDE.md`) → this sub-project's `CLAUDE.md`. All ancestors merge automatically when Claude is launched in the sub-project directory (parent-walk to the git root). Do NOT instruct Claude to manually re-read parent constitutions.

**Launch context:** this skill assumes cwd = the brain. If you were launched at the vault root (Claudian plugin — cwd has no `01 Journals/`), first ask which brain, set `BRAIN` to it, and prefix every `mkdir`/path below with `<brain>/` (e.g. `<brain>/03 Projects/PROJECT/...`).

## How It Works

1. Interview the user one question at a time (up to 6 questions)
2. Scaffold the folder tree with `mkdir -p`
3. Seed the bootstrap files (project `CLAUDE.md`, `COMMANDS.md`, Session-Log-Template, Reasoning-Gaps)
4. Register the project in the BRAIN's `CLAUDE.md` (the cwd brain, not the vault-root base)
5. If the user has no answer for a question yet, use a sensible default and leave a `<!-- TODO: fill this in -->` comment

## Interview Questions (Ask One at a Time)

Ask conversationally (questions may be phrased in Vietnamese). Each answer can shape the next. If someone says "I'm not sure yet" or gives a partial answer, do not push — work with what you have.

### 1. What is the project called?
Used for the folder name under `03 Projects/`.

### 2. What is this project?
One paragraph. What are we building, doing, or learning? What is the subject matter?

### 3. What does "done" look like?
The concrete end goal. This becomes the prime directive in `CLAUDE.md` — the thing Claude nudges toward when a session drifts.

### 4. Who else is involved?
Key people and their roles. "Just me" is valid.

### 5. What is the learning/work process from start to finish?
How work flows from start to finish. This shapes the numbered subfolders.

**If they are not sure:** Use the default learning structure (mirrors learn-terraform-gcp):
- `01 Journals/` — session logs
- `01 Ly thuyet/` — theory notes (Vietnamese)
- `02 Thuc hanh/` — hands-on practice
- `03 Output/` — results and deliverables
- `04 Reviews/` — misconception log

### 6. Any rules or conventions Claude should follow?
Always/never rules, formats, secret handling. Optional.

---

## After the Interview

Let `BRAIN` = the current brain folder name (your cwd's folder name, e.g. `GCP`) and `PROJECT` = the project name from Q1. These are needed for correct wiki-link paths (links resolve from the vault root, so they must include `BRAIN/03 Projects/PROJECT/...`).

### Step 1: Create the project folder tree

Use `mkdir -p` (run from the brain root = cwd). No template copy.

```bash
mkdir -p "03 Projects/PROJECT/01 Journals"
mkdir -p "03 Projects/PROJECT/01 Ly thuyet"
mkdir -p "03 Projects/PROJECT/02 Thuc hanh"
mkdir -p "03 Projects/PROJECT/03 Output"
mkdir -p "03 Projects/PROJECT/04 Reviews"
```

Add or rename folders based on the Q5 process answer. The above is the default. Do NOT create a `05 Skills/` (skills are shared at the vault root).

### Step 2: Seed bootstrap files (write inline — do not copy)

Write these with references scoped to THIS sub-project (so wiki-links resolve correctly from the vault root).

**`03 Projects/PROJECT/01 Journals/Session-Log-Template.md`** (Vietnamese):
```markdown
# Session Log: {{date}}

> **Tham chiếu:** [[BRAIN/03 Projects/PROJECT/CLAUDE.md|Constitution]] | [[BRAIN/03 Projects/PROJECT/04 Reviews/Reasoning-Gaps.md|Reasoning Gaps]]

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
(Replace `BRAIN` and `PROJECT` with the real names.)

**`03 Projects/PROJECT/04 Reviews/Reasoning-Gaps.md`**:
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

### Step 3: Write the project `CLAUDE.md` (English, thin)

The base + brain constitutions merge automatically, so this file holds ONLY project-specific rules. Use `<!-- TODO: fill this in -->` for anything the user was unsure about.

```markdown
# PROJECT — Project Constitution

> Inherits automatically (on launch, via parent-walk to the git root) from the brain CLAUDE.md and the vault-root base CLAUDE.md — no manual read needed. This file adds ONLY project-specific rules — the Learning Mode Contract, Knowledge Model, Spaced Repetition, Language Conventions, and Operational Protocols already apply from the ancestors. Do not duplicate them here.

[One paragraph: what this project is about, from Q2]

## Claude's Role (Project-specific)

[What Claude does in this project — the actual job, not "help me learn".]

If a session is drifting without moving toward [the goal from Q3], nudge the user back (in Vietnamese): "[contextual nudge message]"

## Process

1. **Ly thuyet:** Learn concepts in `01 Ly thuyet/`.
2. **Thuc hanh:** Apply hands-on in `02 Thuc hanh/`.
3. **Output:** Record results in `03 Output/`.

[Adjust to the Q5 answer if given.]

## Key People

[From Q4. "Name — role". Skip section if solo.]

## Rules & Conventions (Project-specific)

- **(C) prefix** — Files created by Claude are prefixed with `(C)`.
- **Editing rule** — Before editing any file without the `(C)` prefix, ask first.
[Add project-specific rules from Q6.]

## Folder Structure

\```
PROJECT/
├── CLAUDE.md          <- this file
├── COMMANDS.md        <- quick reference (English commands, Vietnamese notes)
├── 01 Journals/       <- Session Log & Feynman check
├── 01 Ly thuyet/      <- Theory notes (Vietnamese)
├── 02 Thuc hanh/      <- Hands-on practice
├── 03 Output/         <- Results and deliverables
└── 04 Reviews/        <- Reasoning-Gaps.md
\```

## Current Status

> **Last updated:** [today's date]
> **Status:** Just created — getting started.

<!-- TODO: Update this as the project progresses -->
```

### Step 4: Write `COMMANDS.md`

Convention: command lines in English, explanations in Vietnamese. List the shared vault skills too (they live in the vault-root `05 Skills/`).

```markdown
# Commands & Skills — PROJECT

Bảng tra nhanh lệnh & skill cho project này.

## Project Commands
_Chưa có lệnh riêng cho project._

## Vault Skills (shared, defined in vault-root `05 Skills/`)

| Skill | Giải thích |
|---|---|
| `/learn-continue PROJECT` | Học ĐÚNG sub-project này: nạp context, kiểm tra note đến hạn ôn (sr-due), chạy Socratic review, mở bài mới. **Phải nêu tên sub-project** — từ Claudian (cwd = vault root) dùng `/learn-continue BRAIN PROJECT`. Chạy `/learn-continue` không kèm tên ở cấp brain CHỈ ôn note của brain, KHÔNG đụng tới note của sub-project này (mỗi scope tách biệt). |
| `/day-update` | Kết thúc buổi học: phỏng vấn Feynman, cập nhật lịch ôn (SR), ghi Session Log — ghi vào đúng scope của buổi học. |
```

### Step 5: Register the project in the BRAIN's `CLAUDE.md`

Update the CURRENT brain's `CLAUDE.md` (the cwd brain, e.g. `GCP/CLAUDE.md`) — NOT the vault-root base `CLAUDE.md` (the base only lists brains, not sub-projects).

**1. Folder Structure** — add the project under the brain's `03 Projects/` tree:
```markdown
│   └── PROJECT/
```

**2. Projects & Overviews** — add a subsection:
```markdown
### PROJECT — `03 Projects/PROJECT/`
**Status:** Just created
[One-line description from Q2]
Project-specific rules live in the sub-project's `CLAUDE.md` and merge automatically when Claude is launched in that directory.
```

Only edit these two sections of the brain's `CLAUDE.md`. Do not touch the base constitution.

### Step 6: Confirm to the user (in Vietnamese)

Tell them: the folder tree created, a summary of the project `CLAUDE.md`, that they can restructure anytime, and call out any `<!-- TODO -->` sections. Remind them to launch Claude from inside the project folder (`cd "03 Projects/PROJECT" && claude`) so all three constitution levels merge.
