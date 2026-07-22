// FactoryPatternPractice.java (C)
// Bài thực hành: Factory Pattern — Payment System
//
// Yêu cầu:
// 1. Hoàn thiện interface Payment
// 2. Tạo 3 class implement: VisaCard, Momo, MasterCard
// 3. Hoàn thiện PaymentFactory trả về đúng object theo type
// 4. Chạy main để kiểm tra

public class FactoryPatternPractice {
    public static void main(String[] args) {
        String[] types = {"VISA", "MOMO", "MASTER"};

        for (String type : types) {
            Payment payment = PaymentFactory.create(type);
            payment.pay(100_000);
        }

        // Thử với type không hợp lệ — phải ném exception
        try {
            Payment unknown = PaymentFactory.create("BITCOIN");
            unknown.pay(999);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}

// TODO 1: Khai báo interface Payment với method pay(long amount)

interface Payment {
   void pay(long amount);
};

// TODO 2: Tạo 3 class implement Payment
// - VisaCard: in ra "Paid <amount> via Visa"
// - Momo: in ra "Paid <amount> via Momo"
// - MasterCard: in ra "Paid <amount> via MasterCard"
class VisaCard implements Payment {
    public void pay(long amount){
        System.out.println("Paid " +amount+ " via Visa");
    }
}

class Momo implements Payment {
    public void pay(long amount){
        System.out.println("Paid " +amount+ " via Momo");
    }
}

class MasterCard implements Payment {
    public void pay(long amount){
        System.out.println("Paid " +amount+ " via MasterCard");
    }
}


// TODO 3: Hoàn thiện PaymentFactory
class PaymentFactory {
    // TODO: Viết static method create(String type) trả về Payment
    // - "VISA" → VisaCard
    // - "MOMO" → Momo
    // - "MASTER" → MasterCard
    // - Còn lại → throw IllegalArgumentException("Unknown payment type: " + type)
    public static Payment create(String type){
        return switch (type) {
            case "VISA" -> new VisaCard();
            case "MOMO" -> new Momo();
            case "MASTER" -> new MasterCard();
            default -> throw new IllegalArgumentException("Unknown payment type: " + type);
        };
        
    }

}
