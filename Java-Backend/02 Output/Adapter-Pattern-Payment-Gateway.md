# Output — Adapter Pattern: Payment Gateway Integration (C)

## Mô tả
Hệ thống thanh toán tích hợp hai provider bên ngoài (VNPay, MoMo) thông qua Adapter Pattern. Code nội bộ chỉ biết interface `PaymentGateway` — không biết SDK nào đằng sau.

## Cấu trúc

```
PaymentGateway (interface nội bộ)
├── VnpayAdapter  → holds VnpayClient + merchantId
└── MomoAdapter   → holds MomoClient
```

## Code hoàn chỉnh

```java
public class AdapterPractice {

    public static void main(String[] args) {
        VnpayClient vnpayClient = new VnpayClient();
        VnpayAdapter vnpayAdapter = new VnpayAdapter("MB-MERCHANT", vnpayClient);

        MomoClient momoClient = new MomoClient();
        MomoAdapter momoAdapter = new MomoAdapter(momoClient);

        // Code nội bộ chỉ biết PaymentGateway — không biết VNPay hay MoMo
        processPayment(vnpayAdapter, "0901234567", 500000);
        processPayment(momoAdapter, "0901234567", 500000);
    }

    // ── Interface nội bộ ──
    interface PaymentGateway {
        void charge(String accountId, double amount);
    }

    // ── SDK bên ngoài (không được sửa) ──
    static class VnpayClient {
        void vnpayProcessPayment(String merchantId, String customerId, long amountInVnd) {
            System.out.println("[VNPay] Processing: merchant=" + merchantId
                    + ", customer=" + customerId + ", amount=" + amountInVnd + " VND");
        }
    }

    static class MomoClient {
        void momoCharge(String phoneNumber, int amountInDong) {
            System.out.println("[MoMo] Charging " + amountInDong + " dong to phone " + phoneNumber);
        }
    }

    // ── Adapter: VNPay ──
    static class VnpayAdapter implements PaymentGateway {
        private final String merchantId;
        private final VnpayClient client;

        public VnpayAdapter(String merchantId, VnpayClient client) {
            this.client = client;
            this.merchantId = merchantId;
        }

        @Override
        public void charge(String accountId, double amount) {
            this.client.vnpayProcessPayment(merchantId, accountId, (long) amount);
        }
    }

    // ── Adapter: MoMo ──
    static class MomoAdapter implements PaymentGateway {
        private final MomoClient client;

        public MomoAdapter(MomoClient client) {
            this.client = client;
        }

        @Override
        public void charge(String accountId, double amount) {
            this.client.momoCharge(accountId, (int) amount);
        }
    }

    // ── Method nội bộ — chỉ biết interface ──
    static void processPayment(PaymentGateway gateway, String accountId, double amount) {
        gateway.charge(accountId, amount);
    }
}
```

## Đầu ra
```
[VNPay] Processing: merchant=MB-MERCHANT, customer=0901234567, amount=500000 VND
[MoMo] Charging 500000 dong to phone 0901234567
```

## Bài học rút ra
- Adapter = implements interface nội bộ + holds object bên ngoài + chuyển đổi tham số.
- Thêm provider mới (ZaloPay, ...) → thêm 1 class adapter, không sửa code cũ → Open/Closed.
- Field nên `private final` — SDK object và config không ai nên sửa sau khi tạo adapter.
- Inner class trong Java cần `static` nếu dùng từ context `static` (method `main`).
