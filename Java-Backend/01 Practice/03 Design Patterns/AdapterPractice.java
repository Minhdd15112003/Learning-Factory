/**
 * Thực hành Adapter Pattern — Tích hợp Payment Gateway (C)
 *
 * Luật chơi:
 * - KHÔNG sửa PaymentGateway, VnpayClient, MomoClient (giả lập SDK bên ngoài).
 * - Chỉ viết code ở các vị trí TODO.
 */
public class AdapterPractice {

    // ═══════════════════════════════════════════
    // TODO 5: main — tạo adapter, gọi processPayment()
    // ═══════════════════════════════════════════
    public static void main(String[] args) {
        // TODO: Tạo VnpayClient + VnpayAdapter (merchantId = "MB-MERCHANT")
        // TODO: Tạo MomoClient + MomoAdapter
        // TODO: Gọi processPayment() với từng adapter, accountId = "0901234567", amount
        // = 500000
        VnpayClient vnpayClient = new VnpayClient();
        VnpayAdapter vnpayAdapter = new VnpayAdapter("MB-MERCHANT", vnpayClient);

        MomoClient momoClient = new MomoClient();
        MomoAdapter momoAdapter = new MomoAdapter(momoClient);

        processPayment(vnpayAdapter, "0901234567", 500000);
        processPayment(momoAdapter, "0901234567", 500000);
    }

    // ═══════════════════════════════════════════
    // INTERFACE NỘI BỘ — KHÔNG ĐƯỢC SỬA
    // ═══════════════════════════════════════════
    interface PaymentGateway {
        void charge(String accountId, double amount);
    }

    // ═══════════════════════════════════════════
    // SDK BÊN NGOÀI — KHÔNG ĐƯỢC SỬA
    // ═══════════════════════════════════════════
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

    // ═══════════════════════════════════════════
    // TODO 1 + TODO 2: VnpayAdapter
    // Implements PaymentGateway.
    // Constructor nhận VnpayClient + merchantId.
    // charge() chuyển đổi tham số rồi gọi vnpayProcessPayment().
    // ═══════════════════════════════════════════

    // TODO: Viết class VnpayAdapter ở đây
    static class VnpayAdapter implements PaymentGateway {
        String merchantId;
        VnpayClient client;

        public VnpayAdapter(String merchantId, VnpayClient client) {
            this.client = client;
            this.merchantId = merchantId;
        }

        @Override
        public void charge(String accountId, double amount) {
            this.client.vnpayProcessPayment(merchantId, accountId, (long) amount);
        }
    }

    // ═══════════════════════════════════════════
    // TODO 3 + TODO 4: MomoAdapter
    // Implements PaymentGateway.
    // Constructor nhận MomoClient.
    // charge() chuyển đổi: accountId → phoneNumber, double → int.
    // ═══════════════════════════════════════════

    // TODO: Viết class MomoAdapter ở đây
    static class MomoAdapter implements PaymentGateway {
        MomoClient client;

        public MomoAdapter(MomoClient client) {
            this.client = client;
        }

        @Override
        public void charge(String accountId, double amount) {
            this.client.momoCharge(accountId, (int) amount);
        }
    }

    // ═══════════════════════════════════════════
    // Method nội bộ — chỉ biết PaymentGateway, không biết VNPay hay MoMo
    // ═══════════════════════════════════════════
    static void processPayment(PaymentGateway gateway, String accountId, double amount) {
        gateway.charge(accountId, amount);
    }

}
