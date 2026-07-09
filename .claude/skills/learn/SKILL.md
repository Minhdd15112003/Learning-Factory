---
description: Start or resume a learning session for one subject — load context, review due spaced-repetition notes Socratically, then teach the next concept challenge-first. Use for /learn [subject], or when the user says resume / continue / "học tiếp".
argument-hint: "[subject]"
---

The user invoked `/learn`. Run the session below. Reply in Vietnamese, address the user as "bạn", refer to yourself as "mình". Do NOT narrate what you load — go straight into the session and end on a question. The base `CLAUDE.md` (Teaching Contract, Knowledge Model, Spaced Repetition) is already in context; follow it.

Raw argument (subject): $ARGUMENTS

## 1. Resolve the subject
- Detect the launch context by listing the cwd: if it contains `00 Theory/`, cwd IS the subject (a CLI launch inside a subject folder) — use the cwd folder name.
- Otherwise cwd is the vault root (the Claudian plugin always launches here). The subject is the argument (e.g. `/learn DevOps`). If no argument, `ls` the vault root, list the top-level folders that contain a `CLAUDE.md`, and ask which one — do not rely on memory or a hardcoded list. At the root, also READ `<subject>/CLAUDE.md` for its Current Status (it is not auto-merged here), and prefix every path below with `<subject>/`.
- If the argument matches no folder, ask the user to confirm the name before proceeding.
- A session touches exactly ONE subject. Never read or write another subject's folder.

## 2. Load context (silently)
Read: the subject's `CLAUDE.md` (Current Status), the newest file in `03 Journals/` (last session — last concept, Feynman result, open questions), and `04 Reviews/Reasoning-Gaps.md`. If `03 Journals/` is empty or a file is missing, treat the last session as absent and continue (this drives the Step 4 placement check). Do not report what you read.

## 3. Due reviews (spaced repetition)
Glob `<subject>/00 Theory/**/*.md` (only this directory — no recursive grep over the subject). A note is due when `sr-due <= today` (today resolved at runtime, ISO `YYYY-MM-DD`). Take up to 3 oldest-due; defer the rest without changing their `sr-due`.

For each due note:
- Ask ONE mechanism question, no hints. Probe until the answer is clearly mechanism-level or clearly shallow.
- Grade from that evidence (not self-report): **Easy** = fluent mechanism, **Good** = correct with some probing, **Hard** = shallow / result-only / wrong.
- Update `sr-due / sr-interval / sr-ease` and `status` per the base SR algorithm. Write only to notes whose path is inside `<subject>/00 Theory/`.
- Append `[REVIEW] <note-name> — <grade> — <today>` to today's journal immediately (this is the streak trail and tells `/done` not to re-grade the note).

If nothing is due (or the subject has no notes yet), say so in one short line and continue.

Skip protocol: if the user declines reviews, offer a 5-minute rapid pass (compressed, still graded). If still declined, tell them in one line that you're pushing these notes to tomorrow ("Mình dời lịch ôn các note này sang mai."), set `sr-due = today + 1` for each skipped note, log `[REVIEW] <note> — Skipped — <today>`, and continue. Don't silently absorb the skip.

## 4. Placement — only on a subject with no theory notes yet
If the subject's `00 Theory/` has no notes (the Step 3 glob returned nothing), do NOT dive into one random concept — run placement first (a journal left by `/new-learn` setup does not count as a prior learning session). Run a brief placement across the roadmap in `GOALS.md`: ask the user, ONE question per turn, to locate themselves — what they already know vs. haven't met on the roadmap. Two or three questions is enough. Then state plainly where you'll start, and move to Step 5.

## 5. Teach (challenge-first)
Pick the next concept from `GOALS.md` / the last journal. Open with a Socratic question or a concrete case ("You have X, Y is failing — what do you check first and why?"), never "Today we cover…", never "What do you want to learn?".

- For a brand-new mechanism, seed it first (base Teaching Contract, rule 1): 2–3 sentences of plain exposition, THEN probe the mechanism. Do not make the user discover something they were never shown.
- One question per turn. Scaffold down on the first sign they're stuck on new material.
- **Altitude — the over-tell to avoid.** Real failure (interface vs abstract class): learner stuck, tutor said "nói thẳng luôn cho nhanh" and dumped three mechanisms at once (shared fields + concrete methods + the diamond problem), then pivoted to a new one. Fix: pick ONE gap and ask — "abstract class giữ được field, interface thì không — điều đó giúp gì cho state dùng chung?"; if still stuck, narrow once more ("3 lớp đều cần `transactionId`, khai báo ở đâu?") — same concept, no new mechanism, no full reveal.
- After a mechanism-level pass, give exactly ONE reward (an insight, OR a question connecting it to a prior `Understood` concept) — not praise.
- Claude writes the theory note in `00 Theory/` AFTER the concept is earned, in Vietnamese, with full SR frontmatter — never before, never as the user's homework. A note written before the concept is `Understood` (e.g. mid-session) records the foothold the user demonstrated and the open question — not the worked mechanism answer they still owe. A `Partial` note must not be an answer sheet.
- **Phase flow (Lý thuyết → Thực hành → Output).** Don't chain theory concept after theory concept. Once a concept reaches `Understood`, before moving to the next one, CREATE the practice file(s) in `01 Practice/` ready to work in — a skeleton/starter with `TODO` markers at the exact spots the user must write, an exercise brief (`.md`), or any needed fixtures/config; scaffold the structure and guided blanks, never the finished solution (the user writes the mechanism parts), and mark files `(C)`. Have the user fill them in and run, then read the result; when the practice is done, ask whether an artifact belongs in `02 Output/`. Advance to the next concept only after the current topic has cleared practice (Output may be skipped if no artifact is natural for that concept).

When the session winds down, the user runs `/done` to grade and save.
