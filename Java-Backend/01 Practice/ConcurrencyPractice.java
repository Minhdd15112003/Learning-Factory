// ConcurrencyPractice.java (C)
// Thực hành: Concurrency cơ bản — Thread, Runnable, Race Condition và synchronized.
//
// Hướng dẫn:
//   javac ConcurrencyPractice.java && java ConcurrencyPractice
//
// Hoàn thành các TODO.

public class ConcurrencyPractice {

    // Lớp giữ biến đếm dùng chung (Shared State)
    static class Counter {
        private int count = 0;

        // Tăng không đồng bộ (bị Race Condition)
        public void increment() {
            count++;
        }

        // Tăng đồng bộ (an toàn đa luồng)
        // TODO 3.1: Thêm từ khóa gì vào khai báo phương thức để chỉ cho phép
        // một luồng chạy hàm này tại một thời điểm?
        public synchronized void incrementSynchronized() {
            count++;
        }

        public int getCount() {
            return count;
        }

        public void reset() {
            count = 0;
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        int iterations = 10000;

        System.out.println("=== Phần 1: Chạy không đồng bộ (Race Condition) ===");

        // Định nghĩa công việc tăng 10.000 lần cho luồng
        Runnable taskUnsafe = () -> {
            for (int i = 0; i < iterations; i++) {
                counter.increment();
            }
        };

        // TODO 1.1: Tạo 2 Thread t1 và t2 nhận taskUnsafe ở trên
        // Thread t1 = new Thread(...);
        // Thread t2 = new Thread(...);
        Thread t1 = new Thread(taskUnsafe);
        Thread t2 = new Thread(taskUnsafe);

        // TODO 1.2: Kích hoạt (chạy) cả hai luồng t1 và t2
        // ...
        t1.start();
        t2.start();
        try {

            // Wait for both threads to finish
            // TODO 1.3: Dùng phương thức nào của Thread để luồng main (cha)
            // đợi luồng t1 và t2 hoàn thành trước khi chạy tiếp?
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted!");
        }

        System.out.println("Kết quả mong đợi: " + (iterations * 2));
        System.out.println("Kết quả thực tế:  " + counter.getCount());
        System.out.println("---");

        System.out.println("\n=== Phần 2: Chạy đồng bộ (synchronized) ===");
        counter.reset();

        // Định nghĩa công việc dùng hàm tăng đồng bộ
        Runnable taskSafe = () -> {
            for (int i = 0; i < iterations; i++) {
                counter.incrementSynchronized();
            }
        };

        // TODO 2.1: Tạo và chạy 2 Thread t3 và t4 sử dụng taskSafe
        Thread t3 = new Thread(taskSafe);
        Thread t4 = new Thread(taskSafe);

        try {

            // TODO 2.2: Đợi t3 và t4 hoàn thành
            t3.start();
            t4.start();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted!");
        }

        System.out.println("Kết quả mong đợi: " + (iterations * 2));
        System.out.println("Kết quả thực tế:  " + counter.getCount());
    }
}
