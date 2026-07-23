# Mục tiêu & Lộ Trình Học Tập — Java Backend Architecture & Microservices

## Đích Cuối

**Đủ kiến thức và năng lực để làm việc như một Java Backend & Distributed Systems Engineer thực thụ.**
- Nắm vững toàn bộ bản đồ năng lực từ **Core Java $\rightarrow$ Spring Boot Modular Monolith $\rightarrow$ Architecture & DB $\rightarrow$ Distributed Messaging & Caching $\rightarrow$ Microservices Migration $\rightarrow$ DevOps & Observability**.
- Không chỉ hiểu lý thuyết mà **trực tiếp xây dựng, tối ưu và vận hành 1 hệ thống Fintech Digital Banking thực tế từ con số 0 lên Microservices hoàn chỉnh**.
- Đạt mốc kiểm chứng: Pass phỏng vấn Backend Developer ~2 năm kinh nghiệm tại các công ty fintech/banking/chứng khoán (MB Bank, NAB, Evotek, ASEAN Securities, VNPAY...).

---

## 🚀 Định Hướng 1 Dự Án Duy Nhất: Fintech Digital Banking Backend (`fintech-banking-system`)

Từ **Giai đoạn 2 trở đi**, toàn bộ kiến thức lý thuyết, kiến trúc, và Design Patterns sẽ được **áp dụng trực tiếp vào 1 dự án duy nhất** tên là `fintech-banking-system`.

```
[Giai đoạn 1: Core Java]
       ↓ (Nền tảng OOP, Collections, Concurrency, Design Patterns cơ bản)
[Giai đoạn 2: Spring Boot Modular Monolith & Hexagonal Architecture]
       ↓ (Phân tích User Story → DB Specs → Auth, Account, Payment, Notification trong 1 monolith)
[Giai đoạn 3: Database Optimization, Caching & Event-Driven Hardening]
       ↓ (PostgreSQL Indexing, Redis Cache-Aside, Kafka Event Streaming, Outbox Pattern, Docker)
[Giai đoạn 4: Refactor Monolith sang Microservices Architecture (Strangler Fig Pattern)]
       ↓ (Tách Auth Service, Account Service, Payment Service, Notification Service + API Gateway + Eureka + Saga)
[Giai đoạn 5: Advanced DevOps, Observability & Kubernetes Operations]
       ↓ (Docker Compose, Kubernetes Deployment, Prometheus/Grafana, Zipkin Tracing, CI/CD Pipeline)
```

---

## Roadmap Chi Tiết

### Giai đoạn 1 — Core Java & Foundational Patterns (Đã/đang hoàn thiện)

- [x] [[OOP]]: [[Encapsulation]], [[Inheritance]], [[Polymorphism]], [[Abstraction]]
- [x] [[Collections Framework]]: [[List - ArrayList vs LinkedList|List]], [[Collections Framework - HashMap|HashMap & Set]]
- [x] [[Exception Handling]]: Checked vs Unchecked exceptions, custom handling
- [x] Modern Java (17/21): [[Stream API]], [[Optional]], [[Java Modern Features - var và record|var & record]]
- [x] [[Concurrency - Thread và Shared Memory|Concurrency & Multithreading]]: [[Concurrency - Thread và Shared Memory|Threads]], [[Concurrency - ExecutorService và Future|ExecutorService]], [[Concurrency - ThreadPoolExecutor và RejectedExecutionHandler|ThreadPoolExecutor]], [[Concurrency - CompletableFuture|CompletableFuture]]
- [x] **Design Patterns Nền tảng (GoF Java thuần):**
  - [x] [[Design Pattern - Singleton|Singleton]]
  - [x] [[Design Pattern - Factory|Factory]]
  - [x] [[Design Pattern - Builder|Builder]]
  - [x] [[Design Pattern - Strategy|Strategy]]
  - [x] [[Design Pattern - Observer|Observer]]
  - [x] [[Design Pattern - Adapter|Adapter]]
  - [x] [[Design Pattern - Decorator|Decorator]]

---

### Giai đoạn 2 — Spring Boot Modular Monolith & Hexagonal Architecture (Khởi Tạo Dự Án Duy Nhất)

