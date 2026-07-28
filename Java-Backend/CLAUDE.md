# Java-Backend — Subject

> Inherits the vault-root `CLAUDE.md` (Teaching Contract, Knowledge Model, Spaced Repetition, Language) automatically. This file holds identity + status.

Learning Java Backend toward: becoming a job-ready Java Backend & Distributed Systems Engineer with real mastery of the full competency map — Core Java → Spring ecosystem → DB/ORM → architecture → testing → DevOps → messaging/cache → microservices.

**Core Learning Approach:**
Starting from Stage 2 onwards, build **ONE SINGLE UNIFIED PROJECT**: `commerce-fulfillment-system` (E-commerce Order & Fulfillment Backend). ALL technologies provided in the curriculum are learned and implemented step-by-step:

- **Stage 1 (Core Java & Patterns):** Java 17/21, Maven, OOP, Collections, Concurrency, GoF Design Patterns.
- **Stage 2 (Modular Monolith DDD + Hexagonal):** Strategic DDD, Tactical DDD, Ports & Adapters, Spring Boot 3, Spring Security, JWT, Lombok, MapStruct, Swagger/OpenAPI, Spring Modulith, ArchUnit, REST vs GraphQL vs RPC.
- **Stage 3 (Database, Caching, Messaging & Storage):** PostgreSQL, MySQL, Spring Data JPA, Hibernate, Flyway, Liquibase, MongoDB (NoSQL), Redis (Cache-Aside, Locks), MinIO, AWS S3, CloudFront, Kafka, RabbitMQ, Transactional Outbox Pattern, Docker & Compose.
- **Stage 4 (Microservices Transformation):** Strangler Fig Pattern, Spring Cloud Gateway, Eureka Discovery, Config Server, OpenFeign, Resilience4j (Circuit Breaker, Rate Limiter, Bulkhead), gRPC, Protobuf, OAuth2/OIDC, Keycloak IAM, Saga Pattern, CQRS/CQS.
- **Stage 5 (Advanced DevOps & Distributed Systems Operations):** Multi-stage Docker, Kubernetes (K8s), Helm Charts, Spring Boot Actuator, Prometheus, Grafana, Micrometer Tracing, Zipkin, ELK Stack, GitHub Actions / GitLab CI / Jenkins, SonarQube, Nexus.

**Teaching Rules & Standards:**

- Direct, clear, unambiguous Socratic questions.
- Detailed 4-part theory notes in `00 Theory/<Topic_Folder>/`.
- Reorganized subfolder structure for `00 Theory/` and `01 Practice/`.
- Max 2-review limit rule: skip daily Socratic review for notes with `review-count >= 2`.

**⚠️ CRITICAL TEACHING GUARDRAILS — NEVER VIOLATE:**

1. **Seed before use:** NEVER use a technical term in a question or explanation before that exact term has been explicitly defined and seeded to the user. This was violated in the first DDD session: `Bounded Context` was used as if the user already knew it, before any definition was given. Result: the entire session became confusing. If you are about to use a term that has no corresponding note in `00 Theory/` with status >= `Partial`, STOP — define it first (2–3 sentences: what it is, what problem it solves, one contrast), THEN continue.
2. **One unfamiliar term per turn:** Do not introduce more than one new term per reply. If explaining `Bounded Context` requires mentioning `Domain Event`, note that `Domain Event` will be explained next — do not explain both in the same turn.
3. **Check the note before the session:** At the start of every DDD/Architecture topic session, read the existing notes in `00 Theory/04 Domain-Driven Design/` and `00 Theory/05 Architecture/`. If a concept has no note yet, it MUST be seeded before being used in any question.

Flow per topic: Lý thuyết (`00 Theory/<Topic>/`) $\rightarrow$ Thực hành (`01 Practice/<Topic>/`) $\rightarrow$ Output (`02 Output/`).

## Current Status

> Last updated: 2026-07-28

- Placement done: strong OOP + backend background (Python/FastAPI, JS/Express+NestJS, Go/Gin); first time with Java.
- Reorganized `00 Theory/` and `01 Practice/` into topic subfolders (`01 Core Java/`, `02 Concurrency/`, `03 Design Patterns/`).
- Fully updated Roadmap to 1 Single E-commerce Project (`commerce-fulfillment-system`) evolving from Monolith to Microservices. Domain: E-commerce Order & Fulfillment Backend.

