---
status: Understood
tags: [java, concurrency, executor, future, review]
sr-due: 2026-07-22
sr-interval: 4
sr-ease: 270
---

# Concurrency — ExecutorService và Future (Java) (C)

> Note do mình (Claudian) soạn. Bạn đã hiểu cơ chế pool tái sử dụng thread và cách dùng Future để chạy song song.

## 1. Tại sao cần Thread Pool?
- **Tạo thread là đắt**: Mỗi `new Thread()` tốn RAM cho stack và tốn thời gian OS khởi tạo.
- **Quá tải**: Tạo 10.000 thread cùng lúc có thể làm sập ứng dụng (out of memory).
- **Giải pháp**: **Thread Pool** giữ một số lượng thread cố định, dùng đi dùng lại cho nhiều tác vụ (như quầy giao dịch cố định nhân viên).

## 2. ExecutorService
Interface quản lý pool và queue.
- `Executors.newFixedThreadPool(n)`: Tạo pool với `n` thread cố định.
- `execute(Runnable)`: Đẩy việc vào, không quan tâm kết quả, exception bị nuốt.
- `submit(Callable)`: Đẩy việc vào, trả về **Future**.

## 3. Future — Biên lai hẹn giờ
Là một "vật giữ chỗ" cho kết quả sẽ có trong tương lai.
- **Non-blocking**: `submit()` trả về Future ngay lập tức, không đợi việc chạy xong.
- **Blocking**: `future.get()` sẽ chặn luồng hiện tại cho đến khi kết quả có sẵn.
- **Chiến thuật**: Đẩy task vào pool → Làm việc khác ở luồng chính → `get()` ở bước cuối cùng để tối ưu thời gian.

## 4. Cơ chế lỗi
Nếu một task bị exception:
- Trong `execute()`: Thread chết, pool tự tạo thread mới thay thế, lỗi biến mất âm thầm.
- Trong `submit()`: Lỗi được gói vào Future, chỉ nổ ra khi gọi `get()`.

## Ví dụ tối ưu
| Tuần tự (2s) | Song song (1s) |
|---|---|
| `getAccount()` (1s) | `Future f = submit(getAccount)` (0s) |
| `getHistory()` (1s) | `getHistory()` (1s) |
| **Tổng: 2s** | `f.get()` + **Tổng: 1s** |
