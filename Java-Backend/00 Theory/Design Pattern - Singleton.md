---
status: Understood
tags: [java, design-pattern, concurrency, review]
sr-due: 2026-07-21
sr-interval: 4
sr-ease: 270
---

# Design Pattern - Singleton

[[Singleton]] là mẫu thiết kế đảm bảo một class chỉ có duy nhất một instance trong suốt vòng đời của ứng dụng và cung cấp một điểm truy cập toàn cục (global access point) đến instance đó.

Ý tưởng cốt lõi của Singleton gói gọn trong **2 điều**:

1. **Duy nhất:** Đảm bảo một lớp (Class) chỉ có đúng **một đối tượng (Instance) duy nhất** được tạo ra trong suốt vòng đời của ứng dụng.
2. **Toàn cục:** Cung cấp một **điểm truy cập chung duy nhất** (Global Access Point) cho mọi nơi trong hệ thống muốn sử dụng đối tượng đó.

**Mục đích chính:** Tiết kiệm bộ nhớ và tránh xung đột khi quản lý các tài nguyên dùng chung (ví dụ: Kết nối cơ sở dữ liệu, File cấu hình hệ thống, Bộ ghi nhật ký - Logger).

## 1. Cơ chế hoạt động (Ba thành phần chính)

1. **Private Constructor**: Ngăn không cho code bên ngoài tự do khởi tạo đối tượng mới bằng từ khóa `new`.
2. **Private Static Instance & Static Method**: Biến static chứa instance duy nhất. Method static (thường tên là `getInstance()`) dùng để truy xuất instance này từ bên ngoài mà không cần tạo object trước.
3. **Double-Checked Locking**: Cơ chế kiểm tra an toàn đa luồng hiệu năng cao:
   - Kiểm tra lần 1 (ngoài lock): Nếu instance đã tồn tại, trả về ngay lập tức để tránh tranh chấp lock không cần thiết (performance).
   - Kiểm tra lần 2 (trong lock): Dành cho lượt khởi tạo đầu tiên, đảm bảo chỉ duy nhất một luồng có thể tạo đối tượng.

## 2. Code chuẩn hóa (Thread-safe với Double-Checked Locking)

```java
public class DatabaseConnection {
    // volatile ngăn compiler đảo thứ tự thực thi lệnh (instruction reordering)
    private static volatile DatabaseConnection instance;

    private DatabaseConnection() {}

    public static DatabaseConnection getInstance() {
        if (instance == null) { // Check 1
            synchronized (DatabaseConnection.class) {
                if (instance == null) { // Check 2
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
}
```

## 3. Vai trò của từ khóa `volatile`

Khi chạy `new DatabaseConnection()`, CPU thực hiện 3 bước:

1. Cấp phát vùng nhớ cho đối tượng.
2. Gán địa chỉ vùng nhớ cho biến `instance` (lúc này `instance != null`).
3. Chạy hàm khởi tạo (constructor).

Nếu không có `volatile`, CPU có thể tối ưu hóa bằng cách đảo bước 2 lên trước bước 3 (reordering). Một luồng khác nhảy vào gọi `getInstance()`, kiểm tra lần 1 thấy `instance != null` nên lấy ra sử dụng luôn, nhưng lúc này object thực tế chưa chạy xong constructor (bước 3 chưa hoàn thành) → Gây lỗi dữ liệu/lỗi runtime.
`volatile` ngăn chặn việc đảo thứ tự này (nhờ cơ chế memory barrier).

## 4. Bill Pugh Singleton (Static Inner Class)

Ủy thác thread-safety cho JVM class loader thay vì tự xử lý bằng `synchronized`/`volatile`.

```java
public class DatabaseConnection {
    private DatabaseConnection() {}

    private static class Holder {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return Holder.INSTANCE;
    }
}
```

- JVM chỉ load class `Holder` khi `getInstance()` được gọi lần đầu → **lazy initialization**.
- JVM đảm bảo quá trình load class là **thread-safe** ở cấp JVM → không cần `synchronized`/`volatile`.

## 5. Enum Singleton (cách đơn giản nhất)

```java
public enum DatabaseConnection {
    INSTANCE;

    public void query(String sql) { /* ... */ }
}
// Sử dụng: DatabaseConnection.INSTANCE.query("SELECT ...");
```

- JVM đảm bảo mỗi hằng số enum chỉ có **đúng một instance** và khởi tạo thread-safe.
- Chống được **serialization attack** và **reflection attack** — hai thứ mà Double-Checked Locking và Bill Pugh không chặn được nếu không xử lý thêm.
- Nhược điểm: **eager** (tạo ngay khi class load, không lazy).

## 6. So sánh 3 cách triển khai

| Cách                       | Thread-safe      | Lazy          | Chống serialize/reflect | Độ phức tạp   |
| -------------------------- | ---------------- | ------------- | ----------------------- | ------------- |
| **Enum**                   | JVM đảm bảo      | Không (eager) | Có                      | Đơn giản nhất |
| **Bill Pugh**              | JVM class loader | Có            | Không                   | Đơn giản      |
| **Double-Checked Locking** | Tự xử lý         | Có            | Không                   | Phức tạp nhất |
