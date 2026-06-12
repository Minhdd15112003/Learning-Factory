# Skill: day-update

**Trigger:** user types `/day-update`, or says "kết thúc buổi học" / "tổng kết hôm nay".

**Purpose:** End-of-session close. Summarize incremental checkpoints, run a Feynman interview to grade understanding, update the SR schedule in every affected theory note, and append all of this to the session log before closing.

---

## Step 1 — Determine scope

Identify which scope the session belongs to — a sub-project, or the brain directly. The vault root is never a study scope.
- If `$ARGUMENTS` was provided to the command (shown in the prompt body as `Raw arguments passed to the command:`), parse it as `<brain> [sub-project]` and use it as the scope candidate — treat it as if the user had stated this scope in conversation.
- Otherwise check the conversation context for a project path (e.g., `03 Projects/learn-terraform-gcp/`). day-update normally runs right after `/learn-continue` in the same conversation, so the scope is usually already established there.
- If cwd is the vault root and NO brain can be identified from args or context, resolve the BRAIN first — ask (in Vietnamese) which brain today's session was under, naming the brains that exist on disk — then resolve sub-project vs brain-directly. Never write SR fields or logs to a brain you have not confirmed.
- If only the layer is ambiguous, ask: "Which scope was today's session under — a sub-project (e.g. learn-terraform-gcp) or the brain directly?"
- If it was a sub-project, note its path. When launched inside the brain (CLI), do NOT manually re-read its `CLAUDE.md` to "load rules" — the constitutions are already merged on launch; consult it only for Current Status / project-specific context.
- If cwd is the vault root (Claudian plugin launch), identify the brain, prefix all paths with `<brain>/`, and READ the brain's (and sub-project's) `CLAUDE.md` — from the vault root these are not auto-merged.

---

## Step 2 — Summarize incremental logs

1. Locate today's session log file in the relevant `01 Journals/` directory.
2. Read it; identify internally (do NOT output a list to the user) the checkpoints logged during the session.
3. Identify the one or two most important concepts that surfaced today — these drive Step 3.

Do NOT narrate what was read. Go straight to the Feynman question.

---

## Step 3 — Feynman interview

Ask 1-2 deep mechanism questions about the most important concept(s) from today. Questions must probe the HOW and WHY, never just the WHAT.

**Scope filter:** Only interview concepts that belong to today's resolved scope (notes inside `<scope>/01 Ly thuyet/` for a sub-project session, or inside BOTH `<scope>/01 Ly thuyet/` and `<scope>/00 Notes/` for a brain session). Skip any concept marked `[DOWNHILL REF — no SR update]` in the session log — those are read-only enrichments surfaced from a parent brain during a sub-project session and must not be graded or rescheduled here.

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
2. If a gap is significant (recurring, foundational, or likely to block future concepts), PROMOTE it: Claude writes a full theory note in Vietnamese in `<scope>/01 Ly thuyet/` — where `<scope>` is the scope resolved in Step 1 (the brain root, OR the sub-project root). A note's location follows the session scope; never write a brain-level note into a sub-project's `01 Ly thuyet/`, or vice versa. If `<scope>/01 Ly thuyet/` does not exist on disk, create it first. Use SR frontmatter (interval=1, ease=250, sr-due=today+1). Do NOT ask the user to write this note.

   **Downhill-reference gap exception:** If the gap involved a parent brain concept that was surfaced during teaching as a downhill enrichment reference (recognizable by a `[DOWNHILL REF — no SR update]` marker in the session log, or from the context of the session), do NOT create a new note for it in `<scope>/01 Ly thuyet/`. Instead, note it in `04 Reviews/Reasoning-Gaps.md` with a pointer to the parent note's location (e.g. `GCP/01 Ly thuyet/<ConceptName>.md`), and inform the user that they may want to revisit it in a brain-level session. If NEITHER the `[DOWNHILL REF — no SR update]` marker NOR unambiguous session context confirms the concept is a downhill reference, do NOT create a new theory note for it — append to `04 Reviews/Reasoning-Gaps.md`: "Gap [concept] — scope unclear; confirm in next session before creating a note," rather than risk a cross-scope write.
3. If a gap was resolved today (user passed the mechanism check), move it from `## Active Gaps` to `## Resolved Gaps` with today's date.

---

## Step 5 — Update SR schedule

For every concept newly taught or encountered during TODAY'S TEACHING (and Feynman-tested in Step 3):

