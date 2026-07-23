---
status: Understood
tags: [java, modern, review]
sr-due: 2026-08-03
sr-interval: 11
sr-ease: 270
---

# Java Modern Features — `var` và `record` (C)

> Note do mình (Claudian) soạn sau khi bạn giải thích được cơ chế bằng lời của mình.

## 1. `var` — Local Variable Type Inference (Java 10+)

### Cơ chế
`var` cho phép compiler **tự suy ra kiểu** của biến địa phương từ vế phải của phép gán. Biến vẫn là **static type** cố định từ compile-time — không phải dynamic typing như JS/Python.

```java
var name = "Minh";       // compiler chốt: String name
var list = new ArrayList<String>(); // compiler chốt: ArrayList<String> (không phải List<String>!)

name = 123;              // ❌ compile error — String không nhận int
list = new LinkedList<>(); // ❌ compile error — ArrayList ≠ LinkedList
```

### Điểm cần cẩn thận
`var` suy ra kiểu **cụ thể nhất** ở vế phải, không phải interface. Nếu muốn giữ tính linh hoạt, khai báo tường minh:
```java
List<String> list = new ArrayList<>();  // ✅ static type là interface List
```

---

## 2. `record` — Immutable Data Carrier (Java 16+)

### Vấn đề giải quyết
Trước record, mỗi DTO/data class phải viết thủ công: field, constructor, getter, `equals()`, `hashCode()`, `toString()` — rất dài dòng (boilerplate).

### Cơ chế
Khai báo `record` → compiler tự sinh ra toàn bộ:
- **Field**: `private final` → ẩn (private) + khóa chặt sau khởi tạo (final) → **bất biến hoàn toàn**
- Constructor, getter (tên = tên field, không có `get-`), `equals()`, `hashCode()`, `toString()`

```java
public record TransactionDTO(String txnId, double amount, String date) {}

// Dùng:
var txn = new TransactionDTO("TXN-001", 500_000, "2026-07-14");
txn.amount();       // ✅ getter: tên field trực tiếp
txn.amount = 999;   // ❌ private → không truy cập trực tiếp
// this.amount = 999 // ❌ final → không gán lại dù bên trong class
```

### Vì sao record an toàn cho banking DTO
Dữ liệu giao dịch (mã, số tiền, ngày) một khi được tạo ra, **không tầng nào có thể vô tình thay đổi** trong quá trình xử lý. Dùng class thường có setter → tầng service nào cũng có nguy cơ sửa số tiền lúc đang truyền tay.

### Giới hạn
- Không thể kế thừa record khác (record ngầm kế thừa `java.lang.Record`).
- Chỉ dùng cho dữ liệu bất biến — không phù hợp cho entity có vòng đời thay đổi (dùng class thường + JPA thay thế).

## Liên quan
[[Stream API]] · [[Optional]] · [[List - ArrayList vs LinkedList]] (thực hành tổng hợp tiếp theo)
