
public class pratice {
  // ============================================================
  // MÔ PHỎNG: Order Bounded Context giao tiếp với Inventory,
  // Payment (Command/Sync) và Notification (Event/Async)
  // ============================================================

  // ------------------------------------------------------------
  // 1) COMMAND SIDE (Sync) — Order gọi trực tiếp, chờ kết quả,
  // quyết định flow tiếp theo dựa trên response.
  // => Đại diện cho quan hệ Order-Inventory, Order-Payment
  // ------------------------------------------------------------

  /**
   * Interface do Inventory Context (bên nhận) implement.
   * Order Context chỉ biết interface này (Anti-Corruption Layer /
   * Port trong Hexagonal Architecture) — không phụ thuộc trực tiếp
   * vào code nội bộ của Inventory.
   */
  public interface InventoryPort {
    // Trả về kết quả NGAY LẬP TỨC vì Order phải biết để quyết định
    // có tạo đơn hay không => đây là lý do bắt buộc phải Sync.
    ReservationResult reserveStock(String sku, int quantity);
  }

  public interface PaymentPort {
    // Tương tự: Order cần biết thanh toán có thành công không
    // để trả response cho client ngay trong cùng 1 HTTP request.
    PaymentResult charge(String orderId, Money amount);
  }

  // Value objects tối giản cho phần Command
  record ReservationResult(boolean success, String reservationId, String reason) {
  }

  record PaymentResult(boolean success, String transactionId, String reason) {
  }

  record Money(long amountInCents, String currency) {
  }

  // ------------------------------------------------------------
  // 2) EVENT SIDE (Async) — Order KHÔNG biết ai sẽ lắng nghe.
  // Order chỉ "publish" sự kiện đã xảy ra rồi đi tiếp,
  // không chờ, không quan tâm kết quả.
  // => Đại diện cho quan hệ Order-Notification
  // ------------------------------------------------------------

  /**
   * Domain Event — mô tả một sự kiện ĐÃ xảy ra trong quá khứ
   * (tên luôn ở thì quá khứ: OrderPlaced, không phải PlaceOrder).
   * Đây là điểm khác biệt cốt lõi so với Command (tên ở thì mệnh lệnh).
   */
  public record OrderPlacedEvent(
      String orderId,
      String customerId,
      Money totalAmount,
      java.time.Instant occurredAt) {
  }

  /**
   * Order Context chỉ phụ thuộc vào interface publish, không biết
   * và không cần biết Notification Context tồn tại.
   * Có thể là Kafka, RabbitMQ, hay đơn giản là in-memory event bus
   * ở giai đoạn Modular Monolith (Stage 2).
   */
  public interface EventPublisher {
    void publish(Object domainEvent);
  }

  // ------------------------------------------------------------
  // 3) ORDER SERVICE — nơi thể hiện rõ nhất sự khác biệt:
  // - Gọi Command (Inventory, Payment) => BLOCKING, ảnh hưởng
  // trực tiếp đến việc tạo đơn có thành công hay không.
  // - Publish Event (Notification) => FIRE-AND-FORGET, không
  // chặn luồng chính, không rollback nếu thất bại.
  // ------------------------------------------------------------

  public class OrderService {

    private final InventoryPort inventoryPort;
    private final PaymentPort paymentPort;
    private final EventPublisher eventPublisher;

    public OrderService(InventoryPort inventoryPort,
        PaymentPort paymentPort,
        EventPublisher eventPublisher) {
      this.inventoryPort = inventoryPort;
      this.paymentPort = paymentPort;
      this.eventPublisher = eventPublisher;
    }

    public OrderResult placeOrder(PlaceOrderCommand cmd) {

      // --- COMMAND #1: Inventory (Sync) ---
      // Nếu hết hàng => dừng NGAY, không đi tiếp.
      // Đây chính là lý do quan hệ này PHẢI là Command/Sync:
      // Order không thể "tạo đơn trước, kiểm tra kho sau".
      ReservationResult reservation = inventoryPort.reserveStock(cmd.sku(), cmd.quantity());

      if (!reservation.success()) {
        return OrderResult.failed("INVENTORY_UNAVAILABLE: " + reservation.reason());
      }

      // --- COMMAND #2: Payment (Sync) ---
      // Tương tự: nếu thanh toán thất bại, đơn coi như fail.
      // (Ở mô hình Saga phức tạp hơn, bước này có thể tách thành
      // một Event riêng — đây là điểm bạn nên tự nghiên cứu thêm.)
      PaymentResult payment = paymentPort.charge(cmd.orderId(), cmd.totalAmount());

      if (!payment.success()) {
        // Compensating action: phải nhả lại hàng đã reserve
        // vì bước Inventory đã thành công trước đó.
        // => Đây chính là mầm mống của khái niệm SAGA PATTERN.
        releaseReservedStock(reservation.reservationId());
        return OrderResult.failed("PAYMENT_FAILED: " + payment.reason());
      }

      // --- Tạo đơn hàng thành công ---
      Order order = Order.create(cmd, reservation, payment);

      // --- EVENT: Notification (Async) ---
      // Không blocking, không kiểm tra kết quả, không rollback
      // nếu gửi thất bại. Order Context "quên" sự kiện này ngay
      // sau khi publish — trách nhiệm xử lý thuộc về bên nghe.
      eventPublisher.publish(new OrderPlacedEvent(
          order.id(),
          order.customerId(),
          order.totalAmount(),
          java.time.Instant.now()));

      return OrderResult.success(order.id());
    }

    private void releaseReservedStock(String reservationId) {
      // gọi lại InventoryPort để hoàn kho — vẫn là Command/Sync
      // vì đây là compensating transaction, cần đảm bảo chắc chắn.
    }
  }

  // ------------------------------------------------------------
  // 4) Phía Notification Context — HOÀN TOÀN TÁCH BIỆT,
  // không được Order Context biết đến sự tồn tại.
  // Chỉ implement 1 "Event Listener/Consumer".
  // ------------------------------------------------------------

  public class NotificationEventListener {

    // Trong thực tế đây sẽ là @KafkaListener hoặc @EventListener (Spring)
    public void onOrderPlaced(OrderPlacedEvent event) {
      // gửi email/SMS xác nhận đơn hàng
      // Nếu bước này lỗi, KHÔNG ảnh hưởng gì đến Order Context
      // vì Order đã publish xong và đi tiếp từ lâu rồi.
      System.out.println("Sending confirmation for order " + event.orderId());
    }
  }

  // ------------------------------------------------------------
  // Supporting types (tối giản, chỉ để code compile được về mặt ý tưởng)
  // ------------------------------------------------------------

  record PlaceOrderCommand(String orderId, String sku, int quantity, Money totalAmount) {
  }

  record Order(String id, String customerId, Money totalAmount) {
    static Order create(PlaceOrderCommand cmd, ReservationResult r, PaymentResult p) {
      return new Order(cmd.orderId(), "customer-123", cmd.totalAmount());
    }
  }

  record OrderResult(boolean success, String orderId, String errorMessage) {
    static OrderResult success(String orderId) {
      return new OrderResult(true, orderId, null);
    }

    static OrderResult failed(String reason) {
      return new OrderResult(false, null, reason);
    }
  }
}
