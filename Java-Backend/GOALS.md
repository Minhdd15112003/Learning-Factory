# Mục tiêu & Lộ Trình Học Tập — Java Backend Architecture & Microservices

## Đích Cuối

**Đủ kiến thức và năng lực để làm việc như một Java Backend & Distributed Systems Engineer thực thụ.**
- Nắm vững toàn bộ bản đồ năng lực từ **Core Java $\rightarrow$ Spring Boot Modular Monolith (DDD + Hexagonal) $\rightarrow$ Architecture & DB $\rightarrow$ Distributed Messaging & Caching $\rightarrow$ Microservices Migration $\rightarrow$ DevOps & Observability**.
- Trực tiếp học, áp dụng và thực hành **TOÀN BỘ CÁC CÔNG NGHỆ BACKEND** vào **1 DỰ ÁN DUY NHẤT** tên là `fintech-banking-system` từ con số 0 lên Microservices hoàn chỉnh.
- Đạt mốc kiểm chứng: Pass phỏng vấn Backend Developer ~2 năm kinh nghiệm tại các công ty fintech/banking/chứng khoán (MB Bank, NAB, Evotek, ASEAN Securities, VNPAY...).

---

## 🚀 Định Hướng 1 Dự Án Duy Nhất: Fintech Digital Banking Backend (`fintech-banking-system`)

Từ **Giai đoạn 2 trở đi**, toàn bộ kiến thức lý thuyết, kiến trúc, công cụ và Design Patterns sẽ được **áp dụng trực tiếp vào 1 dự án duy nhất**: `fintech-banking-system`.

```
[Giai đoạn 1: Core Java & Foundational Patterns]
       ↓ (Java 17/21, Maven, OOP, Collections, Concurrency, GoF Design Patterns)
[Giai đoạn 2: Spring Boot Modular Monolith (DDD + Hexagonal Architecture)]
       ↓ (Strategic DDD, Modular Monolith, Spring Boot 3, Spring Security, JWT, Lombok, MapStruct, Swagger/OpenAPI, ArchUnit, GraphQL/RPC)
[Giai đoạn 3: Database Optimization, Caching, Event-Driven & Storage]
       ↓ (PostgreSQL, MySQL, Flyway, Liquibase, Redis, MinIO, AWS S3, CloudFront, Kafka, RabbitMQ, Outbox Pattern, MongoDB)
[Giai đoạn 4: Refactor Monolith sang Microservices Architecture (Strangler Fig Pattern)]
       ↓ (Tách Services, Spring Cloud Gateway, Eureka, Config Server, OpenFeign, Resilience4j, gRPC/Protobuf, OAuth2/OIDC, Keycloak, Saga, CQRS)
[Giai đoạn 5: Advanced DevOps, Observability & Kubernetes Operations]
       ↓ (Docker, Docker Compose, Kubernetes, Helm, Actuator, Prometheus, Grafana, Micrometer Tracing, Zipkin, ELK Stack, CI/CD, SonarQube, Nexus)
```

---

## Roadmap Chi Tiết

### Giai đoạn 1 — Core Java & Foundational Patterns (Đã/đang hoàn thiện)

- [x] [[01 Core Java/Polymorphism|OOP]]: Encapsulation, Inheritance, Polymorphism, Abstraction
- [x] [[01 Core Java/Collections Framework - HashMap|Collections Framework]]: List (ArrayList, LinkedList), Set (HashSet), Map (HashMap)
- [x] [[01 Core Java/Exception Handling|Exception Handling]]: Checked vs Unchecked exceptions, Custom Exceptions
- [x] Modern Java (17/21): [[01 Core Java/Stream API|Stream API]], [[01 Core Java/Optional|Optional]], [[01 Core Java/Java Modern Features - var và record|var & record]]
- [x] [[02 Concurrency/Concurrency - Thread và Shared Memory|Concurrency & Multithreading]]: Threads, Shared Memory, ExecutorService, ThreadPoolExecutor, RejectedExecutionHandler, CompletableFuture
- [x] Maven: Dependency management, pom.xml configuration, build lifecycle
- [x] **Design Patterns Nền tảng (GoF Java thuần):**
  - [x] [[03 Design Patterns/Design Pattern - Singleton|Singleton]]
  - [x] [[03 Design Patterns/Design Pattern - Factory|Factory]]
  - [x] [[03 Design Patterns/Design Pattern - Builder|Builder]]
  - [x] [[03 Design Patterns/Design Pattern - Strategy|Strategy]]
  - [x] [[03 Design Patterns/Design Pattern - Observer|Observer]]
  - [x] [[03 Design Patterns/Design Pattern - Adapter|Adapter]]
  - [x] [[03 Design Patterns/Design Pattern - Decorator|Decorator]]

