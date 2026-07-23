# Output — Factory Pattern: Payment System (C)

## Mô tả
Tạo object Payment theo loại (VISA, MOMO, MASTER) mà client không cần biết class cụ thể. Thêm loại mới chỉ thêm class + case trong factory.

## Cơ chế
- Interface `Payment` định nghĩa hợp đồng `pay(long amount)`
- Các concrete class implement interface
- `PaymentFactory.create(String type)` dùng switch trả về đúng object — client chỉ biết interface

## Code hoàn chỉnh

```java
public class FactoryPatternPractice {
    public static void main(String[] args) {
        String[] types = {"VISA", "MOMO", "MASTER"};
        for (String type : types) {
            Payment payment = PaymentFactory.create(type);
            payment.pay(100_000);
        }
        try {
            Payment unknown = PaymentFactory.create("BITCOIN");
            unknown.pay(999);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}

interface Payment {
    void pay(long amount);
}

class VisaCard implements Payment {
    public void pay(long amount) {
        System.out.println("Paid " + amount + " via Visa");
    }
}

class Momo implements Payment {
    public void pay(long amount) {
        System.out.println("Paid " + amount + " via Momo");
    }
}

class MasterCard implements Payment {
    public void pay(long amount) {
        System.out.println("Paid " + amount + " via MasterCard");
    }
}

class PaymentFactory {
    public static Payment create(String type) {
        return switch (type) {
            case "VISA" -> new VisaCard();
            case "MOMO" -> new Momo();
            case "MASTER" -> new MasterCard();
            default -> throw new IllegalArgumentException("Unknown payment type: " + type);
        };
    }
}
```

## Bài học rút ra
- Factory dời logic khởi tạo ra khỏi client — client chỉ biết interface.
- Java 17 enhanced switch (`->`) gọn hơn switch-case cổ điển.
- Factory vẫn có switch-case bên trong — Strategy giải quyết triệt để hơn khi hành vi cần đổi lúc runtime.
