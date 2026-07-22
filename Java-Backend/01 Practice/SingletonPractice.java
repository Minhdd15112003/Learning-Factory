public class SingletonPractice {
    public static void main(String[] args) {
        // TODO: Chạy nhiều luồng để lấy instance
        // Mỗi luồng in ra địa chỉ bộ nhớ (hoặc toString()) của instance
        // Nếu các chuỗi in ra y hệt nhau, Singleton thành công

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                DatabaseConnection conn = DatabaseConnection.getInstance();
                System.out.println(Thread.currentThread().getName() + " got: " + conn);
            }).start();
        }
    }
}

class DatabaseConnection {
    // TODO 1: Khai báo biến instance (cần dùng volatile với Double-Checked Locking)
    private static volatile DatabaseConnection databaseConnection;

    private DatabaseConnection() {
        // Có thể thêm logic khởi tạo kết nối ở đây
    }

    // TODO 2: Khai báo constructor ngăn khởi tạo bên ngoài
    public static DatabaseConnection getInstance() {
        if (databaseConnection == null) {
            // TODO 3: Viết method getInstance() sử dụng Double-Checked Locking
            synchronized (DatabaseConnection.class) {
                if (databaseConnection == null) {
                    databaseConnection = new DatabaseConnection();
                }
            }
        }
        return databaseConnection;
    }
}
