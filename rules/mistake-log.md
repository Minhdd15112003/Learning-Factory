# Global Mistake Log (Systemic Teaching & Reasoning Gaps)

**Scope:** Vault-wide tracking of teaching failures, sequencing errors, and recurring student misconceptions across all subjects.

---

## Log Entries

### [2026-07-28] Strategic DDD — Sequencing Error (Java-Backend)
- **Failure:** AI launched into Bounded Context communication exercise before defining "Bounded Context".
- **Symptom:** Learner inferred business flows correctly (sync/async) but had fundamental misconception about the term itself (thought Bounded Context was inside an Entity).
- **Root Cause:** Sequencing error — running exercises before completing core theory definitions.
- **Corrective Action:**
  1. Created `rules/no-undefined-terms.md` and `rules/concept-first-teaching.md`.
  2. Updated Root `CLAUDE.md` and `/learn` skill to enforce pre-question vocabulary check and auto-load all `rules/*.md` files.
  3. Rewrote `Strategic DDD.md` theory note putting Bounded Context first.

---

## Log Schema for Future Entries

```markdown
### [YYYY-MM-DD] <Topic> — <Error Type> (<Subject>)
- **Failure:** Description of what went wrong during teaching/questioning.
- **Symptom:** Learner confusion or incorrect reasoning observed.
- **Root Cause:** Analysis of why the teaching approach failed.
- **Corrective Action:** Changes made to notes, rules, or teaching contract.
```
