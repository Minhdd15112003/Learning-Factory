// ObserverPatternPractice.java (C)
// Bài thực hành: Observer Pattern — Payment Notification

import java.util.ArrayList;
import java.util.List;

/*
Yêu cầu:
Hiện tại PaymentService đang phải tự tay gọi Email và SMS.
Hãy tái cấu trúc lại dùng Observer Pattern để PaymentService không cần biết
có những loại Notifier nào.
*/

public class ObserverPatternPractice {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();

        // TODO 4: "Đăng ký kênh" cho paymentService bằng cách tạo và truyền
        // EmailNotifier và SmsNotifier vào hàm subscribe().
        EmailNotifier emailNotifier = new EmailNotifier();
        SmsNotifier smsNotifier = new SmsNotifier();
        AppNotifier appNotifier = new AppNotifier();
        paymentService.subscribe(smsNotifier);
        paymentService.subscribe(emailNotifier);
        paymentService.subscribe(appNotifier);
        // Chạy thanh toán
        paymentService.completePayment("TXN-9999");


        // CÂU HỎI THỰC HÀNH:
        // Hãy tự viết thêm 1 class AppNotifier (Push notification),
        // đăng ký nó và chạy lại xem PaymentService có cần sửa gì không?
    }
}


// TODO 1: Tạo interface chung Notifier (Observer)
// Có 1 method: void update(String transactionId);
interface Notifier{
    void update(String transactionId);
}

// TODO 2: Tạo các class thực thi Notifier (Concrete Observers)
// - EmailNotifier: in ra "Gửi Email cho giao dịch: " + id
// - SmsNotifier: in ra "Gửi SMS cho giao dịch: " + id
class EmailNotifier implements Notifier {
    public void update(String transactionId){
        System.err.println("Gửi Email cho giao dịch: " + transactionId);
    }
}

class SmsNotifier implements Notifier {
    public void update(String transactionId){
        System.err.println("Gửi SMS cho giao dịch: " + transactionId);
    }
}

class AppNotifier implements Notifier {
    public void update(String transactionId){
        System.err.println("Gửi app cho giao dịch: " + transactionId);
    }
}

// TODO 3: Sửa class PaymentService (Subject / Publisher)
class PaymentService {
    // 1. Tạo một List giữ các Notifier
    List<Notifier> list = new ArrayList<>(); 
    // 2. Viết method subscribe(Notifier n) để thêm vào List
    public void subscribe(Notifier n){
        list.add(n);
    }
    
    // 3. Viết method notifyAll(String transactionId) duyệt qua List và gọi update()
    public void notifyAll(String transactionId){
        for (Notifier notifier : list) {
            notifier.update(transactionId);
        }
    }
    public void completePayment(String transactionId) {
        System.out.println("Thanh toán thành công: " + transactionId);
        // Thay vì gọi hardcode, hãy gọi hàm notifyAll() ở đây
        notifyAll(transactionId);
    }
}

