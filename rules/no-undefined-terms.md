# Rule: No Undefined Terms

**Scope:** vault-wide — every subject (GCP, Terraform, DevOps, Java-Backend, and any subject created later), every roadmap, every session.
**Loaded via:** embedded in root `CLAUDE.md` under `## Rule: No Undefined Terms`.

---

## Root cause on record

The Strategic DDD session (Stage 2, commerce-fulfillment-system) listed 6 Bounded Contexts and immediately asked about business flows between them — but "what is a Bounded Context" had never been defined. The user answered reasoning questions well (sync/async, reservation, saga) by inferring from real-world experience, but when asked a question requiring precise understanding of the term, the gap surfaced: they thought Bounded Context lived *inside* an Entity.

This is a **sequencing failure, not a knowledge failure**: the exercise ran before core theory, violating the 4-part note standard that mandates core theory first.

---

## Core principle

Never use any technical term or concept in a question, exercise, or example if that term has not been explicitly defined for the learner beforehand — either in the same session or in a note already written in `00 Theory/`.

---

## Two knowledge types — different teaching modes

| Type | Characteristic | How to teach |
|---|---|---|
| **Reasoning / trade-offs** (WHY, when to use what) | Can be inferred from real-world experience or logic | Keep Socratic — ask questions, let them derive the answer |
| **Terminology / foundational definitions** (names, field-specific concepts) | Cannot be guessed — they are conventions from docs/community | Teach directly, concisely, with a concrete example — **before** using in any question |

**Example:**
- "Why should Payment run async?" → Type 1 (reasoning). Ask Socratically.
- "What is a Bounded Context?" → Type 2 (terminology). Define it first. Never ask it cold.

---

## Mandatory mechanisms

1. **Vocabulary primer when opening a new topic** — before the first Socratic question on a topic, list every new term that will appear in that topic and define each in 1–2 sentences with a concrete example. This IS the "core theory" section of the 4-part note standard — it must happen first in the session, not be written as a note retroactively after the exercise is already done.

2. **Self-check before every question** — before writing any question containing a technical term, ask internally: "has this term been unambiguously defined for this user?" If no → stop, define it this turn, ask the question next turn. One new term per turn, always.

3. **Fix in place, don't patch** — if an answer reveals the user doesn't know a **term** (not a wrong application of a known concept), stop the current exercise immediately. Re-teach that concept from a clean definition. Then resume the exercise.

4. **Anchor to known ground when possible** — if the new term has an equivalent or near-equivalent concept in Go, JavaScript, or Python (languages the user already knows), state that analogy at the moment of definition. This shortens the time-to-understanding significantly.

---

## Quick-reference checklist (run before every question)

- [ ] Does this question contain a technical term?
- [ ] Is that term in `00 Theory/` with status ≥ `Partial`, OR was it seeded earlier this session?
- [ ] If either check fails → define the term this turn. Ask the question next turn.
