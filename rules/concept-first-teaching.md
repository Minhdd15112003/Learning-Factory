# Rule: Concept-First Teaching Protocol

**Scope:** Vault-wide — applies to all subjects (GCP, Terraform, DevOps, Java-Backend, and any future subjects), all roadmaps, all learning sessions.

---

## Core Principle

Core theory and foundational definitions MUST precede exercises and practice. Never assign exercises, hands-on tasks, or Socratic questions requiring a term or concept that has not been explicitly defined and taught first.

---

## 4-Step Sequence for Every Topic

1. **Core Theory & Vocabulary Primer:**
   - Define all new technical terms, mechanisms, and architectural concepts clearly (1–2 sentences + concrete example or real-world analogy).
   - Core theory note in `00 Theory/<Topic_Folder>/` must be seeded or created first.
2. **Concept Verification (Socratic Check):**
   - Probe Bloom L2 understanding (how/why it works).
   - Check if the learner can explain the mechanism in their own words.
3. **Guided Practice (`01 Practice/`):**
   - Provide starter skeletons, guided blanks, or exercise briefs ONLY after concept verification passes.
   - Learner implements the mechanism parts.
4. **Deliverable / Output (`02 Output/`):**
   - Produce real artifacts or complete implementation.

---

## Strict Rules

- **No Premature Exercises:** Never give exercise files or hands-on tasks before the underlying concepts are taught and verified.
- **Anchor to Known Experience:** When introducing new concepts, anchor to known languages/frameworks (Python, JS/TS, Go) whenever applicable.
- **Single Concept Focus:** Introduce one new mechanism per turn.
