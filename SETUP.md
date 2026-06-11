# SETUP — Agent-Executable Installation Guide

> **Audience: an AI coding agent** (Claude Code, Cursor, etc.) running on a fresh `git clone` of this repository. Read this top to bottom and execute each step. Where a step needs a human (GUI clicks, installs, authentication), STOP and tell the human the exact action, then continue once done.
>
> Your current working directory when you start should be the repository root (the folder that contains this `SETUP.md`, `CLAUDE.md`, and `GCP/`). That folder is the **vault root**.

---

## 0. What this project is

A personal, multi-"brain" learning vault. The git root is ONE Obsidian vault and ONE Claude project. The teaching logic lives entirely in plain files that ARE in the repo:
- `CLAUDE.md` (base framework) + `GCP/CLAUDE.md` (a "brain") + `GCP/03 Projects/*/CLAUDE.md` (sub-projects). These auto-merge via parent-walk.
- `05 Skills/` — the operating skills (`learn-continue`, `day-update`, `weekly-update`, `new-project`, `brain-setup`).
- Theory notes, journals, reviews under `GCP/`.

Read `README.md` and `CLAUDE.md` for the full model. The learning loop is driven by **Claude reading/writing note frontmatter** (`sr-due`, `status`), so the core works from the command line with no Obsidian plugins at all. Obsidian + plugins only add the GUI experience.

---

## 1. What the clone does NOT contain (must be recreated)

`.gitignore` excludes:
- `.obsidian/` — so after clone there is **no Obsidian config and no Obsidian plugins** (Spaced Repetition, Claudian, etc.). Recreate only if the human wants the Obsidian GUI (Step 4).
- `*.tfstate`, `*.tfstate.backup`, `**/.terraform/` — Terraform local state and provider caches. Correct to omit; they regenerate.

Everything else is present: all `CLAUDE.md` files, `05 Skills/`, `.claude/`, notes, journals, `GOALS.md`, `README.md`.

---

## 2. Prerequisites (check; if missing, tell the human to install)

| Tool | Check command | Needed for |
|---|---|---|
| Claude Code CLI | `claude --version` | Running the tutor (required) |
| Node.js | `node --version` | Claude Code / Claudian plugin runtime (required) |
| git | `git --version` | Cloning, version control |
| Obsidian | (GUI app) | Optional — only for the GUI experience (Step 4) |

The human must also have **Claude Code authenticated** (Anthropic login or API key). If `claude` is not logged in, tell them to run `claude` once and complete login.

---

## 3. Localize machine-specific paths

The repo was authored on Windows under `C:\Users\minhdd_resolve\Desktop\WorkSpace\gcp-document`. Only one file hardcodes that path: `README.md` (the Start Guide example). Update it to this clone's real path.

```bash
# from the vault root — list any remaining hardcoded author paths
grep -rln "minhdd_resolve" --include="*.md" . | grep -vE 'node_modules|/\.obsidian/|/\.git/'
```

For each hit (expected: only `README.md`), replace the example path with the absolute path of THIS clone. On macOS/Linux the `cd` example becomes e.g. `cd "/home/<user>/.../gcp-document/GCP"`. The skill files use relative/dynamic paths and need no change.

---

## 4. (OPTIONAL) Obsidian GUI + plugins

Skip this entire section if the human only wants the CLI tutor. Do it only if they want the Obsidian experience (reading notes, the spaced-repetition dashboard, or running Claude inside Obsidian via the Claudian plugin).

**4a. Open the vault.** Tell the human: in Obsidian, "Open folder as vault" → select the **repository root** (the folder with this `SETUP.md`), NOT the `GCP/` subfolder. Obsidian creates a fresh `.obsidian/`.

