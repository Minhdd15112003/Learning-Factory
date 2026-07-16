import java.util.concurrent.*;

/**
 * Bài tập thực hành: ThreadPoolExecutor và RejectedExecutionHandler.
 */
public class ThreadPoolThrottlingPractice {

    public static void main(String[] args) {
        System.out.println("=== 1. TEST VỚI ABORT_POLICY ===");
        runWithPolicy(new ThreadPoolExecutor.AbortPolicy());

        System.out.println("\n=== 2. TEST VỚI CALLER_RUNS_POLICY ===");
        runWithPolicy(new ThreadPoolExecutor.CallerRunsPolicy());
    }

    private static void runWithPolicy(RejectedExecutionHandler handler) {
        // TODO: Tạo ThreadPoolExecutor theo đúng thông số đề bài yêu cầu
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,                                  // corePoolSize
            4,                                  // maximumPoolSize
            60,                                 // keepAliveTime
            TimeUnit.SECONDS,                   // Đơn vị thời gian cho keepAliveTime
            new LinkedBlockingQueue<>(5),       // workQueue với dung lượng tối đa 5
            handler                             // Handler xử lý khi overload
        );

        try {
            // Chúng ta submit 12 tasks cùng lúc.
            // Mỗi task chạy mất 200ms.
            // Sức chứa tối đa = 4 threads + 5 slot queue = 9 tasks cùng lúc.
            // Task thứ 10, 11, 12 sẽ kích hoạt RejectedExecutionHandler!
            for (int i = 1; i <= 12; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("Task " + taskId + " bắt đầu chạy trên " + threadName);
                    try {
                        Thread.sleep(200); // Giả lập việc chạy mất 200ms
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Task " + taskId + " hoàn thành trên " + threadName);
                });
            }
        } catch (RejectedExecutionException e) {
            System.err.println("LỖI: Bị từ chối chạy task! " + e.getMessage());
        } finally {
            executor.shutdown();
            try {
                // Đợi các task hoàn thành trước khi chuyển sang test tiếp theo
                executor.awaitTermination(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}