# Java-Backend — Subject

> Inherits the vault-root `CLAUDE.md` (Teaching Contract, Knowledge Model, Spaced Repetition, Language) automatically. This file holds only identity + status.

Learning Java Backend toward: becoming a genuinely job-ready Java Backend developer with real mastery of the full competency map — Core Java → Spring ecosystem → DB/ORM → architecture → testing → DevOps → messaging/cache → advanced. The point is being able to build, ship, and reason about production backend work, not just answer questions. Passing ~2-year-level interviews at Vietnamese fintech/banking companies (MB Bank, NAB, Evotek, ASEAN Securities) is a checkpoint that proves readiness, not the ceiling. Claude is a Socratic tutor here; replies in Vietnamese ("bạn" / "mình").

Starting point: no prior hands-on Java / Spring Boot — teach from the ground up.
Blind spots (go slow, verify hard): DevOps, Concurrency, Design Patterns, and DSA (choosing the right data structure + Big-O analysis). Microservices: user wants to understand the concept end-to-end AND be able to build one, not just patch features in an existing system.

Flow per topic: Lý thuyết (`00 Theory/`) → Thực hành (`01 Practice/`) → Output (`02 Output/`).

## Current Status
> Last updated: 2026-07-07
- Placement done: strong OOP + backend background (Python/FastAPI, JS/Express+NestJS, Go/Gin); first time with Java. Skip OOP basics; teach Java-specific differences. NestJS ≈ Spring is a big transfer asset.
- [[Interface vs Abstract Class]] — `Understood`. Reviewed 2026-07-07 → Easy (decision axis solid, old "nhiều method" framing gone). sr-due 2026-07-11.
- [[Polymorphism]] / dynamic dispatch — `Understood` (Good). Saw it run (heterogeneous `List<INotify>` loop), hit the static-type gate (`sendBulk` blocked), grasped cast = per-expression static-type change. Output artifact written. sr-due 2026-07-08.
- [[Exception Handling]] — checked vs unchecked — `Understood` (Good). Self-derived the axis (uncontrollable-external → checked/forced vs own-logic-bug → unchecked); tied "force" to avoiding debit-without-credit inconsistency. Felt the real compile error; corrected a rollback-direction slip (debit vs credit). sr-due 2026-07-08.
- **Watch (Active gap):** tends to answer at label/describe level before the causal mechanism — push for "vì sao" at reviews before grading.
- Note: 2026-07-07 was a dense session (2 new concepts + 1 review). User absorbs slowly — watch retention on next review.
- Next concept: [[Collections Framework]] — List/Set/Map + Big-O (blind spot [[DSA]]). Opened challenge-first with the "ID exists?" lookup scenario.
