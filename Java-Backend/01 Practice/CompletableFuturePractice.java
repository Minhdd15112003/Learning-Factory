import java.util.concurrent.*;

/**
 * Bài tập thực hành: CompletableFuture — Pipeline bất đồng bộ.
 *
 * Kịch bản: Hệ thống thanh toán ngân hàng.
 * - Bước 1: Gọi API kiểm tra số dư (bất đồng bộ, mất 1 giây)
 * - Bước 2: Trừ tiền và tạo mã giao dịch
 * - Bước 3: In biên lai
 * - Xử lý lỗi: nếu bất kỳ bước nào fail → trả kết quả dự phòng
 *
 * Chú thích: (C) AI-generated skeleton.
 */
public class CompletableFuturePractice {

    public static void main(String[] args) throws Exception {
        System.out.println("=== BÀI 1: PIPELINE CƠ BẢN ===");
        exercise1();

        System.out.println("\n=== BÀI 2: XỬ LÝ LỖI VỚI EXCEPTIONALLY ===");
        exercise2();

        System.out.println("\n=== BÀI 3: CHẠY SONG SONG 2 DỊCH VỤ ===");
        exercise3();
    }

    // ============================================================
    // BÀI 1: Xây pipeline bất đồng bộ
    // Mục tiêu: supplyAsync → thenApply → thenAccept
    // ============================================================
    static void exercise1() throws Exception {
        // TODO: Tạo một CompletableFuture pipeline:
        // 1. supplyAsync: gọi checkBalance("ACC-001") — trả về số dư (long)
        // 2. thenApply: nếu số dư >= 500_000 thì trả về "TXN-OK", ngược lại "TXN-FAIL"
        // 3. thenAccept: in ra "Kết quả giao dịch: <kết quả>"
        //
        // Gợi ý: CompletableFuture.supplyAsync(() -> ...)
        // .thenApply(balance -> ...)
        // .thenAccept(result -> ...)
        // .join(); // chờ pipeline hoàn thành

        // --- Viết code ở đây ---
        CompletableFuture.supplyAsync(() -> checkBalance("ACC-001"))
                .thenApply(t -> {
                    if (t < 500_000) {
                        return "TXN-FAIL";
                    }
                    return "TXN-OK";
                }).thenAccept(t -> System.out.println("Kết quả nhận được: " + t)).join();

        // --- Kết thúc ---
    }

    // ============================================================
    // BÀI 2: Pipeline có xử lý lỗi
    // Mục tiêu: supplyAsync → thenApply → exceptionally → thenAccept
    // ============================================================
    static void exercise2() throws Exception {
        // TODO: Tạo pipeline tương tự bài 1, nhưng:
        // 1. supplyAsync: gọi checkBalanceUnstable("ACC-002") — hàm này CÓ THỂ ném
        // RuntimeException
        // 2. thenApply: trừ tiền và tạo mã giao dịch (trả về String "TXN-12345")
        // 3. exceptionally: bắt lỗi, in ra thông báo lỗi, trả về "TXN-FAILED"
        // 4. thenAccept: in ra "Mã giao dịch: <kết quả>"
        //
        // Kết quả mong đợi: pipeline KHÔNG crash, in ra "Mã giao dịch: TXN-FAILED"

        // --- Viết code ở đây ---

        CompletableFuture.supplyAsync(() -> checkBalanceUnstable("ACC-002"))
            .thenApply(balance -> "TXN-12345")
            .exceptionally(t -> {
                System.out.println("Lỗi giao dịch: " + t.getMessage());
                return "TXN-FAILED";
            })
            .thenAccept(t -> System.out.println("Mã giao dịch: " + t)).join();

        // --- Kết thúc ---
    }

    // ============================================================
    // BÀI 3: Chạy song song 2 dịch vụ, kết hợp kết quả
    // Mục tiêu: thenCombine — chạy 2 CompletableFuture đồng thời, gộp kết quả
    // ============================================================
    static void exercise3() throws Exception {
        // TODO:
        // 1. future1 = supplyAsync: gọi checkBalance("ACC-001") — trả về số dư
        // 2. future2 = supplyAsync: gọi getAccountName("ACC-001") — trả về tên chủ tài
        // khoản
        // 3. Dùng future1.thenCombine(future2, (balance, name) -> ...) để gộp thành:
        // "Chủ TK: <name>, Số dư: <balance>"
        // 4. thenAccept: in ra kết quả
        //
        // Hai hàm chạy song song, tổng thời gian ~1 giây (không phải 2 giây)

        long start = System.currentTimeMillis();

        // --- Viết code ở đây ---
        var future1 =  CompletableFuture.supplyAsync(() -> checkBalance("ACC-001"));
        var future2 =  CompletableFuture.supplyAsync(() -> getAccountName("ACC-001"));

        future1.thenCombine(future2, (balance, name) -> {
            System.out.println("Chủ TK: " + name + "Số dư: " + balance);
            return "Chủ TK: " + name + "Số dư: " + balance;
        }).join();
        // --- Kết thúc ---

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Thời gian thực thi: " + elapsed + "ms (mục tiêu: ~1000ms)");
    }

    // ============================================================
    // CÁC HÀM GIẢ LẬP — KHÔNG CẦN SỬA
    // ============================================================

    static long checkBalance(String accountId) {
        System.out.println("[" + Thread.currentThread().getName() + "] Đang kiểm tra số dư " + accountId + "...");
        sleep(1000);
        return 1_500_000; // 1.5 triệu
    }

    static long checkBalanceUnstable(String accountId) {
        System.out.println("[" + Thread.currentThread().getName() + "] Đang kiểm tra số dư " + accountId + "...");
        sleep(500);
        throw new RuntimeException("Lỗi kết nối đến Core Banking!");
    }

    static String getAccountName(String accountId) {
        System.out
                .println("[" + Thread.currentThread().getName() + "] Đang lấy tên chủ tài khoản " + accountId + "...");
        sleep(1000);
        return "Nguyen Van A";
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
