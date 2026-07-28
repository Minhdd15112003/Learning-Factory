---
status: Understood
tags: [ddd, strategic-design, java, review]
sr-due: 2026-07-28
sr-interval: 1
sr-ease: 250
review-count: 0
---

# Strategic Domain-Driven Design (DDD) (C)

> Note do mình (Claudian) soạn sau khi bạn giải thích được cơ chế giữ chỗ tồn kho (Inventory Reservation) và ranh giới giao tiếp giữa các Bounded Context.

## 1. Toàn bộ Lý thuyết cốt lõi
Strategic DDD là tập hợp các nguyên tắc thiết kế ở cấp độ hệ thống (vĩ mô) nhằm chia nhỏ một nghiệp vụ lớn, phức tạp thành các phần nhỏ hơn có ranh giới rõ ràng. Các khái niệm cốt lõi bao gồm:

*   **Domain (Miền nghiệp vụ):** Toàn bộ bài toán thực tế mà doanh nghiệp đang giải quyết (ở đây là E-commerce Order & Fulfillment).
*   **Bounded Context (Ranh giới ngữ cảnh):** Ranh giới phân chia một miền nghiệp vụ lớn thành các phân vùng nhỏ hơn. Trong mỗi ranh giới, các mô hình dữ liệu (Domain Models) và ngôn ngữ thống nhất (Ubiquitous Language) được định nghĩa rõ ràng và không bị lẫn lộn nghĩa với ranh giới khác.
    *   *Ví dụ:* Cùng là khái niệm "Sản phẩm", nhưng trong `Catalog Context` nó mang thuộc tính mô tả, hình ảnh, giá bán; còn trong `Inventory Context` nó chỉ cần quan tâm đến mã SKU và số lượng tồn kho.
*   **Ubiquitous Language (Ngôn ngữ thống nhất):** Bộ thuật ngữ chung được cả kỹ sư phát triển phần mềm và chuyên gia nghiệp vụ (Domain Experts) thống nhất sử dụng trong code, tài liệu và giao tiếp hàng ngày.
*   **Context Map (Sơ đồ ngữ cảnh):** Bản đồ thể hiện mối quan hệ và cách thức giao tiếp giữa các Bounded Context. Các mối quan hệ phổ biến:
    *   *Shared Kernel:* Hai ngữ cảnh dùng chung một phần mô hình.
    *   *Customer-Supplier / Upstream-Downstream:* Ngữ cảnh phía trên (Upstream) cung cấp dữ liệu, sự thay đổi của nó sẽ ảnh hưởng trực tiếp đến ngữ cảnh phía dưới (Downstream).
    *   *Anticorruption Layer (ACL):* Lớp dịch chuyển/bảo vệ giúp ngữ cảnh phía dưới không bị ô nhiễm bởi mô hình dữ liệu phức tạp từ ngữ cảnh phía trên.

## 2. Tất cả Kiến thức & Insights
Trong hệ thống thương mại điện tử (`commerce-fulfillment-system`), luồng đặt hàng được tối ưu hóa thông qua ranh giới giao tiếp:

*   **Command (Đồng bộ - Synchronous):** `Order` -> `Inventory`
    *   Khi người dùng bấm "Đặt hàng", việc tạo đơn hàng và giữ chỗ tồn kho (`Inventory Reservation`) phải diễn ra đồng bộ. Điều này đảm bảo tính nhất quán ngay lập tức (Immediate Consistency). Hệ thống chỉ phản hồi thành công khi đã chắc chắn giữ được hàng trong kho.
*   **Domain Event (Bất đồng bộ - Asynchronous):** `Payment`, `Notification`, `Fulfillment`
    *   Các hành động như thanh toán, gửi email/SMS xác nhận, và tạo phiếu giao hàng được xử lý bất đồng bộ thông qua các sự kiện (ví dụ: `OrderPlacedEvent`).
    *   Tách biệt này giúp giải phóng luồng HTTP chính, giảm độ trễ tối đa cho nút "Đặt hàng", tăng khả năng chịu tải và cách ly lỗi (nếu Service Email chết, giao dịch mua hàng vẫn hoàn tất và Email sẽ được gửi bù khi Service hồi phục).

## 3. Lý do tồn tại
*   **Giải quyết nỗi đau của Monolith Spaghetti:** Tránh việc một cơ sở dữ liệu duy nhất hoặc một class Model (như `Product`) chứa hàng trăm thuộc tính dùng chung cho tất cả các phòng ban, dẫn đến việc sửa đổi một tính năng nhỏ ở module này làm sập module khác.
*   **Tránh Over-selling (Bán quá đà):** Bằng cách giữ kho (`Reservation`) ngay khi tạo đơn hàng, hệ thống loại bỏ rủi ro 11 khách hàng thanh toán thành công cho một món hàng chỉ còn 10 sản phẩm trong kho vật lý.
*   **Giảm tải và chống nghẽn hệ thống:** Loại bỏ việc tích hợp các bên thứ ba chậm chạp (như cổng thanh toán) ra khỏi tiến trình chính của giao dịch tạo đơn.

## 4. Cách sử dụng & Use cases thực tế
### Khi nào nên sử dụng
*   Hệ thống có nghiệp vụ phức tạp, nhiều phòng ban tham gia (ví dụ: Tài chính, Kho vận, CSKH).
*   Khi cần phát triển hệ thống theo hướng Microservices hoặc Modular Monolith để chia việc cho các team độc lập.

### Trade-offs (Đánh đổi)
*   **Sự phức tạp của Eventual Consistency (Nhất quán cuối cùng):** Vì thanh toán và giao hàng chạy bất đồng bộ, hệ thống phải xử lý các tình huống phức tạp như: Khách hàng tạo đơn giữ kho thành công nhưng không thanh toán trong 15 phút -> Phải có worker tự động thu hồi/nhả tồn kho (Release Reservation).
*   **Chi phí tích hợp lớn:** Phải quản lý các Event Broker (Kafka, RabbitMQ) và giải quyết bài toán giao tin cậy (Transactional Outbox Pattern).
