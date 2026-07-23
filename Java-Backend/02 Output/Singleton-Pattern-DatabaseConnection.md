# Output — Singleton Pattern: Database Connection (C)

## Mô tả
Đảm bảo chỉ có **một instance duy nhất** của `DatabaseConnection` trong toàn bộ ứng dụng, kể cả khi nhiều thread cùng gọi `getInstance()`.

## Cơ chế
- `private constructor` → ngăn `new` từ bên ngoài
- `private static volatile` instance → đảm bảo visibility giữa các thread
- **Double-Checked Locking**: kiểm tra null → `synchronized` → kiểm tra null lần 2 → tạo instance

## Code hoàn chỉnh

```java
public class SingletonPractice {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                DatabaseConnection conn = DatabaseConnection.getInstance();
                System.out.println(Thread.currentThread().getName() + " got: " + conn);
            }).start();
        }
    }
}

class DatabaseConnection {
    private static volatile DatabaseConnection databaseConnection;

    private DatabaseConnection() {}

    public static DatabaseConnection getInstance() {
        if (databaseConnection == null) {
            synchronized (DatabaseConnection.class) {
                if (databaseConnection == null) {
                    databaseConnection = new DatabaseConnection();
                }
            }
        }
        return databaseConnection;
    }
}
```

## Bài học rút ra
- `volatile` + double-checked locking = thread-safe mà không lock mỗi lần gọi.
- Qua Spring (Giai đoạn 2), IoC container quản lý singleton — gần như không tự viết tay nữa.
