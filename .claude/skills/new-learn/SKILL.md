---
description: Create a new subject — a top-level learning folder. Short interview, then scaffold the flat folder tree, a thin CLAUDE.md, and a Vietnamese GOALS.md. Use for /new-learn [name], or when the user wants to start learning a new topic.
argument-hint: "[name]"
---

The user invoked `/new-learn`. Create a new subject per below. Ask the interview questions in Vietnamese ("bạn" / "mình"); the generated `CLAUDE.md` is English; `GOALS.md` is Vietnamese. The base `CLAUDE.md` is in context.

Raw argument (proposed name): $ARGUMENTS

## 1. Interview (one question per turn)
If the argument gave a name, use it for Q1 and skip asking it. Otherwise ask, one at a time:
1. **Name** — becomes the folder name (e.g. `Learn-Golang`, `Tieng-Anh`).
2. **Ultimate goal** — e.g. "vận hành được production cluster trên Kubernetes".
3. **Blind spots** — where the user is weak in this subject.
4. **Rough roadmap / first topics** — can be loose; it will be refined after the first sessions.

## 2. Scaffold the flat folder tree
Run from the vault root. If cwd is a subject folder, prefix paths with `../`. First check the name is free: `ls "<Name>" 2>/dev/null` — if the folder already exists, stop and ask whether to pick a new name or work inside the existing subject.
```bash
S="<Name>"
mkdir -p "$S/00 Theory" "$S/01 Practice" "$S/02 Output" "$S/03 Journals" "$S/04 Reviews"
```

## 3. Write `<Name>/CLAUDE.md` (thin, English)
```markdown
# <Name> — Subject

> Inherits the vault-root `CLAUDE.md` (Teaching Contract, Knowledge Model, Spaced Repetition, Language) automatically. This file holds only identity + status.

Learning <subject> toward: <ultimate goal>. Claude is a Socratic tutor here; replies in Vietnamese ("bạn" / "mình").
Blind spots: <from Q3>.

Flow per topic: Lý thuyết (`00 Theory/`) → Thực hành (`01 Practice/`) → Output (`02 Output/`).

## Current Status
> Last updated: <today, YYYY-MM-DD>
- First session not yet run.
```

## 4. Write `<Name>/GOALS.md` (Vietnamese)
Ultimate goal, current position ("chưa bắt đầu"), and the rough roadmap from Q4 as a checklist, with `[[wiki-links]]` on key concepts.

## 5. Seed `<Name>/04 Reviews/Reasoning-Gaps.md`
```markdown
# Reasoning Gaps Log

> Nơi ghi lại các hiểu lầm và lỗ hổng suy luận phát hiện trong quá trình học.

## Active Gaps
(Trống)

## Resolved Gaps
(Trống)
```

## 6. Register + handover
1. Append a one-line entry to the `Subjects:` list in the vault-root `CLAUDE.md`: `- \`<Name>/\` — <one-line description>.` Append ONLY that one bullet under `Subjects:` — do not modify any other part of the root `CLAUDE.md` (it is otherwise protected framework).
2. Tell the user (Vietnamese): the folder is ready; run `/learn <Name>` to start — the first session runs a short placement assessment, then teaches. Confirm the folders exist (`ls "<Name>"`) and report only if something is missing.
