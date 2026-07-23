---
status: Understood
tags: [java, design-pattern, review]
sr-due: 2026-07-25
sr-interval: 2
sr-ease: 250
---

# Design Pattern - Adapter (C)

> Note do mình (Claudian) soạn sau khi bạn giải thích được cơ chế bằng lời của mình.

## Vấn đề giải quyết
Tích hợp thư viện / service bên ngoài có **interface không khớp** với code nội bộ. Không được sửa SDK bên ngoài (không có source, update sẽ mất). Sửa toàn bộ code nội bộ thì rải khắp nơi, dễ sót, và đổi provider sau phải sửa lại.

## Cơ chế
Tạo một **adapter class** trung gian:
1. **Implements interface nội bộ** (ví dụ `PaymentGateway`)
2. **Giữ object của class bên ngoài** qua constructor (ví dụ `VnpayClient`)
3. Trong method nội bộ (`charge()`), adapter làm 3 việc:
   - **Chuyển đổi tham số** cho khớp signature bên ngoài (ví dụ `double` → `long`)
   - **Bổ sung tham số thiếu** từ field đã inject qua constructor (ví dụ `merchantId`)
   - **Gọi method của object bên ngoài** (`vnpayClient.vnpayProcessPayment(...)`)

```java
public class VnpayAdapter implements PaymentGateway {
    private final VnpayClient vnpayClient;
    private final String merchantId;

    public VnpayAdapter(VnpayClient vnpayClient, String merchantId) {
        this.vnpayClient = vnpayClient;
        this.merchantId = merchantId;
    }

    @Override
    public void charge(String accountId, double amount) {
        // Chuyển đổi tham số + gọi sang SDK
        vnpayClient.vnpayProcessPayment(merchantId, accountId, (long) amount);
    }
}
```

## Kết quả
- Code nội bộ chỉ thấy `PaymentGateway` — không biết VNPay tồn tại.
- Đổi sang MoMo → thêm `MomoAdapter implements PaymentGateway`, không sửa class cũ → **Open/Closed Principle**.

## So sánh với Strategy
| | Strategy | Adapter |
|---|---|---|
| Mục đích | Đổi **hành vi** lúc runtime | Đổi **giao diện** để hai bên không khớp nói chuyện được |
| Cả hai | Dựa trên interface để tách coupling, thỏa mãn Open/Closed |

## Domain fintech
Đụng liên tục khi tích hợp payment gateway (VNPay, MoMo, ZaloPay), KYC provider, core banking API — mỗi bên có SDK riêng, interface riêng.

## Liên quan
[[Design Pattern - Strategy]] · [[Interface vs Abstract Class]] · [[Design Pattern - Decorator]]
