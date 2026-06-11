# Skill: Weekly Update

Interview the user to refresh the context files of the CURRENT brain: its `CLAUDE.md` (Weekly Update, Goals summary, Projects & Overviews), its `GOALS.md`, and each sub-project's `CLAUDE.md`. Keeps the brain's context current so Claude always has accurate state.

**Scope (Option A):** Run this from INSIDE a brain (cwd = the brain, e.g. `GCP/`). The file you update is the BRAIN's `CLAUDE.md` (`./CLAUDE.md`) — NOT the vault-root base `CLAUDE.md`. The base is shared framework and must NEVER receive user-progress data. If the user has multiple brains, run weekly-update from each brain separately (or enumerate them with `ls -d <vault-root>/*/` and repeat per brain). If launched at the vault root (Claudian plugin — cwd has no `01 Journals/`), first ask which brain and treat every `./` path below as `<brain>/`.

## When to Use

- User says "weekly update", "let's do a weekly review", "update my vault", or similar.
- It has been a week or more since the last update (check the "Last updated" date in the brain CLAUDE.md Weekly Update section).

## How It Works

1. Scan the current brain's context
2. Interview at the meta level (weekly pulse, goals)
3. Walk through each sub-project for status updates
4. Update the brain's files
5. Optionally create a weekly review note

---

## Phase 1: Scan Current State (cwd = the brain)

Read everything before asking a single question.

```bash
ls -d "03 Projects"/*/ 2>/dev/null || echo "No sub-projects yet"
```

Read:
- `./CLAUDE.md` (the brain) — focus on the **Weekly Update**, **Goals (summary)**, and **Projects & Overviews** sections.
- `./GOALS.md` — scan for numbers, dates, or milestones that may need updating.
- Each sub-project's `CLAUDE.md` — the **Current Status** section.

Do NOT read or edit the vault-root base `../CLAUDE.md` — it is shared framework, not per-brain state.

**Goal:** build a mental map of what was true last time so you can ask targeted questions about what changed, not make the user repeat everything.

---

## Phase 2: Meta-Level Interview

Covers the brain's CLAUDE.md and GOALS.md. Reference what you read in Phase 1 so the user knows you are caught up.

### Weekly Pulse
Show the current Weekly Update text (or note it is blank), then ask:
- What's working right now?
- What's not working?
- What are you sitting on or need to decide?
- What are you feeling pulled toward?
- Any deadlines or time-sensitive things coming up?

If a field has not changed, the user can say "same" and you keep it.

### Goals Check-In
Reference the brain's **Goals (summary)** and `GOALS.md`. Ask:
- Any progress on the main goal since last time?
- Has the plan changed?
- Anything new on the risk/runway front?

Keep it tight. If nothing changed, move on.

---

## Phase 3: Sub-Project Updates

Walk through each sub-project folder found in Phase 1. For each:
1. Show the current status from its `CLAUDE.md` (Current Status section).
2. Ask: "What's the update on [Project]? Any status change this week?"
3. If nothing changed, move on.

One question per project. This is a check-in, not a deep dive.

### SR Queue Check
Scan theory notes for notes where `sr-due <= <today>` (resolve `<today>` at runtime):
- brain-level notes: `./00 Notes/**/*.md`
- each sub-project: `./03 Projects/<project>/01 Ly thuyet/**/*.md`

Count the overdue notes and name them, then surface to the user (in Vietnamese): "Có X note quá hạn ôn tập: [list]. Các note này sẽ được kiểm tra trong phiên /learn-continue tiếp theo."

Do NOT run the actual Socratic review here — that happens through `/learn-continue`, not this skill and not the Obsidian SR plugin UI (the plugin holds scheduling metadata only; Claude drives the review).

---

## Phase 4: Update the brain's files

The file to update is the BRAIN's `CLAUDE.md` (`./CLAUDE.md`), NOT the vault-root base. Show the user a summary of the changes before writing.

### Brain CLAUDE.md
**Weekly Update section:**
```markdown
## Weekly Update

> **Last updated:** [today's date]

- What's working: [from interview]
- What's not working: [from interview]
- What I'm sitting on / need to decide: [from interview]
- What I'm feeling pulled toward: [from interview]
- Any deadlines or time-sensitive things: [from interview]
```
**Goals (summary)** — update only if something actually changed.
**Projects & Overviews** — update the Status line / overview for any sub-project whose status changed. Leave unchanged ones alone.

### GOALS.md (this brain's)
Update any specific numbers/dates/milestones the user called out. Do not restructure it.
**Language rule:** each brain's `GOALS.md` is the user's personal goal document and must stay in Vietnamese at all times. There is no single vault-root GOALS.md — each brain owns its own.

### Each sub-project CLAUDE.md
Update the **Current Status** section:
```markdown
## Current Status

> **Last updated:** [today's date]
> **Status:** [updated status]

[Any additional context from this week]
```

**Critical rules:** Only edit status/progress sections. Never rewrite a brain's or sub-project's structure, process, or rules. NEVER edit the vault-root base `CLAUDE.md`.

---

## Phase 5: Weekly Review Note (Optional)

Ask: "Want me to create a weekly review note in `04 Reviews/`?" If yes, create a dated note (Vietnamese) using the interview answers. Match any existing format. If no, skip.

---

## Summary of What Gets Updated

| File | What changes |
|------|-------------|
| Brain `CLAUDE.md` (cwd) → Weekly Update | All five pulse fields + date |
| Brain `CLAUDE.md` (cwd) → Goals (summary) | Only if it changed |
| Brain `CLAUDE.md` (cwd) → Projects & Overviews | Status line for changed sub-projects |
| Brain `GOALS.md` | Any trackable items that changed (Vietnamese) |
| Each sub-project `CLAUDE.md` → Current Status | Status + date + what happened |
| Weekly review note (`04 Reviews/`) | Optional — user's choice, Vietnamese |
| Vault-root base `CLAUDE.md` | NEVER — it is shared framework |
