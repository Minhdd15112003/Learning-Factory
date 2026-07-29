// (C) Claudian - Java-Backend Learning Vault
// Bài tập thực hành: Tactical DDD (Aggregate, Entity, Value Object) & Hexagonal Architecture (Ports & Adapters)
// Dự án: commerce-fulfillment-system (E-commerce Order & Fulfillment Backend)

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TacticalAndHexagonalPractice {

    // =========================================================================
    // PHẦN 1: TẦNG DOMAIN (CORE JAVA THUẦN - KHÔNG PHỤ THUỘC SPRING/JPA)
    // =========================================================================

    /**
     * TODO 1: Viết Value Object `Money` bằng Java Record.
     * Yêu cầu:
     * - Có 2 trường: `BigDecimal amount` và `String currency`.
     * - Trong compact constructor của record, kiểm tra (validate):
     * + `amount` không được null và không được nhỏ hơn 0 (chỉ nhận >= 0). Nếu vi
     * phạm ném IllegalArgumentException.
     * + `currency` không được null và không được rỗng. Nếu vi phạm ném
     * IllegalArgumentException.
     * - Viết thêm method: `public Money add(Money other)` để cộng 2 số tiền.
     * (Lưu ý: Nếu `other.currency` khác `this.currency` thì ném
     * IllegalArgumentException.
     * Vì Value Object là bất biến/immutable, method add phải return về một instance
     * `Money` MỚI).
     */
    public record Money(BigDecimal amount, String currency) {
        public Money {
            // TODO 1.1: Viết logic validate cho amount và currency ở đây
            if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("amount must be >= 0");
            }
            if (currency == null || currency.isBlank()) {
                throw new IllegalArgumentException("currency cannot be null or blank");
            }
        }

        public Money add(Money other) {
            // TODO 1.2: Viết logic kiểm tra cùng tiền tệ và trả về đối tượng Money mới
            if (other == null) {
                throw new IllegalArgumentException("other cannot be null");
            }
            if (!this.currency.equals(other.currency)) {
                throw new IllegalArgumentException("currency must match");
            }

            BigDecimal total = this.amount.add(other.amount);

            return new Money(total, this.currency);
        }
    }

    /**
     * TODO 2: Viết Entity con `OrderItem` (thuộc về Aggregate `Order`).
     * Yêu cầu:
     * - Đây là Entity nên phải có ID (ví dụ: `String itemId`) để định danh.
     * - Các thuộc tính khác: `String productId`, `int quantity`, `Money
     * pricePerUnit`.
     * - Viết method `public Money calculateSubtotal()` trả về tổng tiền của item
     * này (`pricePerUnit * quantity`).
     */
    public static class OrderItem {
        private final String itemId;
        private final String productId;
        private int quantity;
        private final Money pricePerUnit;

        public OrderItem(String itemId, String productId, int quantity, Money pricePerUnit) {
            this.itemId = Objects.requireNonNull(itemId, "itemId cannot be null");
            this.productId = Objects.requireNonNull(productId, "productId cannot be null");
            if (quantity <= 0)
                throw new IllegalArgumentException("Quantity must be > 0");
            this.quantity = quantity;
            this.pricePerUnit = Objects.requireNonNull(pricePerUnit, "pricePerUnit cannot be null");
        }

        public String getItemId() {
            return itemId;
        }

        public String getProductId() {
            return productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public Money getPricePerUnit() {
            return pricePerUnit;
        }

        public Money calculateSubtotal() {
            // TODO 2.1: Tính subtotal = pricePerUnit.amount() * quantity, rồi trả về Money
            // mới cùng tiền tệ

            BigDecimal subtotal = pricePerUnit.amount().multiply(BigDecimal.valueOf(quantity));
            return new Money(subtotal, pricePerUnit.currency());
        }
    }

    /**
     * TODO 3: Viết Aggregate Root `Order`.
     * Yêu cầu:
     * - `Order` là gốc (Root) kiểm soát toàn bộ vòng đời và sự bất biến của các
     * `OrderItem` bên trong.
     * - Bên ngoài KHÔNG ĐƯỢC phép truy cập trực tiếp danh sách `items` để `add()`
     * hoặc `remove()`.
     * - Viết method `public void addItem(String itemId, String productId, int
     * quantity, Money pricePerUnit)`:
     * + Tạo một `OrderItem` mới và thêm vào danh sách nội bộ `items`.
     * + Tự động cập nhật lại tổng tiền `totalAmount` của đơn hàng sau khi thêm.
     * - Viết method `public List<OrderItem> getItems()`:
     * + Trả về một danh sách BẤT BIẾN (thông qua
     * `Collections.unmodifiableList(...)`) để bảo vệ dữ liệu bên trong khỏi bị sửa
     * từ bên ngoài.
     */
    public static class Order {
        private final String orderId;
        private final List<OrderItem> items = new ArrayList<>();
        private Money totalAmount;

        public Order(String orderId, String currency) {
            this.orderId = Objects.requireNonNull(orderId, "orderId cannot be null");
            this.totalAmount = new Money(BigDecimal.ZERO, currency);
        }

        public String getOrderId() {
            return orderId;
        }

        public Money getTotalAmount() {
            return totalAmount;
        }

        public void addItem(String itemId, String productId, int quantity, Money pricePerUnit) {
            // TODO 3.1: Kiểm tra xem pricePerUnit có cùng currency với totalAmount không
            // TODO 3.2: Tạo OrderItem mới và thêm vào danh sách `items`
            // TODO 3.3: Cộng dồn tiền vào `totalAmount` thông qua method `totalAmount =
            // totalAmount.add(item.calculateSubtotal())`
        }

        public List<OrderItem> getItems() {
            // TODO 3.4: Trả về unmodifiable list để bảo vệ encapsulation của Aggregate Root
            return null; // Thay bằng code thật
        }
    }

    /**
     * TODO 4: Định nghĩa Outbound Port (Interface) ở tầng Domain.
     * Yêu cầu:
     * - Khai báo interface `OrderRepositoryPort` với 2 method:
     * + `void save(Order order)`
     * + `Order findById(String orderId)`
     * - Nhắc lại: Tầng Domain tuyệt đối không chứa annotation của Spring/JPA, chỉ
     * dùng Java thuần.
     */
    public interface OrderRepositoryPort {
        // TODO 4.1: Khai báo 2 method ở đây
        void save(Order order);

        Order findById(String orderId);
    }

    // =========================================================================
    // PHẦN 2: TẦNG APPLICATION (USE CASE ĐIỀU PHỐI)
    // =========================================================================

    /**
     * TODO 5: Viết Use Case `PlaceOrderUseCase` ở tầng Application.
     * Yêu cầu:
     * - Tầng Application điều phối luồng nghiệp vụ thông qua Port interface
     * (`OrderRepositoryPort`).
     * - Trong constructor, inject `OrderRepositoryPort` vào.
     * - Viết method `public void execute(String orderId, String productId, int
     * quantity, Money price)`:
     * + Bước 1: Khởi tạo đối tượng `Order` mới (Aggregate Root).
     * + Bước 2: Gọi method `addItem(...)` của `Order` để thêm sản phẩm.
     * + Bước 3: Gọi `orderRepositoryPort.save(order)` để lưu đơn hàng thông qua
     * Port.
     */
    public static class PlaceOrderUseCase {
        private final OrderRepositoryPort orderRepositoryPort;

        public PlaceOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
            this.orderRepositoryPort = Objects.requireNonNull(orderRepositoryPort);
        }

        public void execute(String orderId, String productId, int quantity, Money price) {
            // TODO 5.1: Thực hiện 3 bước điều phối ở đây
        }
    }

    // =========================================================================
    // PHẦN 3: KIỂM THỬ TẠI MAIN (MÔ PHỎNG RUNTIME CỦA SPRING IOC CONTAINER)
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("=== BẮT ĐẦU KIỂM THỬ TACTICAL DDD & HEXAGONAL ARCHITECTURE ===");

        // 1. Giả lập một Adapter ở tầng Infrastructure (dùng bộ nhớ tạm RAM giả lập DB)
        OrderRepositoryPort fakeAdapter = new OrderRepositoryPort() {
            private final List<Order> db = new ArrayList<>();

            @Override
            public void save(Order order) {
                db.add(order);
                System.out.println("-> [Infrastructure Adapter] Đã lưu Order [" + order.getOrderId() +
                        "] với tổng tiền: " + order.getTotalAmount().amount() + " "
                        + order.getTotalAmount().currency());
            }

            @Override
            public Order findById(String orderId) {
                return db.stream().filter(o -> o.getOrderId().equals(orderId)).findFirst().orElse(null);
            }
        };

        // 2. Giả lập Spring IoC Container tiêm (inject) Adapter vào Use Case ở tầng
        // Application
        PlaceOrderUseCase placeOrderUseCase = new PlaceOrderUseCase(fakeAdapter);

        // 3. Thực thi Use Case từ phía người dùng
        Money price = new Money(new BigDecimal("150.00"), "USD");
        placeOrderUseCase.execute("ORD-2026-001", "PROD-MACBOOK", 2, price);

        // 4. Kiểm chứng bất biến của Aggregate Root
        Order savedOrder = fakeAdapter.findById("ORD-2026-001");
        if (savedOrder != null) {
            System.out.println("-> Kiểm tra tổng tiền đơn hàng: " + savedOrder.getTotalAmount().amount() + " "
                    + savedOrder.getTotalAmount().currency());
            try {
                // Thử phá vỡ encapsulation bằng cách xóa item trực tiếp (phải bị lỗi
                // UnsupportedOperationException)
                savedOrder.getItems().clear();
                System.out.println("-> [LỖI] Danh sách items không được bảo vệ!");
            } catch (UnsupportedOperationException e) {
                System.out.println("-> [THÀNH CÔNG] Danh sách items đã được bảo vệ bất biến (Encapsulation OK)!");
            }
        }
    }
}
