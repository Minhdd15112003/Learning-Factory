# Java-Backend — Subject

> Inherits the vault-root `CLAUDE.md` (Teaching Contract, Knowledge Model, Spaced Repetition, Language) automatically. This file holds identity + status.

Learning Java Backend toward: becoming a job-ready Java Backend & Distributed Systems Engineer with real mastery of the full competency map — Core Java → Spring ecosystem → DB/ORM → architecture → testing → DevOps → messaging/cache → microservices.

**Core Learning Approach:**
Starting from Stage 2 onwards, build **ONE SINGLE UNIFIED PROJECT**: `fintech-banking-system` (Digital Banking & Wallet Backend). ALL technologies provided in the curriculum are learned and implemented step-by-step:
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

Flow per topic: Lý thuyết (`00 Theory/<Topic>/`) $\rightarrow$ Thực hành (`01 Practice/<Topic>/`) $\rightarrow$ Output (`02 Output/`).

## Current Status
> Last updated: 2026-07-23

- Placement done: strong OOP + backend background (Python/FastAPI, JS/Express+NestJS, Go/Gin); first time with Java.
- Reorganized `00 Theory/` and `01 Practice/` into topic subfolders (`01 Core Java/`, `02 Concurrency/`, `03 Design Patterns/`).
- Fully updated Roadmap to 1 Single Fintech Project (`fintech-banking-system`) evolving from Monolith to Microservices, incorporating 100% of all technologies across the 5 stages.

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

- **Next Step:** Chốt Giai đoạn 1 (DSA cơ bản / Placement tổng kết Stage 1) $\rightarrow$ Khởi tạo dự án duy nhất `fintech-banking-system` ở Giai đoạn 2 (Spring Boot 3 + DDD + Hexagonal Architecture).
