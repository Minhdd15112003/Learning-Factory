# Rule: Mistake Log & Misconception Protocol

**Scope:** Vault-wide — applies to all subjects, roadmaps, and sessions.

---

## Core Purpose

Systematically capture, analyze, and resolve student misconceptions and AI teaching errors. Prevent repeating the same teaching mistakes or leaving student knowledge gaps unaddressed.

---

## Protocol Execution

### 1. Detection
When a learner response indicates:
- **Terminology gap:** Learner doesn't know what a term means.
- **Conceptual misconception:** Learner misinterprets how a mechanism works (e.g. thinking Bounded Context lives inside an Entity).
- **Application failure:** Learner knows the concept but applies it incorrectly.

### 2. Immediate Correction (Fix-In-Place)
- **Do NOT patch over the error** or proceed with the exercise.
- **Pause the exercise immediately.**
- Re-teach the missing concept from a clean definition and concrete example.
- Verify understanding before resuming the exercise.

### 3. Logging Standard
- Log student misconceptions in `<subject>/04 Reviews/Reasoning-Gaps.md`.
- Log systemic teaching failures (e.g. AI asking about undefined terms) in `rules/mistake-log.md`.
- Record:
  1. Date & Subject
  2. Misconception / Gap description
  3. Root Cause (Sequencing Error vs Logic Error)
  4. Corrective Action Taken
