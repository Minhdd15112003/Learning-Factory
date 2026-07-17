# Mục tiêu — Java Backend

## Đích cuối

**Đủ kiến thức và năng lực để đi làm một Java Backend developer thực thụ** — nắm vững **toàn bộ bản đồ năng lực** bên dưới (Core Java → Spring → DB/ORM → kiến trúc → testing → DevOps → messaging/cache → nâng cao). Không chỉ trả lời lý thuyết mà **code được, triển khai được, và giải thích được cơ chế**.

Pass được phỏng vấn tầm ~2 năm kinh nghiệm ở các công ty fintech / banking / chứng khoán tại Việt Nam (MB Bank, NAB, Evotek, ASEAN Securities…) chỉ là **cột mốc kiểm chứng** năng lực, không phải trần.

## Vị trí hiện tại

Chưa bắt đầu. Chưa từng code Java / [[Spring Boot]] thực tế → học từ nền.

**Chỗ yếu nhất (ưu tiên đi chậm, soi kỹ):** [[DevOps]], [[Concurrency]], [[Design Patterns]], và [[DSA]] (chọn cấu trúc dữ liệu phù hợp + phân tích [[Big-O]]).

---

## Roadmap

### Giai đoạn 1 — Core Java (nền tảng)

- [ ] [[OOP]] đầy đủ: [[Encapsulation]], [[Inheritance]], [[Polymorphism]], [[Abstraction]]
- [ ] [[Collections Framework]]: [[List]], [[Map]], [[Set]], [[Queue]] + các implementation
- [ ] [[Exception Handling]]: checked / unchecked, custom exception
- [ ] Java 17/21: [[Stream API]], [[Optional]], [[Lambda]], `var`
- [ ] [[Concurrency]] cơ bản: [[Thread]], [[ExecutorService]], `synchronized` (nhận biết, chưa deep)
- [ ] [[Design Patterns]] — GoF nền tảng, học bằng Java thuần trước khi có framework:
  - [ ] [[Singleton]] — hiểu cơ chế; qua Giai đoạn 2, Spring IoC container làm việc này thay bạn, gần như không tự viết tay nữa
  - [ ] [[Factory]]
  - [ ] [[Builder]]
  - [ ] [[Strategy]]
  - [ ] [[Observer]]
  - [ ] [[Adapter]] — nền tảng, domain fintech sau này đụng liên tục khi tích hợp payment gateway / KYC provider ngoài
  - [ ] [[Decorator]] — nền để hiểu Spring AOP ở Giai đoạn 2
- [ ] [[DSA]] cơ bản: chọn cấu trúc phù hợp, phân tích [[Big-O]]

### Giai đoạn 2 — Spring Ecosystem (bắt buộc)

- [ ] [[Spring Boot]] (cấu hình, `application.yml`, [[profiles]], [[Actuator]])
- [ ] [[Spring MVC]]: controller, [[filter]], [[interceptor]], request mapping
- [ ] [[Spring Security]]: authentication / authorization cơ bản, session
- [ ] [[Spring Data JPA]] / [[Hibernate]]: entity mapping, [[JPQL]], lazy/eager, [[transaction]]
- [ ] [[Maven]] hoặc [[Gradle]]
- [ ] **Design Patterns — Spring chính là pattern được đóng gói sẵn, giai đoạn thực chiến nhất:**
  - [ ] [[Dependency Injection]] — Spring IoC container là hiện thân sống của DI; pattern quan trọng nhất cả roadmap
  - [ ] [[Proxy]] — `@Transactional`, Spring Security, Spring AOP chạy qua JDK dynamic proxy/CGLIB; giải thích được vì sao gọi method `@Transactional` từ trong cùng class không có tác dụng (câu hỏi phỏng vấn kinh điển)
  - [ ] [[Chain of Responsibility]] — filter chain và interceptor chain của Spring MVC/Security
  - [ ] [[Factory]] _(ôn lại có ví dụ thật)_ — `BeanFactory` / `ApplicationContext` / `FactoryBean`

### Giai đoạn 3 — Database & ORM

- [ ] RDBMS: [[MySQL]] / [[PostgreSQL]]
- [ ] [[SQL]] thuần: [[JOIN]], [[subquery]], `GROUP BY`, [[index]], query optimization nhẹ
- [ ] [[JPA]]/[[Hibernate]]: CRUD, quan hệ (`@OneToMany`, `@ManyToOne`), ý thức [[N+1]]
- [ ] [[PL/SQL]] cơ bản
- [ ] **Design Patterns:**
  - [ ] [[Repository]] — `JpaRepository` chính là tên pattern, học Spring Data JPA = học Repository trực tiếp
  - [ ] [[Unit of Work]] — `EntityManager`/Hibernate Session âm thầm làm việc này (gom thay đổi, flush khi commit); hiếm khi tự cài tay nhưng cần hiểu để debug lazy-loading, dirty checking
  - [ ] [[Data Mapper]] — Hibernate/JPA là hiện thân pattern này (domain object không biết gì về bảng DB)
  - Ghi chú: Active Record biết để so sánh (Rails, Laravel dùng) — Java/Spring gần như không dùng, không cần ưu tiên

