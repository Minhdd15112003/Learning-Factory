// ModernJavaPractice.java
// Thực hành tổng hợp: var + record + Stream API + Optional
//
// Domain: hệ thống xử lý giao dịch ngân hàng.

import java.util.*;
import java.util.stream.Collectors;

public class ModernJavaPractice {

    // === 1. Dùng `record` làm DTO immutable
    record Transaction(
        String txnId,           // mã giao dịch
        String type,            // "DEPOSIT", "WITHDRAW", "TRANSFER"
        double amount,          // số tiền (>0)
        String status,          // "SUCCESS", "FAILED", "PENDING"
        String accountId        // ID tài khoản liên quan
    ) {}

    // === 2. Dữ liệu mẫu — giả lập một ngày giao dịch
    // Lưu ý: Không thể dùng `var` ở đây vì đây là thuộc tính của Class (instance field).
    List<Transaction> transactions = List.of(
        new Transaction("TXN-001", "DEPOSIT",  500_000.0, "SUCCESS", "ACC-001"),
        new Transaction("TXN-002", "WITHDRAW", 100_000.0, "SUCCESS", "ACC-002"),
        new Transaction("TXN-003", "TRANSFER", 200_000.0, "FAILED",  "ACC-001"), // chuyển tiền thất bại
        new Transaction("TXN-004", "WITHDRAW",  50_000.0, "PENDING", "ACC-003"),
        new Transaction("TXN-005", "TRANSFER", 150_000.0, "SUCCESS", "ACC-002"),
        new Transaction("TXN-006", "DEPOSIT",  300_000.0, "SUCCESS", "ACC-003"),
        new Transaction("TXN-007", "WITHDRAW", 500_000.0, "SUCCESS", "ACC-001")
    );

    // === Bài 1: Dùng `var` khởi tạo + phương thức trả về Optional<Transaction>
    // Tìm transaction theo ID.
    Optional<Transaction> findById(String id) {
        // TODO 1.2: Điền pipeline tìm transaction theo txnId
        return transactions.stream()
                .filter(t -> t.txnId().equals(id))
                .findFirst();
    }

    public void bai1() {
        System.out.println("=== Bài 1: Tìm transaction theo ID ===");
        // TODO 1.1: Dùng `var` để nhận Optional<Transaction>
        var result = findById("TXN-003");

        // Nếu tìm thấy, in "Found: <txnId> - <type> - <amount>"
        // Nếu không, in "Not found"
        result.ifPresentOrElse(
            t -> System.out.println("Found: " + t.txnId() + " - " + t.type() + " - " + t.amount()),
            () -> System.out.println("Not found")
        );
    }

    // === Bài 2: Stream API — filter + map + collect
    // Liệt kê tất cả giao dịch **WITHDRAW** thành công (SUCCESS).
    public void bai2() {
        System.out.println("\n=== Bài 2: Giao dịch rút tiền thành công ===");
        // Dùng `var` cho gọn thay vì List<String>
        var successfulWithdrawIds = transactions.stream()
            // TODO 2.1: Lọc type = "WITHDRAW" và status = "SUCCESS"
            .filter(t -> "WITHDRAW".equals(t.type()) && "SUCCESS".equals(t.status()))
            // TODO 2.2: Map lấy ra txnId
            .map(Transaction::txnId)
            // TODO 2.3: Collect thành List
            .collect(Collectors.toList());

        // In ra kết quả. Mong đợi: [TXN-002, TXN-007]
        System.out.println(successfulWithdrawIds);
    }

    // === Bài 3: Stream API — tổng tiền giao dịch thành công
    // Tính tổng số tiền của tất cả giao dịch **TRANSFER** thành công.
    public void bai3() {
        System.out.println("\n=== Bài 3: Tổng tiền chuyển khoản thành công ===");
        // Dùng `var` thay vì double
        var totalTransferAmount = transactions.stream()
            // TODO: Lọc TRANSFER thành công và map sang double để tính tổng
            .filter(t -> "TRANSFER".equals(t.type()) && "SUCCESS".equals(t.status()))
            .mapToDouble(Transaction::amount)
            .sum();

        // In kết quả. Mong đợi: 150000.0 (chỉ có TXN-005 thành công)
        System.out.println("Total: " + totalTransferAmount);
    }

    // === Bài 4: Stream API — groupingBy (nâng cao)
    // Nhóm các giao dịch theo trạng thái (status).
    public void bai4() {
        System.out.println("\n=== Bài 4: Nhóm giao dịch theo trạng thái ===");
        // TODO: Khai báo với `var` thay vì Map<String, List<Transaction>>
        var groups = transactions.stream()
            // TODO: Gom nhóm theo status
            .collect(Collectors.groupingBy(Transaction::status));

        // Duyệt và in ra số lượng giao dịch theo từng status
        // Định dạng mong muốn: "Status SUCCESS: 5 giao dịch" (thực tế dữ liệu mẫu có 5 SUCCESS)
        groups.forEach((status, list) -> {
            System.out.println("Status " + status + ": " + list.size() + " giao dịch");
        });
    }

    // === Bài 5: Kết hợp Optional + Stream
    // Tìm giao dịch PENDING đầu tiên, nếu có thì xử lý, nếu không tạo mặc định.
    public void bai5() {
        System.out.println("\n=== Bài 5: Xử lý giao dịch PENDING ===");
        // TODO 5.1: Tìm giao dịch PENDING đầu tiên bằng Stream API
        var pendingOpt = transactions.stream()
            .filter(t -> "PENDING".equals(t.status()))
            .findFirst();

        // TODO 5.2: Dùng ifPresentOrElse để xử lý rẽ nhánh
        pendingOpt.ifPresentOrElse(
            t -> System.out.println("Processing: " + t.txnId()),
            () -> {
                var defaultTxn = new Transaction("NO_PENDING", "INFO", 0.0, "DEFAULT", "N/A");
                System.out.println("No pending, default: " + defaultTxn.txnId());
            }
        );
    }

    public static void main(String[] args) {
        var practice = new ModernJavaPractice();

        practice.bai1();
        practice.bai2();
        practice.bai3();
        practice.bai4();
        practice.bai5();
    }
}