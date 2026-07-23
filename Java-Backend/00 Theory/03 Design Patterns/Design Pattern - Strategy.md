---
status: Understood
tags: [java, design-pattern, solid, review]
sr-due: 2026-07-25
sr-interval: 2
sr-ease: 250
---

# Design Pattern - Strategy

[[Strategy]] là mẫu thiết kế thuộc nhóm Behavior (hành vi), cho phép định nghĩa một tập hợp các thuật toán, đóng gói từng thuật toán lại thành các class riêng biệt và giúp chúng có thể thay thế lẫn nhau lúc runtime.

## 1. Vấn đề giải quyết
- Tránh việc sử dụng các khối `if-else` hoặc `switch-case` quá lớn khi một hành vi có nhiều biến thể thực thi.
- Giúp code tuân thủ nguyên lý **Open/Closed (SOLID)**: Khi muốn thêm một thuật toán mới, ta chỉ cần tạo thêm class mới mà không cần sửa đổi code của các class cũ (Context).
- Tách biệt logic xử lý cụ thể ra khỏi lớp sử dụng (Context), giúp dễ dàng unit test từng thuật toán độc lập.

## 2. Các thành phần cốt lõi
1. **Strategy Interface**: Định nghĩa phương thức chung cho tất cả các thuật toán (ví dụ: `FeeStrategy` với method `calculateFee()`).
2. **Concrete Strategies**: Các class cụ thể thực thi interface trên (ví dụ: `InternalFeeStrategy`, `InterbankFeeStrategy`).
3. **Context**: Lớp sử dụng strategy (ví dụ: `TransactionProcessor`). Nó giữ một tham chiếu đến interface Strategy và cung cấp method (Setter) để thay đổi strategy lúc runtime.

## 3. Tại sao thỏa mãn Open/Closed?
- **Mở (Open)**: Ta có thể thêm bao nhiêu class Strategy mới tùy ý.
- **Đóng (Closed)**: Class Context (`TransactionProcessor`) không bao giờ phải thay đổi dù hệ thống có thêm 10 hay 100 loại phí mới, vì nó chỉ giao tiếp qua Interface.

## 4. So sánh với Factory
- **Factory**: Tập trung vào việc **khởi tạo** đối tượng (Create object).
- **Strategy**: Tập trung vào việc **thực thi** hành vi (Execute behavior).
- Trong thực tế, người ta thường dùng Factory để **tạo ra** đúng Strategy cần dùng, sau đó nạp Strategy đó vào Context.