### Completed Concepts (Stage 1 Core Java & Design Patterns):

- [[01 Core Java/Interface vs Abstract Class|Interface vs Abstract Class]] — `Understood` (review-count: 1)
- [[01 Core Java/Polymorphism|Polymorphism]] — `Understood` (review-count: 1)
- [[01 Core Java/Exception Handling|Exception Handling]] — `Understood` (review-count: 1)
- [[01 Core Java/Collections Framework - HashMap|HashMap / HashSet]] — `Understood` (review-count: 1)
- [[01 Core Java/List - ArrayList vs LinkedList|ArrayList vs LinkedList]] — `Understood` (review-count: 1)
- [[01 Core Java/Stream API|Stream API]] — `Understood` (review-count: 1)
- [[01 Core Java/Optional|Optional]] — `Understood` (review-count: 1)
- [[01 Core Java/Java Modern Features - var và record|var & record]] — `Understood` (review-count: 1)
- [[02 Concurrency/Concurrency - Thread và Shared Memory|Thread & Shared Memory]] — `Understood` (review-count: 1)
- [[02 Concurrency/Concurrency - ExecutorService và Future|ExecutorService & Future]] — `Understood` (review-count: 1)
- [[02 Concurrency/Concurrency - ThreadPoolExecutor và RejectedExecutionHandler|ThreadPoolExecutor]] — `Understood` (review-count: 1)
- [[02 Concurrency/Concurrency - CompletableFuture|CompletableFuture]] — `Understood` (review-count: 1)
- [[03 Design Patterns/Design Pattern - Singleton|Singleton]] — `Understood` (review-count: 1)
- [[03 Design Patterns/Design Pattern - Factory|Factory]] — `Understood` (review-count: 1)
- [[03 Design Patterns/Design Pattern - Builder|Builder]] — `Understood` (review-count: 1)
- [[03 Design Patterns/Design Pattern - Strategy|Strategy]] — `Understood` (review-count: 1)
- [[03 Design Patterns/Design Pattern - Observer|Observer]] — `Understood` (review-count: 1)
- [[03 Design Patterns/Design Pattern - Adapter|Adapter]] — `Understood` (review-count: 1)
- [[03 Design Patterns/Design Pattern - Decorator|Decorator]] — `Understood` (review-count: 1)

### Stage 2 In Progress (DDD + Hexagonal Architecture):

**⚠️ NOTE — Root cause of today's session failure:**
The previous AI session opened with Strategic DDD and used `Bounded Context`, `Domain Event`, `Saga` etc. without defining any of them first. The user was forced to ask "tôi vẫn không hiểu Bounded Context là gì" and "tôi vẫn chưa biết bản chất của DDD là gì" mid-session. Definitions were only given reactively after the user's frustration. Going forward, the Guardrails above MUST be followed.

- [[04 Domain-Driven Design/Strategic DDD|Strategic DDD]] — `Partial` (review-count: 0)
  - Concepts demonstrated through conversation but Feynman gate not formally closed:
    - ✅ **DDD là gì** — user explained in own words
    - ✅ **Bounded Context** — user understands the "phòng ban" analogy; confirmed it's the basis for future Microservices split
    - ✅ **Command (Sync) vs Domain Event (Async)** — user correctly identified Order→Inventory as sync, Payment/Notification/Fulfillment as async, with correct reasoning
    - ✅ **Inventory Reservation** — user explained over-selling scenario correctly
    - ✅ **Saga (core principle)** — user grasped compensating transaction ("khôi phục sản phẩm về đơn hàng")
    - ✅ **Hexagonal Architecture 3 tầng** — user correctly mapped Order→domain, PlaceOrderUseCase→application, OrderJpaAdapter→infrastructure
    - ✅ **Port/Adapter boundary** — user understands Port is interface in domain; Adapter implements it in infrastructure
    - ⬜ Feynman gate for full Strategic DDD not formally closed — needs `/done` session

- **Next Step:** Buổi tiếp theo bắt đầu bằng Feynman gate chốt `Strategic DDD.md` → sau đó chuyển sang **Tactical DDD** (Aggregate, Entity, Value Object, Domain Service).
