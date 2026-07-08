---
description: Close a learning session — Feynman-interview today's concept(s), update each note's status and spaced-repetition schedule, write the journal, and update Reasoning-Gaps and Current Status. Use for /done, or "kết thúc buổi học" / "tổng kết hôm nay".
---

The user invoked `/done`. Close the session per below. Reply in Vietnamese, address the user as "bạn", refer to yourself as "mình". Don't narrate what you read — go straight to the Feynman question. The base `CLAUDE.md` is in context.

## 1. Resolve the subject
Determine the subject: if `/learn` ran earlier in this conversation and clearly named a subject, use it. Otherwise ALWAYS ask which subject today's session was under (`ls` the vault root and list the folders that contain a `CLAUDE.md`) — never assume. Prefix all paths with `<subject>/`. Touch only this subject; never write another subject's notes.

## 2. Check Feynman eligibility
Scan today's journal for `[FEYNMAN]` entries (set during `/learn` inline close). Every concept that was taught today and has a `[FEYNMAN]` line already has its note + SR schedule finalized — skip grading it here.

If **all** today's concepts have `[FEYNMAN]` entries → skip Feynman interview entirely. Mark the session as done in step 5 directly (the user already demonstrated understanding, and notes are already live).

If **no** `[FEYNMAN]` entries exist (e.g. `/learn` was never run, or the teaching flow was interrupted mid-concept), proceed with the standard Feynman interview in step 3. This is the fallback for when `/done` is called manually mid-session.

## 3. Feynman interview (only when needed)
Identify the 1–2 most important concepts from today (today's journal + the conversation). Ask 1–2 mechanism questions — how/why, never just what.
- **Correct + mechanism-level** → grade **Easy** (fluent, no real probing needed) or **Good** (correct but needed follow-up questions); promote `Partial`/`Exposed` → `Understood`; give ONE insight linking it to a broader pattern; move on.
- **Correct but result-only** → keep `Partial`; ask once "Đó là cái gì. Còn cơ chế thế nào?" — don't explain it for them.
- **Wrong** → follow the wrong logic to its contradiction before correcting; if the user is genuinely stuck (not holding an interesting wrong model), scaffold down to one smaller sub-question.
- **"Không biết"** → fine, don't push the user further; grade **Hard** (apply the SR formula: `ease − 20`, `interval × 0.5`); keep status at `Exposed`/`Partial`.

Record the user's ACTUAL words in the journal. Never fabricate understanding the user did not show.

## 4. Update the schedule
For each concept newly taught today that does NOT have a `[FEYNMAN]` line in the journal, grade it (Easy/Good/Hard) and update its note's `status / sr-due / sr-interval / sr-ease` per the base SR algorithm.
- **Do not re-process** notes already carrying `[REVIEW]` or `[FEYNMAN]` entries in today's journal. Re-applying the formula would compound the interval twice in one day.
- Write only to notes inside `<subject>/00 Theory/`. If a concept has no note yet, Claude writes one now (Vietnamese, full SR frontmatter using the base new-note defaults: `interval=1`, `ease=250`, `sr-due=today+interval`).

## 5. Reasoning-Gaps
Append any new misconception to `<subject>/04 Reviews/Reasoning-Gaps.md` (Vietnamese). If a gap is significant or recurring, promote it into a full theory note in `00 Theory/` (Claude authors it, with SR frontmatter). Move a resolved gap to the resolved section with today's date.

## 6. Close
1. Append the user's Feynman answers (if any were given in step 3) to today's journal under a `## Feynman Check` section. If concepts had `[FEYNMAN]` entries already, append a summary: today's topics covered and their grades.
2. Update the **Current Status** line in the subject's `CLAUDE.md`.
3. Confirm briefly in Vietnamese ("Đã lưu log và cập nhật lịch ôn."), then end on a forward-looking question or teaser for next session — never a passive recap. No emoji.
