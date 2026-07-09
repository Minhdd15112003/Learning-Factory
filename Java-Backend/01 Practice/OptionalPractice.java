// OptionalPractice.java (C)
// Thực hành: Optional — tránh NullPointerException, ép xử lý trường hợp rỗng.
//
// Hướng dẫn:
//   javac OptionalPractice.java && java OptionalPractice
//
// Hoàn thành các TODO.

import java.util.Optional;

public class OptionalPractice {

    // Giả lập database tài khoản ngân hàng
    record Account(String id, String ownerName, Double balance) {}

    static Account[] accounts = {
        new Account("ACC-001", "Nguyen Van A", 5_000_000.0),
        new Account("ACC-002", "Tran Thi B", 12_000_000.0),
        new Account("ACC-003", "Le Van C", 0.0),
    };

    // Tìm tài khoản theo ID — trả về Optional<Account>
    static Optional<Account> findById(String id) {
        for (Account acc : accounts) {
            if (acc.id().equals(id)) return Optional.of(acc);
        }
        return Optional.empty();
    }

    // === Bài 1: orElse — giá trị mặc định ===
    // Tìm account "ACC-999" (không tồn tại).
    // Lấy ownerName ra, không tìm thấy thì trả "Unknown".
    public static void bai1_orElse() {
        // TODO: Dùng findById + map + orElse để lấy ownerName, default "Unknown"
        // String name = findById("ACC-999")
        //     ...
        String name = findById("ACC-999").map(t -> t.toString()).orElse("Unknown");
        // TODO: In kết quả. Mong đợi: "Owner: Unknown"
        System.out.println("name: "+name);
    }

    // === Bài 2: map chuỗi ===
    // Tìm account "ACC-002".
    // Lấy balance, nhân 1.05 (lãi 5%), trả về kết quả.
    // Không tìm thấy thì trả 0.0.
    public static void bai2_mapChain() {
        // TODO: Dùng findById + map (lấy balance) + map (nhân 1.05) + orElse(0.0)
        Double result = findById("ACC-002").map(t -> t.balance * 1.5).orElse(0.0);
        //     ...

        // TODO: In kết quả. Mong đợi: "Balance + interest: 12600000.0"
        System.out.println("Balance + interest: "+ result);
    }

    // === Bài 3: ifPresent — side effect ===
    // Tìm account "ACC-001".
    // Nếu tìm thấy → in "Found: <ownerName> - <balance>"
    // Nếu không → không in gì cả.
    public static void bai3_ifPresent() {
        // TODO: Dùng findById + ifPresent
        findById("ACC-001").ifPresent(t -> System.out.println("Found: " + t.ownerName + "-" + t.balance));
        // Mong đợi: "Found: Nguyen Van A - 5000000.0"
    }

    // === Bài 4: orElseThrow — lỗi nghiêm trọng ===
    // Tìm account "ACC-999".
    // Không tìm thấy → ném RuntimeException("Account not found: ACC-999")
    // Bọc trong try-catch để chương trình không crash.
    public static void bai4_orElseThrow() {
        // TODO: Dùng findById + orElseThrow, bọc try-catch, in message của exception
        findById("ACC-999").orElseThrow(() -> new Error("Error: Account not found: ACC-999"));
        // Mong đợi: "Error: Account not found: ACC-999"
    }

    // === Bài 5: So sánh — Optional vs null check ===
    // Viết cùng một logic BẰNG HAI CÁCH:
    // Tìm account "ACC-003", nếu balance == 0 thì in "Empty account", ngược lại in balance.
    //
    // Cách 1: dùng null check truyền thống (findById trả Account, check null)
    // Cách 2: dùng Optional + filter + ifPresentOrElse (hoặc map + orElse)
    public static void bai5_compare() {
        // Cách 1: null check
        // TODO: Gọi findById, dùng .orElse(null) để lấy Account ra, rồi if/else

        System.out.println("---");

        // Cách 2: Optional pipeline
        // TODO: Gọi findById, dùng filter(acc -> acc.balance() > 0) + ifPresentOrElse(...)
        //       hoặc map + orElse
    }

    public static void main(String[] args) {
        System.out.println("=== Bài 1: orElse ===");
        bai1_orElse();

        System.out.println("\n=== Bài 2: map chuỗi ===");
        bai2_mapChain();

        System.out.println("\n=== Bài 3: ifPresent ===");
        bai3_ifPresent();

        System.out.println("\n=== Bài 4: orElseThrow ===");
        bai4_orElseThrow();

        System.out.println("\n=== Bài 5: Optional vs null check ===");
        bai5_compare();
    }
}
