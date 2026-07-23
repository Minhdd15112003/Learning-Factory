# Chương Trình Khóa Học Backend Architecture & Microservices

| Module                                                                             | Tiêu điểm chính                                                                                                                                                                                                                                                                                                     |
| :--------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **1. Typescript và OOP, SOLID**                                                    | - Cơ bản syntax Typescript<br>- OOP và S.O.L.I.D trong Typescript.<br>- Các patterns quan trọng thường dùng trong kiến trúc backend.                                                                                                                                                                                |
| **2. Phân tích dự án & thiết kế database**                                         | - Phân tích dự án: từ UI $\rightarrow$ User Story $\rightarrow$ Features & APIs $\rightarrow$ Database.<br>- Tìm hiểu thông số tải, độ lớn dữ liệu, tốc độ phát sinh dữ liệu.<br>- Thiết kế hệ thống microservices (system design cơ bản).<br>- Các chia sẻ tăng tốc truy xuất dữ liệu.                             |
| _Ôn tập và thực hành_                                                              | - **Ôn tập và thực hành OOP, SOLID và Database:** Thực hành, giải bài tập và trả lời các câu hỏi.                                                                                                                                                                                                                   |
| **3. Express & RESTful API Microservices**                                         | - RESTful API: Từ định nghĩa tới các tiêu chuẩn thường dùng.<br>- Chuẩn hoá cấu trúc dữ liệu Response & Error.<br>- Cấu trúc model độc lập các services.<br>- Nắm vững giao tiếp với database, 3rd-parties và các services khác.                                                                                    |
| **4. Kiến trúc Hexagonal**                                                         | - Kiến trúc Hexagonal (cùng Onion, Clean Architecture).<br>- Nắm vững Dependency Inversion/Injection để tăng tính linh hoạt cho services.<br>- Hiểu về Ports, Adapters, Repository thông qua ứng dụng nguyên lý DIP.<br>- Cấu trúc modular cho các services, chuyển đổi Monolithic $\leftrightarrow$ Microservices. |
| _Thực hành_                                                                        | - **Thực hành xây dựng API và kiến trúc Hexagonal:** Thực hành, giải bài tập và trả lời các câu hỏi.                                                                                                                                                                                                                |
| **5. Authentication & Authorization với JWT**                                      | - Authentication và Authorization service<br>- JWT cùng các best practice.<br>- Thiết lập Identity Service trong Microservices.                                                                                                                                                                                     |
| **6. Kiến trúc Microservices và ứng dụng**                                         | - Microservices: Từ lý thuyết đến thực tiễn.<br>- Các case study nên dùng hoặc không nên dùng.<br>- Chiến lược phát triển Microservices hiệu quả.<br>- Phương thức giao tiếp các services: Query/Aggregator và Command.<br>- Chuyển đổi Monolithic sang Microservices và ngược lại.                                 |
| **Bonus: Upload Service (Image & Medias) - AWS S3**                                | - Upload service cho toàn bộ hệ thống.<br>- Upload file lên local và remote storage (AWS S3).<br>- Sử dụng AWS Cloudfront để truy xuất các media đã upload.<br>- Best practice về upload flow và quản lý các media hiệu quả.                                                                                        |
| **7. Event-Driven Architecture, Queue & PubSub**                                   | - Event-Driven Architecture và Event-Storming, Event Sourcing.<br>- Event-Driven và các ứng dụng hiệu quả nhất.<br>- Nắm vững Queue vs PubSub và ứng dụng.<br>- Cơ bản về CQS, CQRS, SAGA, Outbox                                                                                                                   |
| _Thực hành_                                                                        | - **Thực hành Microservices, Event-Driven, Queue & Pubsub:** Thực hành, giải bài tập và trả lời các câu hỏi.                                                                                                                                                                                                        |
| **8. Caching và chiến lược cache**                                                 | - Caching với Redis<br>- Caching Strategy trong microservices.<br>- Các lỗi cần tránh trong Caching.<br>- Chia sẻ kinh nghiệm caching (thường gặp trong PV).                                                                                                                                                        |
| **9. Đóng gói và triển khai ứng dụng với Docker**                                  | - Docker và các thành phần trong Docker.<br>- Build image Docker với Dockerfile<br>- Triển khai ứng dụng với Docker.<br>- CI/CD cơ bản với Github Action.                                                                                                                                                           |
| **10. Cơ bản: Load Balancer, API Gateway, Service Mesh/Proxy, Service Discovery.** | - System design cơ bản: Load Balancer, API Gateway, Service Mesh/Proxy, Service Discovery.<br>- Các kiến trúc kiểu mẫu (Architecture Styles/Patterns) thường dùng.<br>- Chia sẻ các hình mẫu chịu tải cao thường gặp (Có thể gặp trong PV).                                                                         |
| _Thực hành_                                                                        | - **Thực hành và ôn tập caching, Docker và các hình mẫu hệ thống:** Thực hành, giải bài tập và trả lời các câu hỏi.                                                                                                                                                                                                 |
| **11. Tổng kết và các chia sẻ về JD, CV**                                          | - Tổng kết khóa học.<br>- Chia sẻ phân tích JD: hiểu rõ nhu cầu doanh nghiệp và chiến lược ứng tuyển.<br>- Chia sẻ review CV: chiến lược để có CV ấn tượng và tăng khả năng nhận offer.<br>- Chia sẻ về con đường tiếp theo.                                                                                        |
| **12. Bài tập project cuối khoá**                                                  | - Các học viên được giao bài tập để hoàn tất và nhận certification của 200Lab trong vòng 30 ngày.                                                                                                                                                                                                                   |

