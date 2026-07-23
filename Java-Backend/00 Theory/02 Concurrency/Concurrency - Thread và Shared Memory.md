---
status: Understood
tags: [java, concurrency, review]
sr-due: 2026-07-30
sr-interval: 7
sr-ease: 250
---

# Concurrency — Thread và Shared Memory (Java) (C)

> Note do mình (Claudian) soạn sau khi bạn giải thích được cơ chế tranh chấp bộ nhớ (Race Condition).

## 1. Process vs Thread trong JVM
- **Process (Tiến trình)**: Là một chương trình đang chạy (ví dụ ứng dụng Java của bạn). Mỗi Process có vùng bộ nhớ riêng biệt, hệ điều hành cô lập chúng để tránh xung đột.
- **Thread (Luồng)**: Là đơn vị thực thi nhỏ nhất bên trong một Process. Một Process có thể có nhiều Thread chạy song song.
- **Shared Memory**: Các Thread chạy song song nhưng **chia sẻ chung vùng nhớ Heap** (nơi chứa các Object, biến instance, biến static). Điều này giúp truyền dữ liệu cực nhanh nhưng dễ xảy ra xung đột dữ liệu.

---

## 2. Race Condition (Tranh chấp bộ nhớ)
Xảy ra khi nhiều luồng cùng truy cập và thay đổi một vùng dữ liệu chung (shared state) tại cùng một thời điểm mà không có sự đồng bộ hóa.

### Cơ chế mất mát dữ liệu (Lost Update)
Lệnh `count++` trông như 1 bước, nhưng thực tế CPU thực thi qua 3 bước (không có tính atomic):
1. **Read**: Đọc giá trị hiện tại của `count` từ RAM vào thanh ghi CPU của luồng đó.
2. **Modify**: Cộng thêm 1 vào giá trị trong thanh ghi.
3. **Write**: Ghi giá trị mới từ thanh ghi ngược lại RAM.

### Kịch bản xung đột (Ví dụ: `count = 0`)
1. **Luồng A** chạy bước 1 & 2 → đọc `0`, tính ra `1` (nhưng chưa kịp ghi lại RAM).
2. **Luồng B** xen vào chạy bước 1 → đọc từ RAM vẫn thấy `0`, tính ra `1`.
3. **Luồng A** chạy bước 3 → ghi `1` vào RAM.
4. **Luồng B** chạy bước 3 → ghi tiếp `1` vào RAM, đè lên giá trị của Luồng A.

Hệ quả: Hai lần tăng (`count++` x2) đáng lẽ phải bằng `2`, nhưng kết quả thực tế chỉ bằng `1`. Một lượt cập nhật đã bị **mất hoàn toàn (Lost Update)**.

---

## 3. Giải pháp: `synchronized` — Intrinsic Lock

### Cơ chế (Monitor Lock)
Mỗi đối tượng trong Java có một khóa nội tại (Intrinsic Lock / Monitor). Khi method được khai báo với `synchronized`:
1. Luồng nào muốn chạy method đó trước tiên phải **lấy được chìa khóa** của đối tượng (`counter`).
2. Nếu chìa đang bị giữ bởi luồng khác → luồng này bị chặn (BLOCKED), đứng chờ.
3. Khi luồng đang giữ chìa* thoát khỏi method → trả chìa → JVM đánh thức luồng đang đợi.
4. Vì `count` đã được ghi lại RAM trước khi trả chìa, luồng kế tiếp đọc được giá trị mới nhất.

→ Không còn Read-Modify-Write chồng chéo.

```java
public synchronized void incrementSynchronized() {
    count++;  // Chỉ 1 luồng được ở đây tại một thời điểm
}
```

### Liên quan
- **Hệ cơ sở dữ liệu**: `synchronized` tương đương với **Isolation** trong ACID — khi một transaction đang sửa row, các transaction khác không được đọc row đó (nếu dùng READ_COMMITTED trở lên). Cùng một triết lý: khóa để tránh đọc/ghi đồng thời một đơn vị dữ liệu.
- **Học sau**: `AtomicInteger` (dùng CAS - Compare-And-Swap, nhanh hơn `synchronized` cho biến đơn lẻ); `ReentrantLock` (linh hoạt hơn `synchronized`).
