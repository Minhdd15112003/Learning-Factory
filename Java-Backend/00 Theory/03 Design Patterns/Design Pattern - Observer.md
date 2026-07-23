---
status: Understood
tags: [java, design-pattern, review]
sr-due: 2026-07-23
sr-interval: 1
sr-ease: 250
---

# Design Pattern - Observer

[[Observer]] (hay Publish-Subscribe) là mẫu thiết kế thuộc nhóm Behavior (hành vi), cho phép một đối tượng (Subject/Publisher) thông báo tự động đến tất cả các đối tượng phụ thuộc (Observer/Subscriber) khi có sự kiện xảy ra, mà không cần biết cụ thể chúng là ai.

## 1. Định nghĩa & Lý do tồn tại
- **Vấn đề:** Khi một hành động xảy ra (ví dụ: thanh toán thành công), cần thông báo cho nhiều thành phần khác (SMS, Email, App, Voucher...). Nếu Subject tự tay giữ từng đối tượng cụ thể và gọi chúng, mỗi khi thêm kênh mới phải sửa lại Subject — vi phạm Open/Closed và gây tight coupling.
- **Giải pháp:** Subject chỉ giữ một danh sách interface (`List<Observer>`), không biết và không cần biết bên trong là ai. Khi sự kiện xảy ra, duyệt list và gọi method chung trên từng phần tử.

## 2. Cơ chế hoạt động
1. **Observer Interface**: Định nghĩa method chung mà tất cả subscriber phải có (ví dụ: `void update(String transactionId)`).
2. **Concrete Observers**: Các class cụ thể implement interface (`SmsNotifier`, `EmailNotifier`, `AppNotifier`). Mỗi class tự xử lý logic riêng trong `update()`.
3. **Subject (Publisher)**: Class phát sự kiện (ví dụ: `PaymentService`). Nó có:
   - Một `List<Observer>` để giữ danh sách subscriber.
   - Method `subscribe(Observer o)` để đăng ký observer mới vào list.
   - Method `notifyAll(...)` duyệt vòng lặp qua list, gọi `update()` trên từng phần tử.
4. **Luồng hoạt động:** Sự kiện xảy ra → Subject gọi `notifyAll()` → vòng lặp duyệt list → gọi `update()` trên mỗi Observer → mỗi Observer tự xử lý.

## 3. Cách sử dụng & Khi nào dùng
- **Khi nào dùng:** Khi một hành động cần kích hoạt nhiều phản ứng khác nhau, và số lượng phản ứng có thể tăng theo thời gian.
- **Ví dụ thực tế:** Hệ thống thanh toán ngân hàng — thanh toán xong cần gửi SMS, Email, push notification, ghi log, cấp voucher... mỗi kênh là một Observer độc lập.

```java
// Đăng ký
paymentService.subscribe(new SmsNotifier());
paymentService.subscribe(new EmailNotifier());

// Khi thanh toán xong → tự động thông báo tất cả
paymentService.completePayment("TXN-9999");
```

## 4. Kiến thức từ buổi học
- **Cạm bẫy `notifyAll()` trong Java:** Mọi class Java đều kế thừa `Object`, mà `Object` có sẵn method `notifyAll()` (dùng cho đa luồng/Monitor). Nếu đặt tên method trùng mà khác signature (có/không có tham số), Java có thể gọi nhầm method của `Object` → gây `IllegalMonitorStateException`. Cần cẩn thận đặt tên (ví dụ: `notifySubscribers()`) hoặc đảm bảo đúng signature.
- **So sánh với Strategy:** Strategy thay đổi **một** hành vi của Context. Observer thông báo cho **nhiều** đối tượng cùng lúc. Cả hai đều dùng interface để tách coupling.

## 5. So sánh / Liên quan
| Đặc điểm | [[Design Pattern - Strategy\|Strategy]] | Observer |
|---|---|---|
| Quan hệ | Context giữ **1** strategy | Subject giữ **N** observers |
| Mục đích | Thay đổi thuật toán/hành vi | Thông báo sự kiện cho nhiều bên |
| Thay đổi lúc runtime | Đổi strategy qua setter | Thêm/bớt observer qua subscribe/unsubscribe |

Liên quan: [[Concurrency - Thread và Shared Memory]] (notifyAll của Object) · Kafka/RabbitMQ (Observer ở quy mô hệ thống phân tán — học ở Giai đoạn 8)
