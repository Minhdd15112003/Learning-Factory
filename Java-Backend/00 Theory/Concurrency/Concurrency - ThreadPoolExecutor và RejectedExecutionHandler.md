---
status: Understood
tags: [java, concurrency, threadpool, review]
sr-due: 2026-07-22
sr-interval: 4
sr-ease: 270
---

# Concurrency — ThreadPoolExecutor và RejectedExecutionHandler (C)

> Note do mình (Claudian) soạn sau khi bạn phân biệt được cơ chế từng policy và chọn đúng policy cho từng kịch bản.

## 1. Vấn đề với `newFixedThreadPool`

`Executors.newFixedThreadPool(n)` bên dưới tạo `ThreadPoolExecutor` với một `LinkedBlockingQueue` **không giới hạn** (unbounded). Submit 10.000 task vào pool 2 thread → 9.998 task nằm chờ trong queue → queue phình vô hạn trong RAM → nguy cơ **OutOfMemoryError**.

## 2. Giải pháp: Bounded Queue + ThreadPoolExecutor

Tự tạo `ThreadPoolExecutor` với queue có giới hạn:

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    2,                                  // corePoolSize: luôn giữ 2 thread
    4,                                  // maximumPoolSize: tối đa 4 thread
    60, TimeUnit.SECONDS,               // keepAliveTime: thread dôi dư rảnh 60s → huỷ
    new LinkedBlockingQueue<>(100),      // bounded queue: tối đa 100 task chờ
    handler                             // RejectedExecutionHandler
);
```

Sức chứa tối đa = `maxPoolSize` + `queueCapacity`. Khi cả hai đều đầy → task mới bị **từ chối** → `RejectedExecutionHandler` xử lý.

## 3. Bốn chiến lược RejectedExecutionHandler

| Policy | Hành vi | Khi nào dùng |
|---|---|---|
| **AbortPolicy** (mặc định) | Ném `RejectedExecutionException` | Giao dịch ngân hàng — cần biết để rollback |
| **CallerRunsPolicy** | Luồng gọi `submit()` tự chạy task đó | Throttling tự nhiên — hãm phanh luồng gửi |
| **DiscardPolicy** | Vứt task âm thầm, không báo lỗi | Task không quan trọng (log, email báo cáo) |
| **DiscardOldestPolicy** | Vứt task cũ nhất đầu queue, nhét task mới vào | Khi dữ liệu mới quan trọng hơn cũ |

## 4. CallerRunsPolicy — Cơ chế throttling

Khi pool từ chối → luồng chính (caller) phải tự chạy task → luồng chính bận → không thể submit thêm → tốc độ gửi tự động giảm xuống khớp với khả năng xử lý của pool.

## Liên quan
[[Concurrency - ExecutorService và Future]] · [[Concurrency - Thread và Shared Memory]]
