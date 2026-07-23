/**
 * Thực hành Decorator Pattern — Transaction Processing Pipeline (C)
 *
 * Luật chơi:
 * - KHÔNG sửa TransactionService interface và CoreTransactionService.
 * - Chỉ viết code ở các vị trí TODO.
 */
public class DecoratorPractice {

    // ═══════════════════════════════════════════
    // DATA CLASS — KHÔNG SỬA
    // ═══════════════════════════════════════════
    static class Transaction {
        final String id;
        final double amount;

        Transaction(String id, double amount) {
            this.id = id;
            this.amount = amount;
        }
    }

    // ═══════════════════════════════════════════
    // INTERFACE — KHÔNG SỬA
    // ═══════════════════════════════════════════
    interface TransactionService {
        void execute(Transaction txn);
    }

    // ═══════════════════════════════════════════
    // CORE — KHÔNG SỬA
    // ═══════════════════════════════════════════
    static class CoreTransactionService implements TransactionService {
        @Override
        public void execute(Transaction txn) {
            System.out.println("[CORE] Executing transaction " + txn.id + ", amount: " + txn.amount);
        }
    }

    // ═══════════════════════════════════════════
    // TODO 1 + TODO 2: LoggingDecorator
    // ═══════════════════════════════════════════
    static class LoggingDecorator implements TransactionService {
        private final TransactionService wrapped;

        public LoggingDecorator(TransactionService wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public void execute(Transaction txn) {
            System.out.println("[LOG] Processing txn: " + txn.id);
            wrapped.execute(txn); // Gọi service bên trong
            System.out.println("[LOG] Done: " + txn.id);
        }
    }

    // ═══════════════════════════════════════════
    // TODO 3: ValidationDecorator
    // ═══════════════════════════════════════════
    static class ValidationDecorator implements TransactionService {
        private final TransactionService wrapped;

        public ValidationDecorator(TransactionService wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public void execute(Transaction txn) {
            if (txn.amount <= 0) {
                System.out.println("[VALIDATION] Rejected: " + txn.id + " — invalid amount");
                // Không gọi wrapped.execute(txn) ở đây để chặn luồng xử lý
            } else {
                System.out.println("[VALIDATION] Passed: " + txn.id);
                wrapped.execute(txn);
            }
        }
    }

    // ═══════════════════════════════════════════
    // TODO 4: AuditDecorator
    // ═══════════════════════════════════════════
    static class AuditDecorator implements TransactionService {
        private final TransactionService wrapped;

        public AuditDecorator(TransactionService wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public void execute(Transaction txn) {
            // Gọi wrapped xử lý trước, ghi audit log sau
            wrapped.execute(txn);
            System.out.println("[AUDIT] Transaction " + txn.id + " completed, amount: " + txn.amount);
        }
    }

    // ═══════════════════════════════════════════
    // TODO 5: main — lồng decorator và chạy
    // ═══════════════════════════════════════════
    public static void main(String[] args) {
        // 1. Khởi tạo core service
        TransactionService core = new CoreTransactionService();

        // 2. Lồng các decorator theo đúng thứ tự: Logging -> Validation -> Audit ->
        // Core
        TransactionService pipeline = new LoggingDecorator(
                new ValidationDecorator(
                        new AuditDecorator(core)));

        // 3. Chạy giao dịch hợp lệ (500,000)
        pipeline.execute(new Transaction("TXN-001", 500000));

        System.out.println("---");

        // 4. Chạy giao dịch không hợp lệ (-100)
        pipeline.execute(new Transaction("TXN-002", -100));
    }
}