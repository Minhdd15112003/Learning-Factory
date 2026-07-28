---
status: Partial
tags: [ddd, tactical-design, java, review]
sr-due: 2026-07-29
sr-interval: 1
sr-ease: 230
review-count: 0
---

# Tactical Domain-Driven Design (C)

> Note do mình (Claudian) soạn. Mức độ: `Partial` (chưa Feynman pass vì kết thúc sớm).

## 1. Toàn bộ Lý thuyết cốt lõi
Tactical DDD cung cấp các công cụ lập trình chi tiết để mô hình hóa nghiệp vụ bên trong một Bounded Context. Bốn khái niệm nền tảng bao gồm:

*   **Entity (Thực thể):**
    *   Đặc trưng bởi một danh tính độc lập (ID duy nhất) không thay đổi theo thời gian.
    *   Hai Entity có các thuộc tính giống hệt nhau nhưng khác ID vẫn là hai đối tượng khác nhau.
    *   *Ví dụ:* `Order` (Đơn hàng) là một Entity với `orderId` làm khóa chính. Dù trạng thái, địa chỉ thay đổi, đơn hàng vẫn là chính nó.
*   **Value Object (Đối tượng giá trị):**
    *   Không có danh tính/ID riêng. Được định nghĩa hoàn toàn bởi giá trị của các thuộc tính bên trong nó.
    *   Nếu hai Value Object có cùng giá trị thuộc tính, chúng được coi là bằng nhau.
    *   Thường là bất biến (immutable - không thể sửa đổi sau khi tạo, muốn đổi phải tạo object mới).
    *   *Ví dụ:* `Money` (gồm `amount` và `currency`). Khoản tiền `100 USD` là như nhau dù ở ví ai hay đơn hàng nào.
*   **Aggregate (Tập hợp) & Aggregate Root:**
    *   Một cụm các Entity và Value Object liên quan chặt chẽ đi liền với nhau như một đơn vị giao dịch.
    *   **Aggregate Root (Gốc):** Entity duy nhất đứng đầu Aggregate. Mọi thay đổi dữ liệu bên trong Aggregate bắt buộc phải được điều khiển và thực hiện thông qua Aggregate Root này. Thế giới bên ngoài không được phép truy cập hoặc thay đổi trực tiếp các Entity con bên trong.
    *   *Ví dụ:* `Order` là Aggregate Root, chứa các `OrderItem` (Entity con). Bạn không thể trực tiếp sửa giá hay số lượng của `OrderItem` từ bên ngoài mà phải gọi hàm `Order.addItem()` hay `Order.updateQuantity()`.
*   **Repository:**
    *   Cơ chế mô phỏng một bộ sưu tập trong bộ nhớ để lưu trữ và truy xuất các Aggregates.
    *   Chỉ có Aggregate Root mới có Repository riêng (ví dụ: `OrderRepository`). Các Entity con (như `OrderItem`) không có Repository riêng mà được lưu gián tiếp qua Aggregate Root.

## 2. Tất cả Kiến thức & Insights
*   **Sự nhầm lẫn cần tránh:** Bounded Context không nằm trong Entity. Bounded Context là một ranh giới ngữ cảnh lớn ở mức kiến trúc hệ thống (như phân hệ Bán hàng, phân hệ Kho), bao quanh nhiều Entity khác nhau.
*   **Quy tắc bất biến (Invariants):** Aggregate Root tồn tại để đảm bảo các quy tắc nghiệp vụ luôn luôn đúng. Ví dụ, khi thêm `OrderItem`, `Order` phải tự tính toán lại tổng số tiền và kiểm tra xem tổng tiền có vượt hạn mức hay không. Nếu cho phép sửa trực tiếp `OrderItem`, quy tắc này sẽ dễ bị vi phạm.
*   **Repository là Port:** Interface của Repository nằm ở lớp Domain, định nghĩa hành vi lưu trữ dưới dạng Java thuần. Code kết nối DB thật (JPA/MySQL/MongoDB) nằm ở lớp ngoài (Infrastructure) và implement interface này.

## 3. Lý do tồn tại
*   **Chống Anemic Domain Model (Mô hình domain thiếu máu):** Ngăn chặn việc các class Entity chỉ chứa `@Getter/@Setter` thuần túy (chỉ là túi chứa dữ liệu) còn toàn bộ logic nghiệp vụ bị trôi dạt ra các Service bên ngoài.
*   **Bảo vệ tính nhất quán dữ liệu (Data Integrity):** Group các đối tượng liên quan vào một Aggregate giúp kiểm soát chặt chẽ luồng sửa đổi, tránh tình trạng bất nhất dữ liệu (ví dụ: cập nhật món hàng nhưng quên cập nhật tổng tiền đơn hàng).

## 4. Cách sử dụng & Use cases thực tế
### Khi nào nên dùng Value Object
*   Khi thuộc tính là một khái niệm tự thân cần đo lường, mô tả (Địa chỉ, Số tiền, Mã SKU, Số điện thoại).
*   Giúp gom cụm các trường liên quan (thay vì để `street`, `city`, `zipcode` rải rác trong `Order`, hãy tạo ra một VO `Address`).

### Trade-offs (Đánh đổi)
*   **Độ phức tạp khi query:** Sử dụng Aggregate Root đồng nghĩa với việc bạn không được phép load lẻ tẻ các Entity con từ DB mà phải load toàn bộ Aggregate. Điều này có thể gây ảnh hưởng hiệu năng nếu Aggregate quá lớn.
*   **Tránh thiết kế Aggregate quá khổng lồ:** Chỉ gộp các Entity thực sự cần đảm bảo tính nhất quán ngay lập tức (Immediate Consistency). Những mối quan hệ lỏng lẻo hơn nên liên kết qua ID và cập nhật bất đồng bộ.