> **No double-processing (disjoint ownership):** Notes that were Socratically reviewed and rescheduled during THIS session's `/learn-continue` Step 4 due-review already have their schedule set for this cycle — they are identifiable by their `[REVIEW] <note> — <grade>` entries in today's session log. Do NOT re-apply the SR formula or rewrite their frontmatter here; doing so compounds the interval twice in one day. `/learn-continue` owns the SR write for due-review notes; `/day-update` owns it for today's new concepts. The two sets must not overlap.

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

3. Open each affected theory note and update its frontmatter (`status`, `sr-due`, `sr-interval`, `sr-ease`). Locate each note by globbing within `<scope>/01 Ly thuyet/` (sub-project sessions), or within BOTH `<scope>/01 Ly thuyet/` and `<scope>/00 Notes/` (brain sessions) — the scope resolved in Step 1. Use only these explicit directories; never search the vault by concept name and update whatever `.md` turns up, as that can write SR fields into an out-of-scope note (e.g. a sub-project note during a brain-level session). If the concept has no note within the scope directory, create one there (Step 5) rather than updating a file in another scope. Claude does this directly; do not ask the user to do it.

   **Cross-scope write guard (applies unconditionally to ALL notes, not only known downhill-reference notes):** Any note whose file path does not begin with `<scope>/01 Ly thuyet/` (or `<scope>/00 Notes/` for a brain-level session) must not have its SR fields written by this session, regardless of what was discussed. If the session was a sub-project and a parent brain concept was surfaced during teaching as a downhill enrichment reference (a `[[wiki-link]]` + bridging sentence shown in-chat, or marked `[DOWNHILL REF — no SR update]` in the session log), that parent note is explicitly excluded from this step. Do not touch its `sr-due`, `sr-interval`, `sr-ease`, or `status`.

4. Apply the status transition implied by the grade (Knowledge Model in `CLAUDE.md`):
   - Mechanism-level pass → promote `Exposed`/`Partial` to `Understood`.
   - `Hard` (shallow / wrong / "I don't know") on a note already at `Understood` → downgrade to `Partial`.
   - Three consecutive Easy/Good reviews on the same note (count the streak from prior session logs, not internal memory) → promote `Understood` to `Mastered`. Any `Hard` resets the streak to zero.

5. For brand-new concepts that do not yet have a theory note, write one now in Vietnamese at `<scope>/01 Ly thuyet/<ConceptName>.md` — always the scope-root `01 Ly thuyet/` resolved in Step 1, never a different brain or sub-project directory. If that directory does not yet exist on disk, create it first. Include the full SR frontmatter block.

---

## Step 6 — Close the session

1. Append the user's Feynman answers (verbatim or close paraphrase with clear attribution) to the session log under a `## Feynman Check` section.
2. Update the **Current Status** line in the SCOPE `CLAUDE.md` — the scope resolved in Step 1, regardless of which notes were touched mid-session: the sub-project's `CLAUDE.md` if the session was a sub-project, otherwise the brain's `CLAUDE.md`. NEVER edit the vault-root base `CLAUDE.md` (it has no Current Status and is shared framework). If the resolved scope's `CLAUDE.md` has no Current Status section to write to (some brain constitutions only carry a Weekly Update / Projects block), record the status line in today's session log instead — do not invent a section or write it into a different scope's file.
3. Update each affected note's schedule silently — no enumerated dump to the user (Learning Mode Rule 8). At most name, in one forward-framed line, the next concept that will come due: e.g. "Lần tới [[Terraform State]] sẽ đến hạn ôn."
4. Close with a brief Vietnamese confirmation that the session is saved (e.g. "Đã lưu log và cập nhật lịch ôn."), then end on a forward-looking question or teaser for next session — never a passive recap.

---

## Note to Claude

- Wrap all GCP and Terraform terms in `[[wiki-links]]` when writing to log files and theory notes.
- No emoji anywhere in output or files.
- Claude authors all theory notes. The user provides spoken Feynman answers and hands-on practice only; Claude turns the learning into written reference artifacts.
- Never list files read or describe what was loaded. Move directly from context to action.
- The session is not closed until Steps 4, 5, and 6 are all written to disk.
- **Scope write integrity:** SR fields (`sr-due`, `sr-interval`, `sr-ease`, `status`) are updated ONLY in notes whose file paths begin with the session's resolved `<scope>/01 Ly thuyet/` (or `<scope>/00 Notes/` for a brain session). A parent brain concept referenced during a sub-project session as downhill enrichment is explicitly excluded from SR updates — do not touch its frontmatter. If in doubt about a note's scope, check its file path: if it does not start with `<scope>/`, do not write to it.
