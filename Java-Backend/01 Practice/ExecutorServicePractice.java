import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

/**
 * (C) ExecutorServicePractice - Bài tập thực hành Thread Pool và Future.
 * Mô phỏng hệ thống báo cáo ngân hàng.
 */
public class ExecutorServicePractice {

    public static void main(String[] args) throws Exception {
        // Khởi tạo pool với 3 nhân viên
        ExecutorService pool = Executors.newFixedThreadPool(3);

        System.out.println("--- BẮT ĐẦU XỬ LÝ BÁO CÁO ---");
        long start = System.currentTimeMillis();

        // BÀI TẬP 1: Chạy song song 2 service lấy dữ liệu.
        // TODO: Gửi task lấy số dư (getAccountBalance) vào pool.
        // TODO: Gửi task lấy lịch sử giao dịch (getTransactionHistory) vào pool.

        // Future<Double> balanceFuture = ...
        // Future<List<String>> historyFuture = ...
        Future<Double> balanceFuture = pool.submit(() -> getAccountBalance());
        Future<List<String>> histoFuture = pool.submit(() -> getTransactionHistory());

        System.out.println("[Main] Đang làm việc khác trong khi đợi dữ liệu...");
        Thread.sleep(500); // Giả sử main thread làm việc khác

        // TODO: Lấy kết quả từ 2 Future ra (sẽ block ở đây nếu chưa xong)
        // Double balance = ...
        // List<String> history = ...
        Double balance = balanceFuture.get();
        List<String> history = histoFuture.get();

        long end = System.currentTimeMillis();

        System.out.println("Kết quả: Số dư " + balance + ", Số giao dịch: " + history.size());
        System.out.println("Tổng thời gian: " + (end - start) + "ms (Mục tiêu: ~1000ms)");

        // BÀI TẬP 2: Error Handling với Future
        // TODO: Gửi task (failTask) vào pool. Gọi get() và bọc try-catch để xem
        // exception.
        try {
            String fail = pool.submit(() -> failTask()).get();
            System.out.println("Kết quả: " + fail);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Task bị lỗi nặng: " + e.getCause()); // In ra ArithmeticException
        }
        pool.shutdown(); // Luôn nhớ đóng pool khi xong việc
    }

    // Giả lập lấy số dư từ database (mất 1s)
    private static Double getAccountBalance() throws Exception {
        Thread.sleep(1000);
        return 5000.0;
    }

    // Giả lập lấy lịch sử từ service khác (mất 1s)
    private static List<String> getTransactionHistory() throws Exception {
        Thread.sleep(1000);
        return List.of("TXN01: -100", "TXN02: +500");
    }

    private static String failTask() {
        throw new RuntimeException("Lỗi hệ thống nghiêm trọng!");
    }
}