---

### Giai đoạn 2 — Spring Boot Modular Monolith: DDD & Hexagonal Architecture

*Mục tiêu:* Khởi tạo dự án duy nhất `fintech-banking-system` bằng DDD + Hexagonal Architecture với đầy đủ các công cụ và chuẩn API.

- [ ] **Strategic Domain-Driven Design (DDD) & System Design:**
  - Phân tích User Stories $\rightarrow$ Event Storming $\rightarrow$ Định hình Ubiquitous Language.
  - Phân chia các **Bounded Contexts**: *Auth Context*, *Account Context*, *Payment Context*, *Notification Context*.
  - Xây dựng **Context Map** (sơ đồ giao tiếp giữa các Bounded Contexts).
- [ ] **Tactical DDD & Hexagonal Core (Ports & Adapters):**
  - Pure Java Domain Core (0 phụ thuộc Spring/DB): Aggregates (`Account`), Entities, Value Objects (`Money`, `AccountNumber`), Domain Events (`MoneyTransferredEvent`), Domain Services.
  - Ports: Inbound Use Cases (`TransferMoneyUseCase`), Outbound Ports (`AccountRepositoryPort`, `PaymentGatewayPort`).
- [ ] **Spring Framework Core & Base Libraries:**
  - **Spring Boot 3.x & Spring Framework:** Auto-configuration, IoC Container, Dependency Injection.
  - **Spring MVC:** Request Mapping, Controllers, Filter, Interceptor, Global Exception Handling (`@ControllerAdvice`).
  - **Lombok:** Annotations (`@Getter`, `@Setter`, `@Builder`, `@RequiredArgsConstructor`).
  - **MapStruct / ModelMapper:** Chuyển đổi dữ liệu tự động giữa Entities $\leftrightarrow$ Domain Objects $\leftrightarrow$ DTOs.
  - **Swagger / OpenAPI:** Tự động tạo trang tài liệu tương tác API (`/swagger-ui.html`).
- [ ] **Security & Identity:**
  - **Spring Security & JWT:** Stateless authentication, JWT Access Token & Refresh Token, Role-Based Access Control (RBAC).
- [ ] **Kiểm Soát Ranh Giới Kiến Trúc:**
  - **Spring Modulith & ArchUnit:** Viết unit test tự động đảm bảo gói `domain` tuyệt đối không dính annotation của Spring/JPA/Web.
- [ ] **Giao Tiếp API & Standard:**
  - RESTful API Standards: Response DTO & Error format chuẩn hóa.
  - Tìm hiểu & Thực hành so sánh REST API vs GraphQL vs RPC.
- [ ] **Design Patterns cài đặt vào dự án:** [[Dependency Injection]], [[Chain of Responsibility]] (Security Filter Chain), [[Strategy]] (áp dụng tính phí chuyển tiền), [[Adapter]] (tích hợp mock cổng thanh toán).

---

### Giai đoạn 3 — Database Optimization, Caching, Event-Driven & Storage Hardening

*Mục tiêu:* Tối ưu hóa CSDL quan hệ, NoSQL, Caching, Media Storage và Messaging bất đồng bộ cho Monolith.

- [ ] **Database & ORM (PostgreSQL & MySQL & Spring Data JPA):**
  - PostgreSQL & MySQL RDBMS: Indexing, Query Optimization, JOIN, Subquery, GROUP BY.
  - Spring Data JPA & Hibernate: Entity Mappings, JPQL, Dynamic Specification, N+1 Handling, Lazy/Eager Loading.
  - Concurrency & Transaction Control: Transaction Isolation Levels, Optimistic Locking (`@Version`), Pessimistic Locking chống Race Condition khi chuyển tiền trùng thời điểm.
