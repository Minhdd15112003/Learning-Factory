---
status: Understood
tags: [ddd, strategic-design, bounded-context, java, review]
sr-due: 2026-07-29
sr-interval: 1
sr-ease: 230
review-count: 1
---

# Strategic Domain-Driven Design (DDD) (C)

> Note do mình (Claudian) soạn. Status `Partial` vì buổi học chưa kết thúc bằng Feynman gate đầy đủ cho toàn bộ note này — chỉ một số cơ chế được giải thích bằng lời của bạn.

---

## 1. Toàn bộ Lý thuyết cốt lõi

### 1.1 DDD là gì và tại sao nó tồn tại?

**Domain-Driven Design (DDD)** là một **triết lý thiết kế phần mềm**, không phải framework hay kiến trúc cụ thể. Cốt lõi của nó là:

> **Code phải phản ánh đúng cách nghiệp vụ thực tế vận hành — không phải cách database lưu trữ, không phải cách framework hoạt động.**

Không có DDD, hệ thống vẫn chạy được. Nhưng khi hệ thống lớn lên:

- Một `OrderService` 2000 dòng chứa validate, tính tiền, check kho, gọi thanh toán, gửi email, lưu DB — tất cả trong một file.
- Sếp nói "thêm luật: VIP giảm phí ship" → bạn phải đào 2000 dòng, sửa xong có thể làm hỏng luồng gửi email.

**Có DDD:** Luật "VIP giảm phí ship" nằm đúng trong `class Order`, method `calculateShippingFee()`. Bạn mở đúng 1 file, sửa đúng 1 method. Code email, code DB ở chỗ khác hoàn toàn.

**DDD có giá trị khi:**
- Nhiều dev/team cùng làm — cần ranh giới để không đạp lên code nhau.
- Nghiệp vụ phức tạp, nhiều luật, thay đổi liên tục.
- Hệ thống sống lâu (3-5 năm), đổi DB/framework là việc có thể xảy ra.

**DDD là overhead khi:** dự án nhỏ (< 10 class), 1 người làm, nghiệp vụ đơn giản.

---

### 1.2 Bounded Context — Khái niệm nền móng của Strategic DDD

> **ĐÂY LÀ KHÁI NIỆM PHẢI HIỂU ĐẦU TIÊN TRƯỚC KHI HỌC BẤT KỲ THỨ GÌ KHÁC TRONG DDD.**

**Bounded Context (Ranh giới ngữ cảnh)** là ranh giới phân chia một miền nghiệp vụ lớn thành các **phân vùng độc lập**. Trong mỗi phân vùng:
- Các từ ngữ chỉ có **một nghĩa duy nhất** — không bị lẫn lộn với nghĩa ở phân vùng khác.
- Mỗi model/class chỉ chứa **đúng những thuộc tính phục vụ ngữ cảnh đó**.

**Ví dụ cụ thể — Từ "Sản phẩm" (Product) trong `commerce-fulfillment-system`:**

| Bounded Context | Từ "Product" có nghĩa là... | Thuộc tính cần |
|---|---|---|
| **Catalog** (Danh mục) | Một listing hấp dẫn khách hàng | Tên, hình ảnh, mô tả chi tiết, giá, reviews |
| **Inventory** (Kho hàng) | Một vật thể vật lý cần quản lý | Mã SKU, vị trí kệ, số lượng tồn |
| **Order** (Đặt hàng) | Một mục trong giỏ hàng | ProductId, tên hiển thị, giá tại thời điểm đặt |

**Nếu không có Bounded Context:**
- Bạn tạo 1 class `Product` khổng lồ với 50+ thuộc tính gộp từ cả 3 ngữ cảnh.
- Phòng Kho sửa logic tính cân nặng → code Bán hàng có thể bị ảnh hưởng.

**Tổng kết:** Bounded Context = "Phòng ban" của hệ thống. Mỗi phòng ban có ngôn ngữ, quy tắc và model riêng.

---

### 1.3 Các Bounded Context trong `commerce-fulfillment-system`

Hệ thống có **6 Bounded Contexts**:

