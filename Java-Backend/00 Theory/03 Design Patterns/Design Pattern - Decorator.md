---
status: Understood
tags: [java, design-pattern, review]
sr-due: 2026-07-25
sr-interval: 2
sr-ease: 250
---

# Design Pattern - Decorator (C)

> Note do mình (Claudian) soạn sau khi bạn giải thích được cơ chế bằng lời của mình.

## Vấn đề giải quyết
Cần thêm hành vi phụ (logging, encryption, audit...) lên một method mà **không sửa class gốc**. Nhét tất cả vào method gốc → method phình to, trộn logic chính với logic phụ, vi phạm Single Responsibility. Thêm/bớt tính năng phải sửa vào giữa method → dễ lỗi.

## Cơ chế
Decorator tạo class bọc ngoài:
1. **Implements cùng interface** với object gốc
2. **Giữ một instance** của interface đó (`wrapped`/`delegate`) qua constructor
3. Trong method: thực hiện **hành vi phụ** (log, encrypt...), rồi **gọi tiếp** `wrapped.execute()` để logic gốc vẫn chạy
4. Có thể **lồng nhau** — mỗi lớp thêm một hành vi, thứ tự lồng = thứ tự chạy (ngoài vào trong)

```java
interface TransactionService {
    void execute(Transaction txn);
}

class CoreTransactionService implements TransactionService {
    public void execute(Transaction txn) { /* logic gốc */ }
}

class LoggingDecorator implements TransactionService {
    private final TransactionService wrapped;

    public LoggingDecorator(TransactionService wrapped) {
        this.wrapped = wrapped;
    }

    public void execute(Transaction txn) {
        System.out.println("LOG: before execute");
        wrapped.execute(txn);  // delegate vào trong
        System.out.println("LOG: after execute");
    }
}

// Lồng: Logging → Encryption → Core
TransactionService service = new LoggingDecorator(
    new EncryptionDecorator(
        new CoreTransactionService()
    )
);
```

## So sánh với Adapter
| | Adapter | Decorator |
|---|---|---|
| Mục đích | Chuyển đổi interface không khớp | Thêm hành vi, giữ nguyên interface |
| Object bên trong | Class bên ngoài (SDK khác interface) | Class cùng interface |
| Cả hai | Implements interface + holds object bên trong |

## Liên kết Spring (Giai đoạn 2)
`@Transactional` hoạt động theo cơ chế Decorator — Spring tạo proxy bọc ngoài bean gốc, thêm hành vi mở/đóng transaction trước/sau method. Gọi `@Transactional` method từ **trong cùng class** không có tác dụng — vì không đi qua lớp proxy bên ngoài. (Câu hỏi phỏng vấn kinh điển.)

## Liên quan
[[Design Pattern - Adapter]] · [[Design Pattern - Strategy]] · [[Interface vs Abstract Class]]
