---
description: Resume or continue a learning session for a subject — alias for /learn, explicitly auto-loading all rules in rules/*.md and continuing the roadmap.
argument-hint: "[subject]"
---

The user invoked `/learn-continue`. This command is an exact continuation alias for `/learn`.

Execute the exact same workflow as `/learn`:
1. Resolve the subject (argument or current working directory).
2. Load context silently: **MUST READ ALL FILES IN `rules/*.md`** (`rules/no-undefined-terms.md`, `rules/concept-first-teaching.md`, `rules/mistake-log-protocol.md`, `rules/mistake-log.md`), `<subject>/CLAUDE.md`, latest journal in `<subject>/03 Journals/`, and `<subject>/04 Reviews/Reasoning-Gaps.md`.
3. Conduct due spaced-repetition reviews (skip notes with `review-count >= 2`).
4. Run placement if no theory notes exist yet.
5. Teach the next concept (Concept-First: define core theory and vocabulary FIRST, before asking any Socratic question or giving exercise skeletons).