| Context | Trách nhiệm chính |
|---|---|
| **Catalog** | Quản lý danh mục sản phẩm, thông tin hiển thị, giá bán |
| **Order** | Tạo và quản lý vòng đời đơn hàng |
| **Inventory** | Quản lý tồn kho, giữ chỗ (reservation), nhả chỗ |
| **Payment** | Xử lý giao dịch thanh toán qua cổng thanh toán |
| **Fulfillment** | Đóng gói và giao hàng vật lý |
| **Notification** | Gửi email, SMS, push notification |

**Mỗi Bounded Context sau này sẽ là một Microservice riêng biệt ở Giai đoạn 4.**

---

### 1.4 Ubiquitous Language (Ngôn ngữ thống nhất)

Bộ thuật ngữ chung được **cả kỹ sư và chuyên gia nghiệp vụ** thống nhất dùng trong code, tài liệu và giao tiếp hàng ngày.

Ví dụ: Đừng dùng "insert vào bảng orders" — dùng "Place an Order". Đừng dùng "update stock" — dùng "Reserve Inventory".

---

### 1.5 Cơ chế giao tiếp giữa các Bounded Context

Khi user bấm "Đặt hàng", các Context phải giao tiếp với nhau. Có 2 cơ chế:

#### Command (Đồng bộ — Synchronous)
- **Là gì:** Hành động bắt buộc phải thành công/thất bại ngay lập tức để quyết định luồng tiếp theo.
- **Dùng khi:** Kết quả ảnh hưởng trực tiếp đến phản hồi trả về cho user.
- **Trong luồng đặt hàng:** `Order → Inventory` (tạo đơn + giữ kho phải xong trước khi trả về màn hình xác nhận).

#### Domain Event (Bất đồng bộ — Asynchronous)
- **Là gì:** Sự kiện mô tả điều đã xảy ra trong quá khứ (ví dụ: `OrderPlacedEvent` — "Đơn hàng đã được đặt"). Các Context khác **lắng nghe và xử lý độc lập**.
- **Dùng khi:** Kết quả không cần thiết ngay lập tức cho user; lỗi không nên làm gián đoạn luồng chính.
- **Trong luồng đặt hàng:** `Payment`, `Notification`, `Fulfillment` — user không cần ngồi chờ email bay vào hộp thư mới thấy màn hình đặt hàng thành công.

**Luồng đặt hàng tối ưu trong `commerce-fulfillment-system`:**
```
[User bấm "Đặt hàng"]
        │
        ▼ (Sync — Command)
[Order Context] → Tạo đơn hàng → trạng thái: PENDING_PAYMENT
        │
        ▼ (Sync — Command)
[Inventory Context] → Giữ kho (Reserve) → đảm bảo hàng không bị bán cho người khác
        │
        ▼ Trả về cho user: "Đơn #123 đã tạo, vui lòng thanh toán"
        │
        ▼ (Async — Domain Event: OrderPlacedEvent)
[Payment Context] ──────► User được chuyển sang trang thanh toán (VNPay/MoMo)
[Notification Context] ──► Gửi email xác nhận đơn hàng
[Fulfillment Context] ───► Chuẩn bị phiếu giao hàng khi thanh toán thành công
```

---

### 1.6 Saga Pattern — Nguyên lý lõi (chi tiết ở Giai đoạn 4)

**Vấn đề:** Nếu user tạo đơn, giữ kho thành công, nhưng 15 phút trôi qua không thanh toán → kho bị "kẹt" trong reservation ảo mãi mãi, khách khác không mua được dù hàng thật vẫn còn.

**Saga Pattern giải quyết điều này bằng 2 thành phần:**
1. **Chuỗi các bước độc lập (async):** Mỗi bước là một giao dịch riêng, khi xong phát ra Event kích hoạt bước tiếp theo.
2. **Compensating Transaction (Giao dịch bù):** Mỗi bước đều có hành động hoàn tác nếu bước sau thất bại.

| Bước | Hành động | Compensating Transaction |
|---|---|---|
| Tạo đơn hàng | Order tạo với trạng thái PENDING | Chuyển trạng thái sang CANCELLED |
| Giữ kho | Inventory giảm stock khả dụng | Inventory nhả lại stock (Release Reservation) |
| Thu tiền | Payment charge thẻ | Payment hoàn tiền (Refund) |

