Đây đúng project bạn đang làm — Order, Inventory, Payment, Notification khớp với các bounded context đã chốt ở Stage 4. Mình sẽ làm cả bảng lẫn code skeleton theo đúng phong cách discovery bạn hay dùng: giải thích _tại sao_, không chỉ đưa đáp án.

## 1. Bảng phân loại giao tiếp

| Context A | Context B        | Cơ chế                                                  | Lý do                                                                                                                                                                                                                                                                 |
| --------- | ---------------- | ------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Order** | **Inventory**    | **Command (Sync)**                                      | Order cần biết **ngay lập tức** còn hàng hay không trước khi xác nhận đơn. Đây là _invariant_ bắt buộc — không thể tạo đơn hàng rồi "để sau" mới biết hết hàng. Nếu async, bạn sẽ rơi vào tình huống overselling.                                                     |
| **Order** | **Payment**      | **Command (Sync)** — nhưng có thể lai Event tùy mô hình | Thanh toán quyết định đơn có **được tạo hay không** (với mô hình thu tiền ngay). Order cần phản hồi đồng bộ: thành công/thất bại để trả kết quả cho user. Nếu dùng "Order trước, thanh toán sau" (giống Amazon), thì đây lại là Event.                                |
| **Order** | **Notification** | **Domain Event (Async)**                                | Notification **không ảnh hưởng đến việc đơn có được tạo thành công hay không**. Order không cần chờ email/SMS gửi xong. Đây là _side-effect_, không phải _invariant_ — tách rời hoàn toàn để tăng resilience (Notification service sập không làm sập luồng đặt hàng). |

**Nguyên tắc chốt lại:** hỏi câu này cho mỗi cặp — _"Nếu Context B không phản hồi kịp, Context A có buộc phải dừng lại không?"_

- Có → **Command/Sync**
- Không → **Event/Async**

Điểm đáng chú ý bạn nên tự đào sâu thêm: **Payment** là trường hợp mập mờ nhất trong 3 cái. Bạn nghĩ nếu hệ thống của bạn theo mô hình **Saga** (đặt hàng trước, giữ hàng, rồi mới charge thẻ bất đồng bộ) thì Order↔Payment sẽ chuyển thành Sync hay Async? Đây chính là chỗ phân biệt **Orchestration-based Saga** với **Choreography-based Saga** mà Stage 3 (Event-Driven) của bạn sẽ đụng tới.

## 2. Code skeleton**Điểm mấu chốt trong skeleton, để bạn tự soi lại:**

1. **`InventoryPort` / `PaymentPort`** — đặt tên theo _danh từ hành động_ (reserve, charge) và **trả về kết quả ngay** → dấu hiệu của Command.
2. **`OrderPlacedEvent`** — đặt tên theo **thì quá khứ** (`Placed`, không phải `PlaceOrder`) → quy ước chuẩn để phân biệt Event với Command chỉ bằng cách đọc tên.
3. **`releaseReservedStock`** trong nhánh Payment thất bại — đây chính là mầm mống **Saga Pattern / Compensating Transaction** mà bạn sẽ gặp lại ở Stage 3 (Event-Driven).

Câu hỏi mình để lại ở phần bảng (Order↔Payment: Sync hay Async nếu dùng Saga?) — bạn thử trả lời trước, mình sẽ phản biện thay vì giảng luôn đáp án.