- [ ] **Database Versioning & Migration (Flyway & Liquibase):**
  - **Flyway & Liquibase:** Tự động hóa quản lý các phiên bản thay đổi schema DB (file SQL/XML/YAML) đồng bộ giữa các môi trường dev/staging/prod.
- [ ] **NoSQL Database (MongoDB):**
  - **MongoDB Integration:** Lưu trữ dữ liệu dạng Document (JSON-like) cho dữ liệu phi cấu trúc (Audit log, User Activity Log). So sánh RDBMS vs NoSQL.
- [ ] **Caching & Memory Strategy (Redis):**
  - **Redis Integration:** In-memory Caching, Cache-Aside Strategy, Distributed Session, Distributed Locks với Redisson.
  - Phòng chống các sự cố Cache: Cache Stampede, Cache Penetration, Cache Breakdown.
- [ ] **Object Storage & Media Service (MinIO & AWS S3 & CloudFront):**
  - Upload Service: Upload file/media lên Local storage và **MinIO** (S3-compatible Object Storage).
  - Tích hợp **AWS S3** và **AWS CloudFront** với Presigned URL để truy xuất media hiệu quả.
- [ ] **Event-Driven Architecture & Messaging (Kafka & RabbitMQ):**
  - Event-Driven Architecture, Event-Storming, Event Sourcing.
  - **RabbitMQ:** Queue vs PubSub cơ chế AMQP.
  - **Apache Kafka:** Event Streaming chịu tải lớn cho luồng sự kiện chuyển tiền & thông báo.
  - **Transactional Outbox Pattern:** Đảm bảo tính nhất quán (Atomic operation) giữa DB update và Event publish lên Kafka.
- [ ] **DevOps Môi Trường Dev:** Docker & Docker Compose chạy toàn bộ stack local.
- [ ] **Design Patterns cài đặt vào dự án:** [[Repository]], [[Unit of Work]], [[Proxy]] (Cache-Aside Proxy), [[Outbox Pattern]], [[Observer]].

---

### Giai đoạn 4 — Refactor Monolith Sang Microservices Architecture (Strangler Fig Pattern)

*Mục tiêu:* Tách các Hexagonal Bounded Contexts thành các Microservices độc lập.

- [ ] **Bóc Tách Microservices (Strangler Fig Pattern):**
  - Tách Monolith thành các Microservices độc lập:
    1. `identity-service` (Port 8081)
    2. `account-service` (Port 8082)
    3. `payment-service` (Port 8083)
    4. `notification-service` (Port 8084)
  - Cấu trúc Model độc lập và Database-per-Service.
- [ ] **Spring Cloud Ecosystem & Netflix OSS:**
  - **Spring Cloud Gateway:** Single Entrypoint, Dynamic Routing, Centralized Rate Limiting, Authentication Propagation.
  - **Spring Cloud Netflix Eureka (Service Registry & Discovery):** Tự động đăng ký IP/Port và định tuyến động.
  - **Spring Cloud Config Server:** Quản lý và phân phối cấu hình tập trung từ Git repository.
  - **Spring Cloud OpenFeign:** Declarative HTTP Client giúp các microservices gọi REST API của nhau đơn giản như gọi hàm Java.
- [ ] **Resiliency & Fault Tolerance (Resilience4j):**
  - **Resilience4j:** Xử lý chịu lỗi với Circuit Breaker, Rate Limiter, Bulkhead, Retry để cô lập sự cố, tránh kéo sập toàn hệ thống.
- [ ] **Enterprise IAM & Single Sign-On (OAuth2 & Keycloak):**
  - **OAuth2 & OpenID Connect (OIDC):** Chuẩn ủy quyền & định danh doanh nghiệp.
  - **Keycloak Integration:** Tích hợp Identity Service tập trung hỗ trợ Single Sign-On (SSO).
- [ ] **High-Performance Communication (gRPC & Protobuf):**
  - **gRPC & Protocol Buffers:** Xây dựng luồng giao tiếp nhị phân tốc độ cao, độ trễ cực thấp giữa `payment-service` và `account-service`.