*Mục tiêu:* Khởi tạo dự án `fintech-banking-system` chuẩn Clean/Hexagonal Architecture, sẵn sàng mở rộng và phân tách service về sau.

- [ ] **Phân Tích Dự Án & Thiết Kế Database (System Design cơ bản):**
  - Phân tích từ UI $\rightarrow$ User Story $\rightarrow$ Features & API Specs $\rightarrow$ DB Schema.
  - Phân tích thông số tải, độ lớn dữ liệu, chiến lược đánh Index và chuẩn hóa dữ liệu.
- [ ] **Khởi Tạo Spring Boot Monolith Base:**
  - Spring Boot (Core, Web, Actuator, Profiles, `application.yml`).
  - RESTful API Specs: Standardization Response format, Global Exception Handler (`@ControllerAdvice`).
- [ ] **Kiến Trúc Hexagonal / Ports & Adapters (Clean Architecture):**
  - Chia gói theo Domain Bounded Contexts: Auth, Account, Payment, Notification.
  - Phân tách lõi Domain Logic độc lập với Framework/DB thông qua Ports (Interfaces) và Adapters.
- [ ] **Identity & Security Service (Authentication & Authorization):**
  - Spring Security, JWT (JSON Web Token) authentication flow, Refresh token, Role-based Access Control (RBAC).
- [ ] **Design Patterns cài đặt trực tiếp vào Dự án:**
  - [[Dependency Injection]] (IoC Container) — Quản lý lifecycle & injection tự động.
  - [[Chain of Responsibility]] — Spring Security Filter Chain & Interceptors xử lý auth/logging.
  - [[Strategy]] — Xử lý linh hoạt các luật tính cước/phí chuyển tiền theo loại tài khoản.
  - [[Adapter]] — Tích hợp Cổng thanh toán 3rd-party (VNPAY/Momo mock integration).

---

### Giai đoạn 3 — Database, Caching & Event-Driven Hardening (Tiền Đề Microservices)

*Mục tiêu:* Tối ưu hiệu năng dữ liệu, thêm Caching và Messaging bất đồng bộ vào Monolith trước khi tách service.

- [ ] **Database Optimization & ORM (PostgreSQL & Spring Data JPA):**
  - Hibernate/JPA mapping (`@OneToMany`, `@ManyToOne`, `@ManyToMany`).
  - Xử lý triệt để bài toán [[N+1]], Lazy/Eager loading, Dirty checking.
  - [[Transaction Isolation Levels]], Optimistic / Pessimistic Locking chống Race Condition khi chuyển tiền trùng thời điểm.
- [ ] **Caching & Storage Strategy (Redis & AWS S3/Local Media Service):**
  - Redis Integration: Session Store, Cache-Aside Pattern cho thông tin tài khoản & danh mục.
  - Phòng chống các lỗi Cache kinh điển: Cache Stampede, Cache Penetration, Cache Breakdown.
  - Media Upload Service: Upload ảnh KYC, Avatar lên Local / S3 Bucket với AWS CloudFront / presigned URL.
- [ ] **Event-Driven Architecture & Messaging (Kafka / RabbitMQ):**
  - Event-Driven Architecture, Event-Storming khái niệm.
  - Queue vs PubSub: Producer & Consumer xử lý gửi Mail/SMS Notification và Audit Logging bất đồng bộ.
  - **Outbox Pattern:** Đảm bảo tính nhất quán dữ liệu giữa DB commit và Kafka Event publish (Atomic operation).
- [ ] **Design Patterns cài đặt trực tiếp vào Dự án:**
  - [[Repository]] & [[Unit of Work]] — Abstract hóa thao tác DB với Hibernate.
  - [[Proxy]] (Cache-Aside Proxy) — Caching interceptor cho service call.
  - [[Outbox Pattern]] — Transactional Outbox pattern cho Event Streaming.

---

### Giai đoạn 4 — Refactor Monolith Sang Microservices Architecture (Strangler Fig Pattern)

*Mục tiêu:* Áp dụng mẫu hình Strangler Fig tách dự án duy nhất `fintech-banking-system` thành hệ thống Microservices độc lập.

