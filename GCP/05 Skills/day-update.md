# Skill: day-update

**Trigger:** user types `/day-update`, or says "ket thuc buoi hoc" / "tong ket hom nay".

**Purpose:** End-of-session close. Summarize incremental checkpoints, run a Feynman interview to grade understanding, update the SR schedule in every affected theory note, and append all of this to the session log before closing.

---

## Step 1 — Determine scope

Identify which project (or vault root) the session belongs to:
- Check the conversation context for a project path (e.g., `03 Projects/learn-terraform-gcp/`).
- If ambiguous, ask: "Which project was today's session under, or was it root GCP?"
- Read that project's `CLAUDE.md` (if a sub-project) to load project-specific rules before proceeding.

---

## Step 2 — Summarize incremental logs

1. Locate today's session log file in the relevant `01 Journals/` directory.
2. Read it; list the checkpoints that were logged during the session.
3. Identify the one or two most important concepts that surfaced today — these drive Step 3.

Do NOT narrate what was read. Go straight to the Feynman question.

---

## Step 3 — Feynman interview

Ask 1-2 deep mechanism questions about the most important concept(s) from today. Questions must probe the HOW and WHY, never just the WHAT.

Protocol by answer type — apply exactly:

**Correct + mechanism-level answer (Bloom L2):**
- Grade: Easy or Good (see Step 5 for distinction).
- Promote status to `Understood` if it was `Partial` or `Exposed`.
- Give exactly ONE pattern-connecting insight that links this concept to a broader [[GCP]] or [[Terraform]] principle. No praise.
- Move on.

**Correct + result-only answer (Bloom L1 — states what, not how):**
- Do NOT advance the status.
- Keep at `Partial`.
- Respond: "That is the what. What is the how?" — one follow-up, nothing more.
- Do not explain the mechanism; the user must retrieve it.

**Incorrect answer:**
- Do NOT correct immediately.
- Exploit the error: follow the wrong logic to its conclusion.
  Example: "If that were true, what would `terraform plan` show?" Let the contradiction surface before guiding toward the correct mechanism.
- Status stays at `Partial` or `Exposed` until a mechanism-level answer is given.

**"I don't know" / no answer:**
- Acceptable. Do not push.
- Record the concept as `Exposed` or keep it at `Partial`.
- Schedule a short review interval (see Step 5).

Record the user's ACTUAL words in the log (Step 6). Never fabricate or paraphrase understanding the user did not demonstrate.

---

## Step 4 — Update Reasoning-Gaps

1. If a new misconception surfaced today, append it to `04 Reviews/Reasoning-Gaps.md` in Vietnamese (gap description) under the English section header `## Active Gaps`.
2. If a gap is significant (recurring, foundational, or likely to block future concepts), PROMOTE it: Claude writes a full theory note in Vietnamese in the relevant `01 Ly thuyet/` directory, with SR frontmatter (interval=1, ease=250, sr-due=today+1). Do NOT ask the user to write this note.
3. If a gap was resolved today (user passed the mechanism check), move it from `## Active Gaps` to `## Resolved Gaps` with today's date.

---

## Step 5 — Update SR schedule

For every concept reviewed or newly encountered today:

1. Determine the Feynman grade:
   - **Easy:** correct + mechanism-level, answered quickly without hesitation.
   - **Good:** correct + mechanism-level, but required some prompting or slow retrieval.
   - **Hard:** result-only, incorrect, or "I don't know".

2. Apply the SR algorithm to the note's current `sr-interval` and `sr-ease`:
   - Easy: `ease += 20`; `interval = round(interval * ease / 100 * 1.3)`
   - Good: `ease` unchanged; `interval = round(interval * ease / 100)`
   - Hard: `ease = max(130, ease - 20)`; `interval = max(1, round(interval * 0.5))`
   - Brand-new note (first scheduling): `interval = 1`, `ease = 250`.
   - Then: `sr-due = today + interval` days.

3. Open each affected theory note and update its frontmatter (`status`, `sr-due`, `sr-interval`, `sr-ease`). Claude does this directly; do not ask the user to do it.

4. For brand-new concepts that do not yet have a theory note, write one now (Vietnamese, in `01 Ly thuyet/` or the project equivalent), with the full SR frontmatter block.

---

## Step 6 — Close the session

1. Append the user's Feynman answers (verbatim or close paraphrase with clear attribution) to the session log under a `## Feynman Check` section.
2. Update the **Current Status** line in the relevant `CLAUDE.md` to reflect today's endpoint (last concept covered, last practice step completed).
3. Report the updated review schedule in plain text — no emoji:
   - Format: "Review schedule updated: [[Concept]] -> next YYYY-MM-DD (interval X days)"
   - List every concept touched today.
4. Confirm the session is saved: "Session log updated and SR schedule applied."

---

## Note to Claude

- Wrap all GCP and Terraform terms in `[[wiki-links]]` when writing to log files and theory notes.
- No emoji anywhere in output or files.
- Claude authors all theory notes. The user provides spoken Feynman answers and hands-on practice only; Claude turns the learning into written reference artifacts.
- Never list files read or describe what was loaded. Move directly from context to action.
- The session is not closed until Steps 4, 5, and 6 are all written to disk.