**Chưa có giao dịch bù → chưa phải Saga, chỉ là async thông thường.**

---

### 1.7 Context Map (Sơ đồ ngữ cảnh)

Bản đồ thể hiện mối quan hệ giữa các Bounded Context. Các kiểu quan hệ phổ biến:

| Kiểu quan hệ | Ý nghĩa |
|---|---|
| **Customer–Supplier** | Upstream cung cấp dữ liệu; thay đổi ở Upstream ảnh hưởng Downstream |
| **Shared Kernel** | Hai Context dùng chung một phần model |
| **Anticorruption Layer (ACL)** | Lớp dịch chuyển bảo vệ Context phía trong khỏi bị "ô nhiễm" bởi model phức tạp từ ngoài |

---

## 2. Tất cả Kiến thức & Insights từ buổi học

- **Inventory Reservation phải chạy sync với Order creation** — vì nếu async, 11 user có thể cùng thấy còn 10 sản phẩm, nhưng khi thanh toán 1 người sẽ bị lỗi (over-selling). Giữ kho ngay lập tức khi tạo đơn loại bỏ race condition này.
- **Payment KHÔNG chạy sync trong nút "Đặt hàng"** — giống Shopee/Tiki: hệ thống tạo đơn → trả về mã QR → user có 15 phút thanh toán → VNPay/MoMo gọi ngược lại callback khi thành công. Request ban đầu đã kết thúc từ lâu.
- **Notification và Fulfillment luôn async** — lỗi gửi email không được làm sập giao dịch mua hàng; email retry sau là được.
- **Domain Core không được biết đến Spring/JPA/DB** — luật nghiệp vụ phải độc lập với công nghệ để khi đổi DB chỉ viết Adapter mới, không sửa lõi.

---

## 3. Lý do tồn tại (Vấn đề DDD giải quyết)

| Nỗi đau | DDD giải quyết như thế nào |
|---|---|
| `OrderService` 2000 dòng, sửa tính năng nhỏ làm hỏng tính năng khác | Mỗi luật nghiệp vụ nằm đúng trong Domain class của nó; tầng Application chỉ điều phối |
| `Product` class có 50 thuộc tính gộp từ Kho + Bán hàng + Giao hàng | Bounded Context tách thành 3 class riêng, mỗi class chỉ chứa đúng thuộc tính của ngữ cảnh đó |
| Over-selling: 11 người mua 10 sản phẩm cuối cùng | Inventory Reservation đồng bộ ngay khi tạo đơn |
| Cổng thanh toán chậm làm user timeout và reload | Payment tách ra async, user không cần chờ |
| Notification chết làm sập toàn bộ giao dịch | Notification async với retry — lỗi tự phục hồi, không ảnh hưởng luồng chính |

---

## 4. Cách sử dụng & Use cases thực tế

### Khi nào NÊN dùng DDD

- Nhiều team/dev cùng làm, cần ranh giới rõ ràng.
- Nghiệp vụ phức tạp, nhiều luật, thay đổi liên tục (chính sách giảm giá, hoàn tiền, VIP...).
- Hệ thống dài hơi (3-5 năm+), có khả năng đổi DB/framework.
- Định hướng Microservices — mỗi Bounded Context sẽ trở thành 1 service độc lập.

### Khi nào KHÔNG NÊN dùng DDD

- Dự án nhỏ (< 10 class), 1 người làm, nghiệp vụ đơn giản.
- Prototype/MVP cần delivery nhanh — overhead của DDD là không cần thiết.

### Trade-offs

| Lợi ích | Chi phí |
|---|---|
| Code dễ sửa, dễ test, dễ scale | Nhiều file, nhiều interface, phức tạp hơn ban đầu |
| Thay DB/framework không ảnh hưởng lõi | Phải quản lý Event Broker (Kafka, RabbitMQ) |
| Lỗi 1 service không kéo sập service khác | Phải xử lý Eventual Consistency — trạng thái hệ thống có thể tạm thời không nhất quán |
| Dễ tách thành Microservices | Phải implement Compensating Transaction cho mỗi bước trong Saga |
