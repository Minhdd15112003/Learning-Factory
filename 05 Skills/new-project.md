# Skill: New Project

Create a new sub-project inside `03 Projects/` by interviewing the user, then scaffolding the folder structure, project CLAUDE.md, and COMMANDS.md from scratch. No template copy — every project is built fresh and consistent with the learning vault structure.

## How It Works

1. Interview the user one question at a time (up to 6 questions)
2. Based on answers, scaffold the folder structure and files using mkdir -p
3. Seed the required bootstrap files
4. Update the root CLAUDE.md to register the new project
5. If the user has no answer yet for a question, use a sensible default and leave a `<!-- TODO: fill this in -->` comment

## Interview Questions (Ask One at a Time)

Ask these conversationally. Each answer can shape the next. If someone says "I'm not sure yet" or gives a partial answer, do not push — just work with what you have.

### 1. What is the project called?

Used for the folder name under `03 Projects/`.

### 2. What is this project?

One paragraph. What are we building, doing, or learning? What is the subject matter?

### 3. What does "done" look like?

What is the concrete end goal? What does success look like? This becomes the prime directive in CLAUDE.md — the thing Claude nudges toward when a session drifts.

### 4. Who else is involved?

Key people and their roles. "Just me" is a valid answer.

### 5. What is the learning/work process from start to finish?

Walk through how work flows from start to finish in this project. This shapes the numbered project subfolders.

**If they are not sure:** Use the default learning structure (mirrors learn-terraform-gcp):
- `01 Journals/` — session logs
- `01 Ly thuyet/` — theory notes
- `02 Thuc hanh/` — hands-on practice
- `03 Output/` — results and deliverables
- `04 Reviews/` — misconception log

Tell them they can restructure later.

### 6. Any rules or conventions Claude should follow?

Things Claude should always or never do. Specific formats, naming conventions, secret handling. Optional — skip if they have nothing yet.

---

## After the Interview

### Step 1: Create the project folder tree

Use `mkdir -p` — no template copy.

```bash
mkdir -p "03 Projects/[Project Name]/01 Journals"
mkdir -p "03 Projects/[Project Name]/01 Ly thuyet"
mkdir -p "03 Projects/[Project Name]/02 Thuc hanh"
mkdir -p "03 Projects/[Project Name]/03 Output"
mkdir -p "03 Projects/[Project Name]/04 Reviews"
```

Add or rename folders based on the process answer from question 5. The structure above is the default.

### Step 2: Seed bootstrap files

**`01 Journals/Session-Log-Template.md`** — copy from root `01 Journals/Session-Log-Template.md`.

**`04 Reviews/Reasoning-Gaps.md`** — copy from root `04 Reviews/Reasoning-Gaps.md`.

### Step 3: Write the project CLAUDE.md

The project CLAUDE.md is **English** (AI-facing). Use this structure. Fill in what you learned from the interview. Use `<!-- TODO: fill this in -->` for anything the user was not sure about.

```markdown
> **Inherits from:** root `../../CLAUDE.md` — the vault constitution (Learning Mode Contract, Teaching Mechanics, Error Handling, Wiki-links, SR algorithm).
>
> **Claude Instruction:** You MUST read root `../../CLAUDE.md` first to load your core persona and teaching mechanics before answering any prompts in this directory.
>
> This file contains ONLY the rules and context specific to this project. All shared rules are defined in the root file.

# [Project Name] — Project Constitution

[One paragraph: what this project is about, from question 2]

## Claude's Role (Project-specific)

[What Claude does in this project. Be specific — not "help me learn" but the actual job.]

If a session is drifting without moving toward [the goal from question 3], nudge the user back: "[contextual nudge message in Vietnamese]"

## Process

1. **[Step name]:** [Description — maps to a folder]
2. **[Step name]:** [Description — maps to a folder]
3. **[Step name]:** [Description — maps to a folder]

[If unsure, default to:]
1. **Ly thuyet:** Learn concepts and theory in `01 Ly thuyet/`.
2. **Thuc hanh:** Apply concepts hands-on in `02 Thuc hanh/`.
3. **Output:** Record results and deliverables in `03 Output/`.

## Key People

[From question 4. "Name — role". Skip section if solo project.]

## Rules & Conventions (Project-specific)

- **(C) prefix** — Files created by Claude are prefixed with `(C)` so it is clear they are AI-generated.
- **Editing rule** — Before editing any file without the `(C)` prefix, ask for permission first.
[Add any project-specific rules from question 6]

## Folder Structure

```
[Project Name]/
├── 01 Journals/       <- Session Log & Feynman check
├── 01 Ly thuyet/      <- Theory notes (Vietnamese)
├── 02 Thuc hanh/      <- Hands-on practice
├── 03 Output/         <- Results and deliverables
├── 04 Reviews/        <- Reasoning-Gaps.md
└── COMMANDS.md        <- Quick reference
```

## Current Status

> **Last updated:** [today's date]
> **Status:** Just created — getting started.

<!-- TODO: Update this as the project progresses -->
```

### Step 4: Write COMMANDS.md

```markdown
# Commands & Skills — [Project Name]

Quick reference for all commands and skills available in this project.

## Skills

_No project-specific skills yet._

## Commands

_No project-specific commands yet._
```

### Step 5: Update the root CLAUDE.md

Two sections must stay in sync after every new project:

**1. Folder Structure** — add the new project under the `03 Projects/` tree:

```markdown
│   └── [Project Name]/ ← [one-line description]
```

**2. Current Projects & Overviews** — add a new subsection:

```markdown
### [Project Name] — `03 Projects/[Project Name]/`
**Status:** Just created
[One-line description from question 2]
See the project's own `CLAUDE.md` for project-specific rules.
```

Only edit these two sections. Do not restructure anything else in root CLAUDE.md.

### Step 6: Confirm to the user (in Vietnamese)

Tell them:
- The folder structure that was created
- A summary of the CLAUDE.md
- That they can update CLAUDE.md and the folder structure anytime as the project evolves
- Call out any `<!-- TODO -->` sections so they know what to fill in later
