# Câu trả lời phỏng vấn — Static type vs Dynamic type & Dynamic Dispatch (C)

> Đúc từ buổi 2026-07-07, dựa trên phần bạn tự giải thích qua bài `Polymorphism.java`. [[Polymorphism]]

## Câu hỏi
> "Static type và dynamic type khác nhau chỗ nào, và dynamic dispatch xảy ra lúc nào?"

## Trả lời gọn (đủ để nói ra miệng)
Một biến có **hai** kiểu, tồn tại song song:

- **Static type** = kiểu *khai báo* (vd `INotify n`). **Compiler** dùng nó, tại **compile-time**, để **gác method nào được phép gọi**. Gọi method không có trong hợp đồng của static type → compiler chặn ngay.
- **Dynamic type** = kiểu *vật thể thật* mà biến đang trỏ tới (vd `EmailNotification`). **JVM** dùng nó, tại **runtime**, để **chọn bản method nào thực sự chạy**.

Việc runtime chọn bản method theo dynamic type gọi là **dynamic dispatch** (late binding). Nó **bắt buộc** ở runtime vì compile-time nhiều khi chưa biết vật thể thật là gì:

```java
INotify n = userInput.equals("email")
    ? new EmailNotification(...) : new SmsNotification(...);
n.send("hi");   // bản send() nào chạy? chỉ runtime mới biết
```

## Bằng chứng từ code đã chạy
- **Một** dòng `n.send(...)` trong vòng lặp `List<INotify>` → in khi EMAIL khi SMS (dynamic dispatch chọn theo vật thể thật).
- `n.sendBulk()` không cast → compiler chặn (`sendBulk` không có trong static type `INotify`).
- `((EmailNotification) n).sendBulk(...)` → chạy được, vì **cast đổi static type mà compiler thấy ở đúng biểu thức đó** (interface và vật thể không đổi).

## Câu chốt (soundbite)
> "Static type quyết định *gọi được gì* (compiler), dynamic type quyết định *chạy bản nào* (runtime). Dynamic dispatch chính là cái máy làm cho 'code theo interface' và Dependency Injection hoạt động." [[Dependency Injection]] · [[Interface vs Abstract Class]]
