---
status: Understood
tags: [java, oop, review]
sr-due: 2026-07-28
sr-interval: 14
sr-ease: 290
---

# Polymorphism — Dynamic Dispatch (Java) (C)

> Note do mình (Claudian) soạn sau khi bạn tự sửa "compile → runtime" và giải thích được vai trò của compiler bằng lời của mình.

## Câu hỏi cốt lõi
> Khi một biến khai báo kiểu cha/interface nhưng giữ vật thể con, `x.method()` chạy **bản của ai**, và **quyết định lúc nào**?

## Hai kiểu — hai người gác khác nhau
| | **Static type** (kiểu khai báo) | **Dynamic type** (kiểu vật thể thật) |
|---|---|---|
| Ví dụ | `INotify n` | `new EmailNotification(...)` |
| Ai dùng | **Compiler** (trước khi chạy) | **Runtime / JVM** (lúc chạy) |
| Quyết định gì | method nào *được phép gọi* (kiểm hợp đồng) | method nào *thực sự chạy* (chọn bản override) |
| Thời điểm | compile-time | runtime |

- Compiler **gác cửa**: nhìn static type `INotify` → chỉ cho gọi method có trong hợp đồng. `n.sendBulk()` bị chặn dù vật thể Email có thật, vì `INotify` không hứa `sendBulk()`.
- Runtime **chọn bản**: nhìn dynamic type thật → gọi `send()` của đúng class đó. Đây là **dynamic dispatch** (còn gọi *late binding* / *runtime polymorphism*).

## Vì sao BẮT BUỘC phải ở runtime
```java
INotify n = userInput.equals("email")
    ? new EmailNotification(...) : new SmsNotification(...);
n.send("hi");
```
`userInput` chỉ có lúc chạy → compile-time không thể biết `n` là Email hay Sms → **không thể** chốt bản `send()` lúc compile. Suy ra việc chọn method phải hoãn tới runtime.

## Hệ quả: đây là "cái máy" chạy Dependency Injection
Viết code dựa trên static type `INotify` (compiler yên tâm vì hợp đồng có `send()`), lúc chạy tráo bất kỳ implementation nào → dynamic dispatch tự gọi đúng bản. Không có dynamic dispatch thì "inject [[interface]] rồi swap implementation" ([[Dependency Injection]], [[Interface vs Abstract Class]]) chỉ là ý tưởng suông.

## Cast = đổi static type mà compiler nhìn thấy
`((EmailNotification) n).sendBulk(...)` hợp lệ vì cast khai báo lại "hãy coi `n` là `EmailNotification`" → static type mới có `sendBulk()` trong hợp đồng. Vật thể không đổi, chỉ góc nhìn của compiler đổi.
