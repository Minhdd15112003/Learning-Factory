# Output — Strategy Pattern: Fee Calculation (C)

## Mô tả
Hệ thống tính phí giao dịch theo loại (Internal, Interbank, International, Napas247). Đổi chiến lược tính phí lúc runtime qua setter mà không sửa `TransactionProcessor`.

## Cơ chế
- Interface `FeeStrategy` định nghĩa `calculateFee(long amount)`
- Mỗi loại phí là một class riêng implements interface
- `TransactionProcessor` (context) giữ tham chiếu đến interface, đổi strategy bằng setter
- Thêm loại phí mới → thêm class, không sửa code cũ → Open/Closed Principle

## Code hoàn chỉnh

```java
public class StrategyPatternPractice {
    public static void main(String[] args) {
        TransactionProcessor processor = new TransactionProcessor();

        processor.setFeeStrategy(new InternalFeeStrategy());
        System.out.println(processor.processTransaction(1_000_000));     // 0

        processor.setFeeStrategy(new InterbankFeeStrategy());
        System.out.println(processor.processTransaction(1_000_000));     // 10000

        processor.setFeeStrategy(new InternationalFeeStrategy());
        System.out.println(processor.processTransaction(1_000_000));     // 50000

        processor.setFeeStrategy(new Napas247FeeStrategy());
        System.out.println(processor.processTransaction(100));           // 123123
    }
}

interface FeeStrategy {
    long calculateFee(long amount);
}

class InternalFeeStrategy implements FeeStrategy {
    public long calculateFee(long amount) { return 0; }
}

class InterbankFeeStrategy implements FeeStrategy {
    public long calculateFee(long amount) { return 10_000; }
}

class InternationalFeeStrategy implements FeeStrategy {
    public long calculateFee(long amount) {
        return Math.max(50_000, (long) (amount * 0.015));
    }
}

class Napas247FeeStrategy implements FeeStrategy {
    public long calculateFee(long amount) { return 123123; }
}

class TransactionProcessor {
    FeeStrategy fee;

    public void setFeeStrategy(FeeStrategy fee) { this.fee = fee; }

    public long processTransaction(long amount) {
        return fee.calculateFee(amount);
    }
}
```

## Bài học rút ra
- Strategy tách mỗi thuật toán thành class riêng — thêm class mới, không sửa context cũ.
- Đổi hành vi lúc runtime bằng setter — cùng một `TransactionProcessor` xử lý được mọi loại phí.
- So với Factory: Factory dời switch-case sang chỗ khác, Strategy loại bỏ hẳn.
