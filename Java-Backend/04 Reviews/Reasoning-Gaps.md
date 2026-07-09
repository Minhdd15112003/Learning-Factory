# Reasoning Gaps Log

> Nơi ghi lại các hiểu lầm và lỗ hổng suy luận phát hiện trong quá trình học.

## Active Gaps
### 2026-07-07 — Trả lời ở tầng "mô tả/dán nhãn" thay vì "cơ chế nhân quả"
- **Hiện tượng:** câu trả lời đầu tiên hay *gọi tên* thứ đang xảy ra rồi dừng, chưa chỉ ra *vì sao*. Đã lặp 2 lần: "interface khi nhiều method" (buổi 07-06) và "send là dynamic method nên tới runtime mới biết" (buổi 07-07). Cả hai đều đúng mô tả nhưng vòng tròn, chưa phải nhân.
- **Cần làm ở review:** khi nghe một câu dán nhãn, đẩy một nhịp — "Đó là cái gì. Còn *vì sao*?" — trước khi chấm. Không nhận nhãn thay cho cơ chế.

### 2026-07-09 — Nhầm hướng: LinkedList nhanh hơn ArrayList khi truy cập theo index
- **Hiện tượng:** nghĩ LinkedList nhanh hơn vì "đã biết index tiếp theo là gì rồi" — lẫn lộn con trỏ `next` (chỉ trỏ sang **node kế tiếp**, không phải index bất kỳ) với random access.
- **Đã sửa trong buổi:** khi được hỏi "con trỏ next trỏ đến node nào?", tự nhận ra next chỉ trỏ sang node 1, không nhảy thẳng đến index 500,000. ArrayList mới truy cập thẳng được nhờ bộ nhớ liên tục.
- **Liên quan:** gap "mô tả/dán nhãn" vẫn active — Feynman hôm nay giải thích được một phía (LinkedList) nhưng thiếu phía ArrayList (dịch chuyển phần tử). Cần soi ở review 07-10.

## Resolved Gaps
### 2026-07-07 — Rollback sai chiều (debit thay vì credit)
- **Slip:** để "hoàn tiền" cho tài khoản đã bị trừ, ban đầu gọi `debit` (trừ thêm) → tài khoản mất gấp đôi.
- **Đã sửa:** tự nhận ra "phải cộng lại". Nguyên tắc: rollback = **đảo ngược đúng thao tác đã làm** (đã `debit` thì `credit` lại đúng số đó). Nhẹ, sửa nhanh — ghi lại vì là lỗi domain-reasoning trong ngân hàng, không phải lỗi cú pháp.

### 2026-07-07 — "Cast nhét method vào interface"
- **Hiểu lầm:** nghĩ `(EmailNotification) n` sửa/bổ sung method cho `interface INotify`, và tưởng static↔dynamic type là một công tắc gạt qua lại.
- **Đã sửa:** cast **không đụng** interface, **không đổi** vật thể; nó chỉ đổi **static type mà compiler nhìn thấy cho đúng một biểu thức đó**. Bạn tự chốt: sang dòng khác không cast thì `n` lại bị soi theo `INotify`. Hai kiểu (static/dynamic) luôn tồn tại song song, không phải toggle.

### 2026-07-06 — Đảo trục "interface vs abstract class"
- **Hiểu lầm ban đầu:** chọn giữa interface / abstract class dựa trên *số lượng method* ("interface khi nhiều method", "abstract khi cần quy ước chung"). Cả hai đều ép class phải có method, nên đó không phải trục quyết định.
- **Đã sửa:** trục đúng = **có cần chia sẻ code/state thật không** (→ abstract, tốn suất kế thừa) hay **chỉ hứa một khả năng** (→ interface, gắn được nhiều). Bạn tự chốt lại đúng ở cuối buổi.
- **Cần theo dõi:** cách nói "nhiều method" từng tái xuất một lần — soi lại ở review kế tiếp xem trục đã dính chắc chưa.
