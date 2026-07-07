// ExceptionHandling.java  (C) — Checked vs Unchecked
// Mục tiêu: CHẠM cái "ép" của compiler, và thấy vì sao checked hợp với lỗi ngoài-tầm-kiểm-soát.
//
// Chạy (trong 01 Practice/):
//   javac ExceptionHandling.java && java ExceptionHandling
//
// Làm lần lượt 3 thí nghiệm. Chỉ điền vào các chỗ // TODO.

public class ExceptionHandling {

    public static void main(String[] args) {
        Bank bank = new Bank();

        // ── Thí nghiệm 1: cảm nhận "ép" của compiler ──────────────────────
        // TODO(1): gọi bank.credit("ACC-2", 100);
        // Chạy `javac` — ĐỌC lỗi tiếng Anh (gợi ý keyword: "unreported exception ...
        // must be caught
        // or declared"). Vì credit() throws một CHECKED exception, compiler không cho
        // bỏ trống.
        // Sau khi đọc xong, comment nó lại rồi làm Thí nghiệm 2.
        // bank.credit("ACC 2", 100);

        // ── Thí nghiệm 2: chuyển tiền có xử lý lỗi (đúng cách) ─────────────
        // TODO(2): gọi bank.transfer("ACC-1", "ACC-2", 100) trong try/catch.
        // transfer() nội bộ debit trước rồi credit sau; credit có thể ném
        // BankTransferException.
        // -> phần bù trạng thái (không để "trừ mà chưa cộng") bạn sẽ viết trong
        // transfer() ở TODO(3).
        try {
            bank.transfer("ACC-1", "ACC-2", 100);
        } catch (Exception e) {
            bank.debit("ACC-1", 100);
        }

        // ── Thí nghiệm 3: unchecked KHÔNG bị ép ───────────────────────────
        // Dòng dưới ném IllegalArgumentException (unchecked) khi amount âm — KHÔNG cần
        // try/catch vẫn compile.
        bank.debit("ACC-1", -50); // để nguyên, chạy thử, quan sát nó nổ lúc RUNTIME chứ không phải compile
    }
}

// ── Checked exception: extends Exception ──────────────────────────────────
class BankTransferException extends Exception {
    public BankTransferException(String message) {
        super(message);
    }
}

class Bank {
    // Gọi sang "core-banking bên kia" — ngoài tầm kiểm soát, có thể chết giữa
    // chừng.
    // Vì throws một checked exception, MỌI nơi gọi credit() buộc phải catch hoặc
    // throws.
    public void credit(String account, double amount) throws BankTransferException {
        boolean networkDown = account.endsWith("2"); // giả lập: tài khoản này làm mạng "chết"
        if (networkDown) {
            throw new BankTransferException("Core-banking không phản hồi khi credit " + account);
        }
        System.out.println("  + Cộng " + amount + " vào " + account);
    }

    // Trừ tiền phía mình — thao tác nội bộ.
    public void debit(String account, double amount) {
        if (amount < 0) {
            // unchecked: lỗi do input sai -> đây là BUG phía caller, sửa code chứ không bọc
            // catch
            throw new IllegalArgumentException("amount phải > 0, nhận: " + amount);
        }
        System.out.println("  - Trừ " + amount + " từ " + account);
    }

    // Chuyển tiền: debit rồi credit. credit ở ngoài tầm kiểm soát -> phải phòng
    // thất bại.
    public void transfer(String from, String to, double amount) throws BankTransferException {
        debit(from, amount);
        // TODO(3): gọi credit(to, amount). Nếu nó ném BankTransferException, KHÔNG được
        // để
        // "đã trừ mà chưa cộng". Hãy bù lại (ví dụ: credit hoàn tiền về `from`,
        // in ra "ROLLBACK ..."), rồi ném lại lỗi để tầng trên biết giao dịch hỏng.
        // Gợi ý cấu trúc:
        // try { credit(to, amount); }
        // catch (BankTransferException e) { /* hoàn tiền về from + in log + throw lại
        // */ }

    }
}