- [ ] **Distributed Transactions & Architectural Patterns:**
  - **Saga Pattern (Orchestration / Choreography):** Quản lý giao dịch phân tán nhiều bước khi chuyển tiền liên dịch vụ.
  - **CQRS (Command Query Responsibility Segregation) & CQS:** Tách biệt luồng Ghi (Command) và luồng Đọc (Query).
- [ ] **Design Patterns cài đặt vào dự án:** [[Circuit Breaker]], [[Bulkhead]], [[Saga Pattern]], [[API Gateway]], [[CQRS]].

---

### Giai đoạn 5 — Advanced DevOps, Observability & Kubernetes Operations

*Mục tiêu:* Đóng gói, triển khai cluster Microservices và thiết lập hệ thống giám sát phân tán chuẩn Production.

- [ ] **Containerization & Deployment với Docker:**
  - Build Image tối ưu với Multi-stage `Dockerfile`.
  - Triển khai toàn bộ ứng dụng với `docker-compose.yml`.
- [ ] **Distributed Observability & Monitoring:**
  - **Spring Boot Actuator:** Mở các HTTP endpoints theo dõi health, metrics, environment.
  - **Prometheus:** Thu thập và lưu trữ metrics thời gian thực (Time-Series).
  - **Grafana:** Trực quan hóa dữ liệu chỉ số hệ thống qua Dashboard.
  - **Micrometer Tracing & Zipkin:** Đính kèm Trace ID & Span ID theo dõi hành trình request qua các microservices phân tán.
- [ ] **Centralized Logging (ELK Stack):**
  - **ELK Stack (Elasticsearch, Logstash/Filebeat, Kibana):** Gom log từ tất cả microservices về một nơi để truy vấn và phân tích sự cố.
- [ ] **Kubernetes Orchestration & Package Management (K8s & Helm):**
  - **Kubernetes (K8s):** Deploy cluster (Pods, Services, Deployments, StatefulSets, Ingress, ConfigMaps, Secrets).
  - **Helm Charts:** Quản lý và đóng gói ứng dụng K8s phức tạp.
- [ ] **CI/CD Pipeline & Code Quality Governance:**
  - **GitHub Actions / GitLab CI / Jenkins:** Dựng đường ống tự động hóa build, unit test, ArchUnit test, docker build và deploy.
  - **SonarQube:** Quét chất lượng code tự động (Static Code Analysis), phát hiện bugs, lỗ hổng bảo mật và code smells.
  - **Nexus:** Repository lưu trữ tập trung các artifact đóng gói (JAR, Docker images).
- [ ] **CV, Job Description Analysis & Certification Preparation:**
  - Phân tích JD, chiến lược ứng tuyển, review CV ấn tượng cho các công ty Fintech/Banking.

---

## 📜 Quy Trình & Tiêu Chuẩn Học Tập (Áp Dụng Cho Tất Cả Các Bài)

1. **Đặt câu hỏi:** Trực diện, rõ ràng, tập trung vào bản chất cơ chế, tuyệt đối không đánh đố.
2. **File Lý Thuyết (`00 Theory/<Topic_Folder>/`):** Phải viết chi tiết đầy đủ 4 phần (Lý thuyết cốt lõi, Insights do AI giảng dạy, Lý do tồn tại/nỗi đau giải quyết, Cách sử dụng & use cases thực tế + trade-offs).
3. **Thư Mục Phân Loại:** Lưu trữ file lý thuyết và thực hành theo từng thư mục con (`01 Core Java/`, `02 Concurrency/`, `03 Design Patterns/`, `04 Spring Framework/`, `05 Database & ORM/`, `06 Architecture & Security/`, `07 Messaging & Caching/`, `08 Microservices/`, `09 DevOps & Cloud/`).
4. **Giới Hạn Ôn Tập Spaced Repetition:** Những note/chủ đề đã được kiểm tra (Feynman/SR pass) **từ 2 lần trở lên (`review-count >= 2`)** sẽ không nhắc lại trong các buổi hỏi hàng ngày để tập trung học kiến thức mới.
