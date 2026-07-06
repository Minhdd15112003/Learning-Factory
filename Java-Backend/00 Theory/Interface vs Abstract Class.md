---
status: Understood
tags: [java, oop, review]
sr-due: 2026-07-07
sr-interval: 1
sr-ease: 250
---

# Interface vs Abstract Class (Java) (C)

> Note do mình (Claudian) soạn sau khi bạn giải thích được cơ chế bằng lời của mình.

## Trục quyết định (chỉ một câu)
> Các kiểu này có cần **chia sẻ CODE và STATE thật** (field + thân hàm viết sẵn) không, hay chỉ cần **hứa là "làm được" một khả năng**?

- Chia sẻ code/state thật → **`abstract class`** (và tốn suất kế thừa duy nhất).
- Chỉ hứa một khả năng (có thể gắn nhiều khả năng lên một class) → **`interface`**.

## Mỗi cái cho gì
| | `interface` | `abstract class` |
|---|---|---|
| Field instance (state) | ❌ không | ✅ có |
| Thân hàm dùng chung (code) | ❌ chỉ chữ ký¹ | ✅ viết sẵn, class con tái dùng |
| Số lượng gắn được | `implements` **nhiều** | `extends` **đúng 1** |
| Ý nghĩa | "kiểu này *làm được* gì" (hợp đồng) | "kiểu này *là* gì + chia sẻ code" |

¹ Java 8+ có `default method` (có thân), nhưng **vẫn không có field instance** → vẫn không dùng cho state chung.

## Cơ chế cốt lõi (vì sao interface gắn được nhiều)
[[interface]] chỉ là **thỏa thuận method**, không chứa logic/state → không có gì để đụng nhau → gắn bao nhiêu cũng được.
[[Kế thừa]] (`extends`) kéo theo **toàn bộ** state + code của class cha → nhiều cha là đụng độ state/logic (*diamond problem*) → Java cấm `extends` nhiều class.

## Hệ quả: "Code theo interface"
Vì interface linh hoạt (gắn nhiều khả năng, dễ thay thế, dễ mock khi test) → nên phụ thuộc vào **interface** thay vì class cụ thể. Đây là gốc của [[Dependency Injection]] ở [[Spring]] / NestJS: inject một `PaymentService` (interface), đổi implementation hoặc mock lúc test mà không sửa code gọi.

## Ví dụ
```java
interface Payment { void process(); }                 // hợp đồng

abstract class AbstractPayment implements Payment {    // state + code chung
    protected String transactionId;
    protected void validateAmount(double amount) { /* viết 1 lần */ }
}

// đã dùng suất extends cho AbstractPayment, nhưng vẫn gắn thêm khả năng qua interface
class CreditCardPayment extends AbstractPayment implements Auditable, Serializable {
    public void process() { /* ... */ }
}
```
