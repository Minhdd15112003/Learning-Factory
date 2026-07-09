---
status: Understood
tags: [java, stream, functional, review]
sr-due: 2026-07-12
sr-interval: 3
sr-ease: 250
---

# Stream API (Java) (C)

> So sánh với JavaScript/Python: `.filter().map()` trong JS/Python tạo mảng trung gian mỗi bước (eager). Java Stream không tạo — lazy evaluation.

## Cơ chế cốt lõi

### 1. Lazy Evaluation
- Các bước `filter`, `map`, `sorted`... là **intermediate operation** — chỉ **ghi nhớ công việc**, chưa chạy gì.
- Chỉ khi gặp **terminal operation** (`collect`, `forEach`, `count`, `toList()`...) thì pipeline mới thực sự chạy.
- Không có terminal operation = không có gì xảy ra.

### 2. Duyệt dọc (vertical processing)
- Mỗi phần tử đi qua **tất cả các bước** trước khi sang phần tử tiếp theo.
- Khác JS/Python: chạy xong `filter` trên toàn bộ mảng → tạo mảng mới → rồi `map` trên mảng mới đó (duyệt ngang / horizontal).

### 3. Không tạo collection trung gian
- Vì duyệt dọc + lazy → không cần mảng tạm giữa các bước.
- Phần tử bị loại ở `filter` sẽ **không bao giờ chạy qua `map`** → tiết kiệm CPU.

## Ví dụ

```java
list.stream()
    .filter(x -> x > 3)       // intermediate — ghi nhớ
    .map(x -> x * 10)         // intermediate — ghi nhớ
    .forEach(System.out::println); // terminal — chạy thật
```

Với `[1, 2, 3, 4, 5, 6]`:
- Phần tử `1, 2, 3` bị `filter` loại → `map` không chạy trên chúng.
- Phần tử `4, 5, 6` qua `filter` → `map` → `forEach`, lần lượt từng phần tử.

## Lợi ích so với eager (JS/Python)
| | Eager (JS/Python) | Lazy (Java Stream) |
|---|---|---|
| RAM | Tạo mảng trung gian mỗi bước | Không tạo collection trung gian |
| CPU | Mỗi bước duyệt hết output bước trước | Phần tử bị loại sớm → bước sau không chạy |

## Phân loại operation
- **Intermediate** (lazy): `filter`, `map`, `sorted`, `distinct`, `limit`, `flatMap`...
- **Terminal** (kích hoạt): `collect`, `forEach`, `count`, `toList()`, `reduce`, `findFirst`, `anyMatch`...
