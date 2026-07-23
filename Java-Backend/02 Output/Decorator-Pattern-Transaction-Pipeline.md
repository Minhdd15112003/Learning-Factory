# Output — Decorator Pattern: Transaction Processing Pipeline (C)

## Mô tả
Thêm hành vi phụ (logging, validation, audit) lên `TransactionService` mà không sửa class gốc. Các decorator lồng nhau như các lớp vỏ hành — mỗi lớp thêm một hành vi.

## Cơ chế
- Decorator implements **cùng interface** với object gốc
- Giữ `wrapped` (instance của cùng interface) qua constructor
- Trong method: thực hiện hành vi phụ → gọi `wrapped.execute()` → logic gốc vẫn chạy
- Lồng nhau: thứ tự lồng = thứ tự chạy (ngoài → trong)

## Code hoàn chỉnh

```java
public class DecoratorPractice {

    static class Transaction {
        final String id;
        final double amount;
        Transaction(String id, double amount) {
            this.id = id;
            this.amount = amount;
        }
    }

    interface TransactionService {
        void execute(Transaction txn);
    }

    static class CoreTransactionService implements TransactionService {
        @Override
        public void execute(Transaction txn) {
            System.out.println("[CORE] Executing transaction " + txn.id + ", amount: " + txn.amount);
        }
    }

    static class LoggingDecorator implements TransactionService {
        private final TransactionService wrapped;
        public LoggingDecorator(TransactionService wrapped) { this.wrapped = wrapped; }

        @Override
        public void execute(Transaction txn) {
            System.out.println("[LOG] Processing txn: " + txn.id);
            wrapped.execute(txn);
            System.out.println("[LOG] Done: " + txn.id);
        }
    }

    static class ValidationDecorator implements TransactionService {
        private final TransactionService wrapped;
        public ValidationDecorator(TransactionService wrapped) { this.wrapped = wrapped; }

        @Override
        public void execute(Transaction txn) {
            if (txn.amount <= 0) {
                System.out.println("[VALIDATION] Rejected: " + txn.id + " — invalid amount");
            } else {
                System.out.println("[VALIDATION] Passed: " + txn.id);
                wrapped.execute(txn);
            }
        }
    }

    static class AuditDecorator implements TransactionService {
        private final TransactionService wrapped;
        public AuditDecorator(TransactionService wrapped) { this.wrapped = wrapped; }

        @Override
        public void execute(Transaction txn) {
            wrapped.execute(txn);
            System.out.println("[AUDIT] Transaction " + txn.id + " completed, amount: " + txn.amount);
        }
    }

    public static void main(String[] args) {
        TransactionService core = new CoreTransactionService();
        TransactionService pipeline = new LoggingDecorator(
                new ValidationDecorator(
                        new AuditDecorator(core)));

        pipeline.execute(new Transaction("TXN-001", 500000));
        System.out.println("---");
        pipeline.execute(new Transaction("TXN-002", -100));
    }
}
```

## Đầu ra
```
[LOG] Processing txn: TXN-001
[VALIDATION] Passed: TXN-001
[CORE] Executing transaction TXN-001, amount: 500000.0
[AUDIT] Transaction TXN-001 completed, amount: 500000.0
[LOG] Done: TXN-001
---
[LOG] Processing txn: TXN-002
[VALIDATION] Rejected: TXN-002 — invalid amount
[LOG] Done: TXN-002
```

## So sánh với Adapter
| | Adapter | Decorator |
|---|---|---|
| Mục đích | Chuyển đổi interface không khớp | Thêm hành vi, giữ nguyên interface |
| Object bên trong | Class bên ngoài (SDK khác interface) | Class cùng interface |

## Liên kết Spring
`@Transactional` = proxy (decorator) bọc ngoài bean, thêm hành vi mở/đóng transaction. Gọi từ trong cùng class không có tác dụng — không đi qua lớp proxy.

## Bài học rút ra
- Decorator = implements cùng interface + holds wrapped + thêm hành vi rồi delegate.
- ValidationDecorator chặn luồng bằng cách **không gọi** `wrapped.execute()` — decorator kiểm soát được đầu vào lẫn đầu ra.
- Lồng nhiều decorator = pipeline xử lý — thứ tự lồng quyết định thứ tự chạy.
