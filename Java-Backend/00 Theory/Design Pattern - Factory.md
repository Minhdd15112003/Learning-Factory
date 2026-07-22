---
status: Understood
tags: [java, design-pattern, review]
sr-due: 2026-07-22
sr-interval: 4
sr-ease: 270
---

# Design Pattern - Factory

[[Factory]] (hay cụ thể là Simple Factory) là mẫu thiết kế giúp gom toàn bộ logic khởi tạo đối tượng phức tạp hoặc phụ thuộc vào điều kiện (như `if-else`/`switch-case`) vào một class duy nhất (Factory Class). Code gọi (caller) sẽ chỉ giao tiếp thông qua interface và không cần biết cụ thể lớp nào được tạo ra.

Ý tưởng của Factory là: gom toàn bộ logic "nhận type → trả về đúng object" vào một class riêng biệt

## 1. Cơ chế hoạt động

1. **Interface/Abstract Class chung**: Định nghĩa hành vi chung cho tất cả các đối tượng (ví dụ: `Payment` với method `pay()`).
2. **Lớp triển khai cụ thể (Concrete Implementations)**: Các class thực tế implement interface trên (`VisaCard`, `Momo`, `MasterCard`).
3. **Factory Class**: Chứa static method nhận tham số đầu vào (ví dụ: `String type`), xử lý logic `switch-case` để khởi tạo đúng lớp cụ thể và trả về dưới dạng interface.

## 2. Code chuẩn hóa

```java
// 1. Interface chung
public interface Payment {
    void pay(long amount);
}

// 2. Các lớp concrete
public class VisaCard implements Payment {
    @Override
    public void pay(long amount) {
        System.out.println("Paid " + amount + " via Visa");
    }
}

public class Momo implements Payment {
    @Override
    public void pay(long amount) {
        System.out.println("Paid " + amount + " via Momo");
    }
}

// 3. Factory Class
public class PaymentFactory {
    public static Payment create(String type) {
        return switch (type) {
            case "VISA" -> new VisaCard();
            case "MOMO" -> new Momo();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
```

## 3. Cách xử lý tham số khởi tạo khác nhau

Khi các lớp concrete cần các tham số khởi tạo khác nhau (ví dụ: `VisaCard` cần số thẻ, `Momo` cần token ví):

- **Không đặt vào method của interface**: Việc thêm tham số đặc thù vào method interface (như `pay(amount, cardNumber, token)`) sẽ phá vỡ tính trừu tượng (Abstraction), ép các class khác phải nhận tham số dư thừa.
- **Giải pháp**: Truyền các tham số riêng biệt qua **constructor** của từng class khi khởi tạo thông qua Factory (hoặc builder bên trong Factory). Interface `pay(long amount)` chỉ giữ lại những gì thực sự là điểm chung.
