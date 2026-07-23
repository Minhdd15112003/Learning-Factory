# Output — Observer Pattern: Payment Notification (C)

## Mô tả
`PaymentService` thông báo cho nhiều kênh (Email, SMS, App) khi thanh toán thành công, mà không cần biết cụ thể có những kênh nào.

## Cơ chế
- Interface `Notifier` (Observer) định nghĩa `update(String transactionId)`
- Các concrete observer: `EmailNotifier`, `SmsNotifier`, `AppNotifier`
- `PaymentService` (Subject) giữ `List<Notifier>`, có `subscribe()` để đăng ký, `notifyAll()` duyệt list gọi `update()` trên từng phần tử
- Thêm kênh mới → thêm class + subscribe, không sửa PaymentService

## Code hoàn chỉnh

```java
import java.util.ArrayList;
import java.util.List;

public class ObserverPatternPractice {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();

        paymentService.subscribe(new SmsNotifier());
        paymentService.subscribe(new EmailNotifier());
        paymentService.subscribe(new AppNotifier());

        paymentService.completePayment("TXN-9999");
    }
}

interface Notifier {
    void update(String transactionId);
}

class EmailNotifier implements Notifier {
    public void update(String transactionId) {
        System.out.println("Gửi Email cho giao dịch: " + transactionId);
    }
}

class SmsNotifier implements Notifier {
    public void update(String transactionId) {
        System.out.println("Gửi SMS cho giao dịch: " + transactionId);
    }
}

class AppNotifier implements Notifier {
    public void update(String transactionId) {
        System.out.println("Gửi app cho giao dịch: " + transactionId);
    }
}

class PaymentService {
    List<Notifier> list = new ArrayList<>();

    public void subscribe(Notifier n) { list.add(n); }

    public void notifyAll(String transactionId) {
        for (Notifier notifier : list) {
            notifier.update(transactionId);
        }
    }

    public void completePayment(String transactionId) {
        System.out.println("Thanh toán thành công: " + transactionId);
        notifyAll(transactionId);
    }
}
```

## Cạm bẫy Java
Đặt tên method `notifyAll()` trùng với `Object.notifyAll()` (dùng cho Monitor/đa luồng) → gọi nhầm gây `IllegalMonitorStateException`. Đổi tên thành `notifyObservers()` hoặc `broadcast()`.

## Bài học rút ra
- Subject giữ List interface, duyệt và gọi update — không biết cụ thể observer nào.
- Thêm kênh mới = thêm class + subscribe, không sửa Subject → Open/Closed.
- Cả Strategy và Observer đều dùng interface tách coupling — Strategy cho 1 hành vi, Observer cho N subscriber.