**4b. Spaced Repetition plugin** (the review *dashboard*; the review *loop* already works via Claude + `sr-due` frontmatter, so this is optional-but-recommended).
- Easiest (human, GUI): Settings → Community plugins → turn off Restricted mode → Browse → search **"Spaced Repetition"** (by Stephen Mwangi) → Install → Enable.
- Headless (you, the agent, can do it):
  ```bash
  PLUG=".obsidian/plugins/obsidian-spaced-repetition"
  mkdir -p "$PLUG"
  REL="https://github.com/st3v3nmw/obsidian-spaced-repetition/releases/latest/download"
  for f in main.js manifest.json styles.css; do curl -sL "$REL/$f" -o "$PLUG/$f"; done
  ```
  Then add `"obsidian-spaced-repetition"` to `.obsidian/community-plugins.json` (create the file as a JSON array if it does not exist), and tell the human to reload Obsidian.
- Config: the plugin's default note-review tag is `#review`, which matches the `review` tag already in every theory note's frontmatter — so note review works out of the box once enabled. Confirm "Notes" review is enabled in the plugin settings.

**4c. Claudian plugin** (OPTIONAL — only to run Claude *inside* Obsidian instead of the terminal).
- Install via Obsidian community browser: search **"Claudian"** (author Yishen Tu; plugin id `realclaudian`) → Install → Enable. It embeds Claude Code, so the Claude Code prerequisite from Step 2 still applies.
- Important behavior: the plugin runs Claude with the **vault root** as the working directory (it cannot target a subfolder). So when using it, invoke a brain by name: `/learn-continue GCP`. The base `CLAUDE.md` and `05 Skills/` resolve from the vault root automatically; Claude will read the brain's `CLAUDE.md` itself.

---

## 5. Make the content yours (decide WITH the human — do not assume)

This vault ships with the original author's personal learning state: `GCP/GOALS.md`, the GCP brain, the `learn-terraform-gcp` sub-project progress, session logs under `GCP/**/01 Journals/`, `.claudian/sessions/`, and `sr-due`/`status` values in the theory notes. Ask the human which they want:

- **Continue the author's GCP content** → do nothing; it just works.
- **Study your own topic** → run the `brain-setup` skill to scaffold a new brain (a new top-level folder) and study there. The GCP brain can stay or be deleted.
- **Keep the framework, wipe the author's progress** → only if explicitly asked, reset: empty the `GCP/**/01 Journals/` logs (keep the `Session-Log-Template.md`), reset both `Reasoning-Gaps.md` to empty Active/Resolved sections, set every theory note's `status` back to `Exposed` and `sr-due` to today / `sr-interval: 1`, blank `GCP/GOALS.md` and the brain's Weekly Update / Current Status. You may also delete `.claudian/sessions/*` (the author's chat history).

Never wipe personal content without explicit confirmation.

---

## 6. Run it

- **CLI (no Obsidian needed):**
  ```bash
  cd "<this-clone>/GCP"
  claude
  ```
  Then type `/learn-continue` (no arg = the current brain, because cwd is `GCP/`).
- **Obsidian + Claudian plugin:** open the Claudian panel, then `/learn-continue GCP` (name the brain, because the plugin sits at the vault root).

The first action of any learning session is `/learn-continue`.

---

## 7. Verify the install

1. Launch Claude in `GCP/` (CLI) or via Claudian with `/learn-continue GCP`. It should load context and open with a **Socratic question / challenge**, NOT a file summary or a recap (Learning Mode).
2. Replies come in the brain's language (Vietnamese for the shipped GCP brain). No emoji.
3. The three theory notes (`GCP/00 Notes/IAM-Role-vs-IAM-Policy.md`, `GCP/03 Projects/learn-terraform-gcp/01 Ly thuyet/01-Ban-chat-cua-Terraform.md`, `02-Terraform-State.md`) have valid frontmatter (`status`, `sr-due`, `sr-interval`, `sr-ease`, a `review` tag).
4. If the Spaced Repetition plugin is installed: open its review/notes pane — notes tagged `review` whose `sr-due <= today` appear in the queue.
5. `/day-update` at the end of a session updates `sr-due`/`status` in the touched notes and appends to the day's session log.

If all five hold, the install is complete.