### Giai đoạn 4 — Kiến trúc

- [ ] [[RESTful API]] design: HTTP methods, status codes, versioning, [[pagination]]
- [ ] [[Microservices]]: hiểu khái niệm đầu-đến-cuối **+ triển khai được** một service
- [ ] [[SOLID]] áp dụng vào code thực tế
- [ ] [[Agile]] / [[Scrum]]: sprint, daily standup, review, retro
- [ ] **Design Patterns:**
  - [ ] [[Strategy]] _(áp dụng SOLID thật)_ — ví dụ kinh điển minh hoạ Open/Closed Principle, dùng khi thiết kế nhiều luật xử lý theo loại giao dịch
  - [ ] [[State]] — thực chiến trong fintech: trạng thái giao dịch (Pending → Processing → Success/Failed), trạng thái KYC, trạng thái khoản vay
  - [ ] [[Adapter]] _(áp dụng thật)_ — gọi API bên thứ ba thật, không còn ví dụ lý thuyết
  - [ ] [[API Gateway]] _(khái niệm)_ — hiểu vai trò, triển khai thật để dành Giai đoạn 8

### Giai đoạn 7 — DevOps & công cụ

- [ ] [[Docker]] cơ bản (NAB, Evotek, ASEAN Securities yêu cầu)
- Ghi chú: không có pattern mới ở tầng code — [[Sidecar]] (Giai đoạn 8) mới thực sự liên quan khi có Kubernetes

### Giai đoạn 8 — Messaging & Cache (differentiator)

- [ ] [[Redis]]: caching, session store (must-have ở Evotek)
- [ ] [[Kafka]] / [[RabbitMQ]]: producer/consumer, [[event-driven]]
- [ ] **Design Patterns:**
  - [ ] [[Observer]] / Pub-Sub _(ôn lại, lần này thật)_ — Kafka/RabbitMQ producer-consumer chính là Observer ở quy mô hệ thống
  - [ ] [[Outbox]] — publish event lên Kafka đi kèm ghi DB cần atomic; trở thành bắt buộc chứ không còn tuỳ chọn
  - [ ] [[Proxy]] _(biến thể Cache-Aside)_ — Redis đứng trước DB call chính là một biến thể của Proxy

### Giai đoạn 9 — Nâng cao (tăng cạnh tranh & trần lương)

- [ ] [[Docker]] / [[Kubernetes]]
- [ ] [[OAuth2]] / [[JWT]] / [[OpenID]] / [[RBAC]]
- [ ] [[CI/CD]]: [[Jenkins]], [[GitLab CI]], [[ArgoCD]]
- [ ] [[Spring Cloud]] / [[API Gateway]]
- [ ] [[AWS]] / [[GCP]]
- [ ] [[NoSQL]] ([[MongoDB]]), [[ELK]] / [[Prometheus]] / [[Grafana]], [[gRPC]] / [[GraphQL]]
- [ ] [[DDD]] / [[CQRS]] / [[Event Sourcing]]
- [ ] Domain fintech / banking / bảo hiểm / chứng khoán
- [ ] **Design Patterns:**
  - [ ] [[Circuit Breaker]] — Spring Cloud + Resilience4j, bắt buộc khi hệ thống gọi service khác qua network
  - [ ] [[Bulkhead]] — Resilience4j cung cấp cùng lúc với Circuit Breaker, giới hạn thread/connection riêng cho từng dependency
  - [ ] [[Saga]] — giao dịch phân tán nhiều service, thực chiến trong fintech (chuyển tiền liên ngân hàng, xử lý đơn hàng nhiều bước)
  - [ ] [[CQRS]] _(đã có ở trên, đúng vị trí)_
  - [ ] [[Event Sourcing]] _(đã có ở trên, đúng vị trí)_
  - [ ] [[Sidecar]] — container phụ trong pod K8s xử lý logging/observability/mTLS tách khỏi service chính
  - [ ] [[API Gateway]] _(triển khai thật)_ — Spring Cloud Gateway
  - [ ] BFF — biến thể của API Gateway, ít khi bị hỏi riêng ở mức 2 năm kinh nghiệm, biết khái niệm là đủ

---

> Roadmap này còn thô — sẽ được tinh chỉnh sau vài buổi học đầu và bài đánh giá đầu vào (placement).