- [ ] **Phân Tách Microservices Độc Lập:**
  - Tách Monolith thành các Microservices:
    1. **Identity-Service:** Auth, User, OTP, JWT Issuance.
    2. **Account-Service:** Số dư, thông tin tài khoản, lịch sử giao dịch.
    3. **Payment-Service:** Nạp/rút/chuyển tiền, tích hợp gateway.
    4. **Notification-Service:** Gửi Mail/SMS, Push notification.
- [ ] **Microservices Infrastructure & Gateway:**
  - **Spring Cloud Gateway (API Gateway):** Single entrypoint, Dynamic Routing, Centralized Rate Limiting & Auth propagation.
  - **Spring Cloud Netflix Eureka (Service Discovery):** Dynamic registration & resolution.
  - **Spring Cloud Config Server:** Centralized configuration management với Git backend.
- [ ] **Distributed Transactions & Resiliency:**
  - **Saga Pattern (Choreography / Orchestration):** Xử lý giao dịch phân tán giữa Account-Service và Payment-Service (Chuyển tiền liên ngân hàng).
  - **CQRS (Command Query Responsibility Segregation):** Tách luồng Read/Write phục vụ tra cứu số dư chịu tải cao.
  - **Resilience4j:** Circuit Breaker, Bulkhead, Retry, Rate Limiter chống rớt mạng dây chuyền (Cascading Failure).
- [ ] **Design Patterns cài đặt trực tiếp vào Dự án:**
  - [[Circuit Breaker]] / Bulkhead — Giám sát & cô lập lỗi network call.
  - [[Saga Pattern]] — Quản lý giao dịch phân tán nhiều bước.
  - [[API Gateway]] — Dynamic routing & cross-cutting concern handling.

---

### Giai đoạn 5 — Triển Khai, Vận Hành & Observability (DevOps & Distributed Systems)

*Mục tiêu:* Đóng gói, triển khai cluster Microservices lên Docker/Kubernetes và thiết lập hệ thống giám sát phân tán.

- [ ] **Containerization & Deployment với Docker:**
  - Viết `Dockerfile` tối ưu (Multi-stage build) cho từng Microservice.
  - Viết `docker-compose.yml` chạy toàn bộ stack: Microservices + PostgreSQL + Redis + Kafka + Eureka + Gateway.
- [ ] **Distributed Observability & Monitoring:**
  - **Metrics:** Prometheus + Grafana Dashboard theo dõi CPU, RAM, JVM Heap, Request throughput.
  - **Distributed Tracing:** Micrometer Tracing / Zipkin / Jaeger theo dõi trace HTTP request qua nhiều service.
  - **Centralized Logging:** ELK Stack (Elasticsearch, Logstash, Kibana) gom log tập trung.
- [ ] **Kubernetes Orchestration & CI/CD:**
  - Deploy cluster lên Kubernetes (Pods, Deployments, Services, Ingress, ConfigMaps, Secrets).
  - Viết CI/CD Pipeline với GitHub Actions / GitLab CI tự động hóa Build, Test, và Deploy.

---

## 📜 Quy Trình & Tiêu Chuẩn Học Tập (Áp Dụng Cho Tất Cả Các Bài)

1. **Đặt câu hỏi:** Trực diện, rõ ràng, tập trung vào bản chất cơ chế, tuyệt đối không đánh đố.
2. **File Lý Thuyết (`00 Theory/<Topic_Folder>/`):** Phải viết chi tiết đầy đủ 4 phần (Lý thuyết cốt lõi, Insights do AI giảng dạy, Lý do tồn tại/nỗi đau giải quyết, Cách sử dụng & use cases thực tế + trade-offs).
3. **Thư Mục Phân Loại:** Lưu trữ file lý thuyết và thực hành theo từng thư mục con (`01 Core Java/`, `02 Concurrency/`, `03 Design Patterns/`, `04 Spring Framework/`, `05 Database & ORM/`, `06 Architecture & Security/`, `07 Messaging & Caching/`, `08 Microservices/`, `09 DevOps & Cloud/`).
4. **Giới Hạn Ôn Tập Spaced Repetition:** Những note/chủ đề đã được kiểm tra (Feynman/SR pass) **từ 2 lần trở lên (`review-count >= 2`)** sẽ không nhắc lại trong các buổi hỏi hàng ngày để tập trung học kiến thức mới.