---

## Detailed Curriculum Structure / Chi Tiết Chương Trình

### 1. Typescript và OOP, SOLID

- Cơ bản syntax Typescript
- OOP và S.O.L.I.D trong Typescript.
- Các patterns quan trọng thường dùng trong kiến trúc backend.

### 2. Phân tích dự án & thiết kế database

- Phân tích dự án: từ UI $\rightarrow$ User Story $\rightarrow$ Features & APIs $\rightarrow$ Database.
- Tìm hiểu thông số tải, độ lớn dữ liệu, tốc độ phát sinh dữ liệu.
- Thiết kế hệ thống microservices (system design cơ bản).
- Các chia sẻ tăng tốc truy xuất dữ liệu.

> **📝 Ôn tập và thực hành OOP, SOLID và Database:** Thực hành, giải bài tập và trả lời các câu hỏi.

### 3. Express & RESTful API Microservices

- RESTful API: Từ định nghĩa tới các tiêu chuẩn thường dùng.
- Chuẩn hoá cấu trúc dữ liệu Response & Error.
- Cấu trúc model độc lập các services.
- Nắm vững giao tiếp với database, 3rd-parties và các services khác.

### 4. Kiến trúc Hexagonal

- Kiến trúc Hexagonal (cùng Onion, Clean Architecture).
- Nắm vững Dependency Inversion/Injection để tăng tính linh hoạt cho services.
- Hiểu về Ports, Adapters, Repository thông qua ứng dụng nguyên lý DIP.
- Cấu trúc modular cho các services, chuyển đổi Monolithic $\leftrightarrow$ Microservices.

> **📝 Thực hành xây dựng API và kiến trúc Hexagonal:** Thực hành, giải bài tập và trả lời các câu hỏi.

### 5. Authentication & Authorization với JWT

- Authentication và Authorization service
- JWT cùng các best practice.
- Thiết lập Identity Service trong Microservices.

### 6. Kiến trúc Microservices và ứng dụng

- Microservices: Từ lý thuyết đến thực tiễn.
- Các case study nên dùng hoặc không nên dùng.
- Chiến lược phát triển Microservices hiệu quả.
- Phương thức giao tiếp các services: Query/Aggregator và Command.
- Chuyển đổi Monolithic sang Microservices và ngược lại.

### 🎁 Bonus: Upload Service (Image & Medias) - AWS S3

- Upload service cho toàn bộ hệ thống.
- Upload file lên local và remote storage (AWS S3).
- Sử dụng AWS Cloudfront để truy xuất các media đã upload.
- Best practice về upload flow và quản lý các media hiệu quả.

### 7. Event-Driven Architecture, Queue & PubSub

- Event-Driven Architecture và Event-Storming, Event Sourcing.
- Event-Driven và các ứng dụng hiệu quả nhất.
- Nắm vững Queue vs PubSub và ứng dụng.
- Cơ bản về CQS, CQRS, SAGA, Outbox.

> **📝 Thực hành Microservices, Event-Driven, Queue & Pubsub:** Thực hành, giải bài tập và trả lời các câu hỏi.

### 8. Caching và chiến lược cache

- Caching với Redis.
- Caching Strategy trong microservices.
- Các lỗi cần tránh trong Caching.
- Chia sẻ kinh nghiệm caching (thường gặp trong PV).

### 9. Đóng gói và triển khai ứng dụng với Docker

- Docker và cách thành phần trong Docker.
- Build image Docker với Dockerfile.
- Triển khai ứng dụng với Docker.
- CI/CD cơ bản với Github Action.

### 10. Cơ bản: Load Balancer, API Gateway, Service Mesh/Proxy, Service Discovery.

- System design cơ bản: Load Balancer, API Gateway, Service Mesh/Proxy, Service Discovery.
- Các kiến trúc kiểu mẫu (Architecture Styles/Patterns) thường dùng.
- Chia sẻ các hình mẫu chịu tải cao thường gặp (Có thể gặp trong PV).

> **📝 Thực hành và ôn tập caching, Docker và các hình mẫu hệ thống:** Thực hành, giải bài tập và trả lời các câu hỏi.
