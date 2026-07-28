---
status: Partial
tags: [architecture, hexagonal, ports-adapters, java, review]
sr-due: 2026-07-29
sr-interval: 1
sr-ease: 230
review-count: 0
---

# Hexagonal Architecture (Ports & Adapters) (C)

> Note do mình (Claudian) soạn. Status: `Partial` — chưa Feynman pass vì buổi kết thúc sớm.

## 1. Toàn bộ Lý thuyết cốt lõi

### Hexagonal Architecture là gì?
Hexagonal Architecture (Kiến trúc Lục giác, còn gọi là Ports & Adapters) là **một cách triển khai cụ thể của triết lý DDD**. Nó chia code thành 3 tầng đồng tâm:

1. **Domain (Tầng lõi — trong cùng):**
   - Chứa **luật nghiệp vụ bất biến** — những quy tắc luôn đúng bất kể ai gọi, gọi từ đâu.
   - Chứa các **Port (interface)** — do lõi tự định nghĩa, nói rằng "tôi cần gì từ thế giới bên ngoài" (ví dụ: `OrderRepositoryPort`, `InventoryPort`).
   - Chứa các Entity, Value Object, Aggregate.
   - **Tuyệt đối không có** annotation Spring, JPA, hay bất kỳ thư viện/framework nào. Chỉ Java thuần.
   - *Ví dụ:* Class `Order` chứa method `calculateTotal()`, method `changeStatus()` kèm validate nghiệp vụ.

2. **Application (Tầng điều phối — giữa):**
   - Chứa các **Use Case** (kịch bản nghiệp vụ) — điều phối thứ tự gọi các bước.
   - **Không chứa luật nghiệp vụ.** Chỉ sắp xếp: bước 1 gọi gì, bước 2 gọi gì, bước 3 gọi gì.
   - *Ví dụ:* `PlaceOrderUseCase`: (1) gọi `inventoryPort.reserve()`, (2) gọi `orderRepository.save(order)`, (3) gọi `eventPublisher.publish(OrderPlacedEvent)`.

3. **Infrastructure (Tầng ngoài cùng):**
   - Chứa các **Adapter** — class cụ thể implement các Port interface từ tầng Domain.
   - Đây là nơi duy nhất chứa code kết nối công nghệ: Spring Boot annotations, JPA queries, Kafka producer, REST controller, SDK bên thứ ba...
   - *Ví dụ:* `OrderJpaAdapter implements OrderRepositoryPort` — bên trong dùng JPA/Hibernate để lưu vào MySQL.

### Hướng phụ thuộc (Dependency Rule)
Phụ thuộc chỉ đi từ **ngoài vào trong**, không bao giờ ngược lại:
```
Infrastructure → Application → Domain
```
- Domain không biết Application tồn tại.
- Application không biết Infrastructure tồn tại (chỉ gọi Port interface).
- Infrastructure biết cả hai và implement các interface.

### Ai quyết định lúc runtime Port dùng Adapter nào?
**IoC Container (Inversion of Control Container)** của Spring Boot. Spring đọc annotation (`@Component`, `@Service`, `@Repository`...) rồi tự động tạo object và tiêm (inject) Adapter vào đúng chỗ cần dùng. Vai trò này tương tự hàm `main()` trong Java thuần khi bạn tự tay `new` object và truyền vào nhau.

## 2. Tất cả Kiến thức & Insights
- **Sự khác biệt giữa Domain và Application:** Domain chứa luật ("đơn hàng phải có ít nhất 1 món"), Application chỉ sắp xếp luồng gọi ("giữ kho trước, lưu đơn sau, phát event cuối"). Bạn đã từng nhầm Application là Port — thực tế Port là interface nằm trong Domain, Application là tầng gọi (sử dụng) các Port đó.
- **Lợi ích thực tế:** Khi đổi từ MySQL sang MongoDB, chỉ cần viết thêm `OrderMongoAdapter implements OrderRepositoryPort` ở tầng Infrastructure. Domain và Application không bị mở ra để sửa.
- **Liên hệ Giai đoạn 1:** Pattern Adapter và Strategy bạn đã học chính là nền tảng của kiến trúc này — Adapter chuyển đổi interface, Strategy cho phép đổi implementation lúc runtime.

## 3. Lý do tồn tại
| Nỗi đau không có Hexagonal | Hexagonal giải quyết |
|---|---|
| Logic nghiệp vụ trộn lẫn annotation Spring, câu SQL JPA → đổi DB phải sửa cả logic | Logic nằm trong Domain thuần Java, đổi DB chỉ viết Adapter mới |
| Unit test phải dựng cả Spring context + DB → test chậm, phức tạp | Domain thuần Java → test nhanh, không cần framework |
| Sửa tầng giao diện (REST → GraphQL) kéo theo sửa logic nghiệp vụ | Tầng Infrastructure độc lập, thay REST controller không ảnh hưởng logic |

## 4. Cách sử dụng & Use cases thực tế
### Khi nào NÊN dùng
- Dự án có nghiệp vụ phức tạp, nhiều luật, thay đổi thường xuyên.
- Dự án cần chạy lâu dài (> 2 năm), có khả năng đổi DB/framework.
- Nhiều team phát triển song song.

### Khi nào KHÔNG NÊN dùng
- CRUD đơn giản, ít logic nghiệp vụ (ví dụ: app todo list).
- Prototype / MVP cần ship nhanh — overhead tạo Port/Adapter tốn thời gian mà chưa cần thiết.

### Trade-offs
- **Nhiều file hơn:** Mỗi chức năng cần ít nhất 3 file (Port interface + Use Case + Adapter) thay vì 1 Service.
- **Đường đi dài hơn:** Request phải đi qua Controller → UseCase → Domain → Port → Adapter → DB, thay vì Controller → Service → DB.
- **Đáng giá khi:** Hệ thống sống lâu và nghiệp vụ phức tạp — chi phí ban đầu được bù lại bằng khả năng bảo trì và mở rộng.
