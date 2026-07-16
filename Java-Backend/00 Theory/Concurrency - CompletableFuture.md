---
status: Understood
tags: [java, concurrency, completablefuture, review]
sr-due: 2026-07-20
sr-interval: 4
sr-ease: 270
---

# Concurrency — CompletableFuture (C)

> Note do mình (Claudian) soạn sau khi bạn giải thích được pipeline, xử lý lỗi, và chạy song song bằng CompletableFuture.

## 1. Vấn đề với Future

`Future` là "biên lai" thụ động — phải gọi `get()` (blocking) để lấy kết quả. Không có cách nào xây pipeline: "khi xong bước 1 → tự động chạy bước 2 → tự động chạy bước 3" mà không chặn luồng chính.

## 2. CompletableFuture — Pipeline bất đồng bộ

`CompletableFuture` cho phép xây pipeline xử lý kết quả ngay khi task xong, **không cần chặn luồng chính**. Cơ chế: dựa trên **callback** — khi thread trong pool chạy xong task, chính thread đó (hoặc thread khác từ pool) tự động kích hoạt bước tiếp theo.

### Các hàm chính

| Hàm | Chức năng | Tương đương |
|---|---|---|
| `supplyAsync(() -> ...)` | Khởi tạo task bất đồng bộ, trả kết quả | Bước đầu pipeline |
| `thenApply(fn)` | Biến đổi kết quả (A → B) | `map()` trong Stream |
| `thenAccept(fn)` | Tiêu thụ kết quả (side-effect, in ra) | `forEach()` trong Stream |
| `exceptionally(fn)` | Bắt lỗi, trả giá trị dự phòng | `catch` trong try-catch |
| `thenCombine(other, fn)` | Gộp kết quả 2 future song song | Không có tương đương Stream |
| `join()` | Chờ pipeline hoàn thành (blocking) | `get()` nhưng không ném checked exception |

## 3. Xử lý lỗi — `exceptionally()`

Khi một bước ném exception → các bước `thenApply`/`thenAccept` phía sau bị bỏ qua → lỗi lan truyền dọc pipeline → `.exceptionally()` bắt lỗi và trả về giá trị dự phòng → pipeline được "cứu" → các bước sau `exceptionally()` chạy bình thường.

**Lưu ý kiểu dữ liệu:** `.exceptionally()` phải trả cùng kiểu với stage ngay trước nó.

## 4. Chạy song song — `thenCombine()`

```java
var f1 = CompletableFuture.supplyAsync(() -> checkBalance("ACC-001"));    // 1s
var f2 = CompletableFuture.supplyAsync(() -> getAccountName("ACC-001"));  // 1s
f1.thenCombine(f2, (balance, name) -> "Chủ TK: " + name + ", Số dư: " + balance)
  .thenAccept(System.out::println)
  .join();
// Tổng: ~1s (không phải 2s) — hai task chạy đồng thời
```

## 5. Phân biệt với ThreadPoolExecutor

- **ThreadPoolExecutor** — "Ai/Bao nhiêu": quản lý tài nguyên (thread, queue, chịu tải).
- **CompletableFuture** — "Thế nào/Khi nào": điều phối pipeline bất đồng bộ.
- Dùng chung: `supplyAsync(() -> ..., executor)` truyền pool tùy chỉnh vào thay cho `ForkJoinPool.commonPool()`.

## Liên quan
[[Concurrency - ExecutorService và Future]] · [[Concurrency - ThreadPoolExecutor và RejectedExecutionHandler]] · [[Stream API]]
