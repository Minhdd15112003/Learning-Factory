# SETUP — Agent-Executable Installation Guide

> **Audience: an AI coding agent** (Claude Code, etc.) running on a fresh `git clone` of this repository. Read top to bottom and execute each step. Where a step needs a human (GUI clicks, installs, authentication), STOP and tell the human the exact action, then continue.
>
> Your working directory at start should be the repository root (the folder containing this `SETUP.md`, `CLAUDE.md`, and the subject folders). That folder is the **vault root**.

---

## 0. What this project is

A personal learning vault. The git root is ONE Obsidian vault and ONE Claude project. The teaching logic lives in plain files that ARE in the repo:

- `CLAUDE.md` (the framework constitution) + one thin `CLAUDE.md` per subject. Launched inside a subject folder, the two auto-merge (parent-walk to the git root).
- `.claude/skills/` — the three operating commands: `learn`, `done`, `new-learn`.
- Theory notes, journals, reviews inside each subject folder.

The structure is **flat**: each top-level folder is one subject (`GCP/`, `Terraform-GCP/`, `DevOps/`). No nesting, no cross-subject machinery. The learning loop is driven by **Claude reading/writing note frontmatter** (`sr-due`, `status`), so the core works from the command line with no Obsidian plugins. Obsidian + plugins only add the GUI.

Read `README.md` and `CLAUDE.md` for the full model.

---

## 1. What the clone does NOT contain (must be recreated)

`.gitignore` excludes:
- `.obsidian/` — after clone there is **no Obsidian config and no plugins** (Spaced Repetition, Claudian). Recreate only if the human wants the GUI (Step 4).
- `*.tfstate`, `*.tfstate.backup`, `**/.terraform/` — Terraform local state and provider caches; correct to omit, they regenerate.

Everything else is present: all `CLAUDE.md` files, `.claude/`, notes, journals, `GOALS.md`, `README.md`.

---

## 2. Prerequisites (check; if missing, tell the human to install)

| Tool | Check command | Needed for |
|---|---|---|
| Claude Code CLI | `claude --version` | Running the tutor (required) |
| Node.js | `node --version` | Claude Code / Claudian runtime (required) |
| git | `git --version` | Cloning, version control |
| Obsidian | (GUI app) | Optional — only for the GUI (Step 4) |

The human must also have **Claude Code authenticated**. If `claude` is not logged in, tell them to run `claude` once and complete login.

---

## 3. Localize machine-specific paths

The repo was authored on Windows under `C:\Users\minhdd_resolve\Desktop\WorkSpace\Learning-Factory`. Only `README.md` (the Start Guide example) hardcodes that path.

```bash
# from the vault root — list any remaining hardcoded author paths
grep -rln "minhdd_resolve" --include="*.md" . | grep -vE 'node_modules|/\.obsidian/|/\.git/'
```

For each hit (expected: only `README.md`), replace the example path with the absolute path of THIS clone. The skill files use relative/dynamic paths and need no change.

---

## 4. (OPTIONAL) Obsidian GUI + plugins

Skip this section if the human only wants the CLI tutor.

**4a. Open the vault.** In Obsidian, "Open folder as vault" → select the **repository root** (the folder with this `SETUP.md`), NOT a subject subfolder. Obsidian creates a fresh `.obsidian/`.

**4b. Spaced Repetition plugin** (the review dashboard; the review loop already works via Claude + `sr-due` frontmatter, so this is optional-but-recommended).
- Easiest (human, GUI): Settings → Community plugins → turn off Restricted mode → Browse → search **"Spaced Repetition"** (by Stephen Mwangi) → Install → Enable.
- Headless (agent):
  ```bash
  PLUG=".obsidian/plugins/obsidian-spaced-repetition"
  mkdir -p "$PLUG"
  REL="https://github.com/st3v3nmw/obsidian-spaced-repetition/releases/latest/download"
  for f in main.js manifest.json styles.css; do curl -sL "$REL/$f" -o "$PLUG/$f"; done
  ```
  Then add `"obsidian-spaced-repetition"` to `.obsidian/community-plugins.json` (a JSON array) and tell the human to reload Obsidian.
- Config: the plugin's default note-review tag `#review` matches the `review` tag already in every theory note's frontmatter — note review works out of the box once enabled.

**4c. Claudian plugin** (OPTIONAL — only to run Claude inside Obsidian).
- Install via Obsidian community browser: search **"Claudian"** (author Yishen Tu; plugin id `realclaudian`) → Install → Enable. It embeds Claude Code, so the Step 2 prerequisite still applies.
- Important: the plugin runs Claude with the **vault root** as cwd (it cannot target a subfolder). So name the subject when calling: `/learn GCP`. The base `CLAUDE.md` resolves from the vault root automatically; Claude reads the subject's `CLAUDE.md` itself.

---

## 5. Make the content yours (decide WITH the human — do not assume)

This vault ships with the original author's learning state: each subject's `GOALS.md` and `CLAUDE.md` Current Status, session logs under `*/03 Journals/`, `.claudian/sessions/`, and `sr-due`/`status` in the theory notes (the real ones live in `Terraform-GCP/00 Ly thuyet/`). Ask the human:

- **Continue the author's content** → do nothing; it just works.
- **Study your own topic** → run `/new-learn` to scaffold a new subject (a new top-level folder) and study there. Existing subjects can stay or be deleted.
- **Keep the framework, wipe progress** → only if explicitly asked: empty each `*/03 Journals/` (keep `Session-Log-Template.md`), reset every `Reasoning-Gaps.md` to empty Active/Resolved sections, set every theory note's `status` to `Exposed` / `sr-due` to today / `sr-interval: 1`, and blank each `GOALS.md` and Current Status. You may also delete `.claudian/sessions/*`.

Never wipe personal content without explicit confirmation.

---

## 6. Run it

- **CLI (no Obsidian needed):**
  ```bash
  cd "<this-clone>/GCP"
  claude
  ```
  Then type `/learn` (no arg = the subject you are in, because cwd is `GCP/`).
- **Obsidian + Claudian plugin:** open the Claudian panel, then `/learn GCP` (name the subject, because the plugin sits at the vault root).

The first action of any learning session is `/learn`; close it with `/done`.

---

## 7. Verify the install

1. Launch Claude in `GCP/` (CLI) or via Claudian with `/learn GCP`. It should load context and open with a **Socratic question / challenge** (for a subject with no notes yet, a placement question), NOT a file summary or a recap (Learning Mode).
2. Replies come in Vietnamese, addressing the user as "bạn". No emoji.
3. The two Terraform theory notes (`Terraform-GCP/00 Ly thuyet/01-Ban-chat-cua-Terraform.md`, `02-Terraform-State.md`) have valid frontmatter (`status`, `sr-due`, `sr-interval`, `sr-ease`, a `review` tag).
4. If the Spaced Repetition plugin is installed: open its review pane — notes tagged `review` with `sr-due <= today` appear in the queue.
5. `/done` at the end of a session updates `sr-due`/`status` in the touched notes and appends to the day's session log.

If all five hold, the install is complete.
