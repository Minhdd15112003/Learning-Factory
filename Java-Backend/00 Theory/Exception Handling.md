---
status: Understood
tags: [java, exception, review]
sr-due: 2026-07-25
sr-interval: 11
sr-ease: 270
---

# Exception Handling — Checked vs Unchecked (Java) (C)

> Note do mình (Claudian) soạn sau khi bạn tự suy ra ranh giới "kiểm soát được / không" và nối được chữ "ép" tới trạng thái bất nhất trong ngân hàng.

## Trục quyết định (một câu)
> Lỗi này có phải **điều kiện ngoài tầm kiểm soát của mình, chắc chắn có ngày xảy ra dù code đúng** không?

- Có (bên ngoài, không code cho biến mất được) → **checked** → compiler **ép** `catch` hoặc `throws`.
- Không, do **logic mình viết sai** (sửa code là hết) → **unchecked** (`RuntimeException`) → compiler **không ép**.

## Hai họ
| | **Checked** | **Unchecked** |
|---|---|---|
| Kế thừa | `Exception` (không phải `RuntimeException`) | `RuntimeException` |
| Compiler | **bắt buộc** `catch` hoặc `throws`, không thì **không build** | không bắt gì, nổ lúc runtime |
| Bản chất lỗi | ngoài tầm kiểm soát, lường trước được (mạng, file, IO) | bug trong logic của mình (null, chia 0, index sai) |
| Ví dụ | `IOException`, `SQLException` | `NullPointerException`, `ArithmeticException`, `IllegalArgumentException` |
| Cách phản ứng đúng | **có phương án**: thử lại / rollback / đối soát | **sửa code** cho nó đừng xảy ra (catch không phải là fix) |

## Vì sao compiler "ép" họ checked
Lỗi ngoài tầm kiểm soát **chắc chắn có ngày xảy ra** và bạn không viết code cho nó biến mất được. Ép `catch`/`throws` = **không cho phép nuốt lặng lẽ** một thất bại tất yếu.
- Ngân hàng: giữa lúc chuyển tiền mà mạng sang core-banking chết → nếu lỗi bị bỏ qua âm thầm, **tiền trừ bên mình mà chưa cộng bên kia** → trạng thái bất nhất. Ép xử lý buộc bạn có phương án (rollback / retry / ghi đối soát).
- Ngược lại, ép `catch` một `NullPointerException` là vô nghĩa: cách sửa đúng là **chữa code**, không phải bọc `try/catch` để giấu bug.

## Cú pháp
```java
class BankTransferException extends Exception { ... }   // checked (extends Exception)

void transfer() throws BankTransferException { ... }     // (1) đẩy lên trên
// hoặc
try { core.credit(...); }                                // (2) xử lý tại chỗ
catch (BankTransferException e) { /* rollback / log để đối soát */ }
```

## Hệ quả / nối tiếp
"Trừ rồi phải cộng, không thì hoàn lại" = hạt giống của [[transaction]] / tính **atomic** (all-or-nothing). Checked exception nhắc ở tầng ngôn ngữ; `@Transactional` ([[Spring]]) tự động rollback ở tầng framework. Liên quan: [[Polymorphism]] (mọi exception là một cây kế thừa — `catch (Exception e)` bắt được cả class con nhờ upcast).
