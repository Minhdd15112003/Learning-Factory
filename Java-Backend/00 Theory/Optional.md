---
status: Understood
tags: [java, optional, review]
sr-due: 2026-07-12
sr-interval: 3
sr-ease: 250
---

# Optional (Java) (C)

> Giải quyết vấn đề NullPointerException — ép người gọi xử lý trường hợp rỗng ở tầng compiler, không phụ thuộc kỷ luật cá nhân.

## Vấn đề Optional giải quyết

```java
// Trước: trả null — compiler không cảnh báo, quên check → crash runtime
User user = findByEmail("abc@test.com");  // null nếu không tìm thấy
String name = user.getName();              // 💥 NullPointerException

// Sau: trả Optional — compiler ép phải mở hộp trước khi dùng
Optional<User> user = findByEmail("abc@test.com");
user.getName();  // ❌ Không compile — user là Optional, không phải User
```

## Cơ chế cốt lõi

`Optional<T>` là một **cái hộp** — có thể chứa giá trị hoặc rỗng.
- Người gọi **không thể** dùng giá trị trực tiếp → **buộc phải mở hộp** → **buộc phải xử lý trường hợp rỗng**.
- `null` check là **convention** (hứa suông). `Optional` là **contract** (compiler ép).

## Các cách lấy giá trị ra

| Method | Hành vi | Khi nào dùng |
|---|---|---|
| `.get()` | Lấy giá trị ra, rỗng → `NoSuchElementException` | Khi **chắc chắn** có giá trị (hiếm khi dùng) |
| `.orElse(default)` | Có → lấy ra, rỗng → trả `default` | Khi có giá trị mặc định hợp lý |
| `.map(fn)` | Biến đổi giá trị bên trong hộp, không mở ra | Khi cần transform trước khi lấy |
| `.ifPresent(fn)` | Có → chạy `fn`, rỗng → không làm gì | Khi chỉ cần side-effect (print, log) |
| `.orElseThrow()` | Có → lấy ra, rỗng → ném exception tùy chọn | Khi rỗng là lỗi nghiêm trọng |

## Ví dụ thực tế

```java
String name = findByEmail("abc@test.com")
    .map(user -> user.getName())    // Optional<User> → Optional<String>
    .orElse("Unknown");             // Mở hộp, rỗng → "Unknown"
```

## Khi nào dùng Optional
- **Return type** của hàm có thể không trả kết quả (tìm kiếm, lookup).
- **Không dùng** cho field, parameter, hay collection (dùng empty collection thay thế).
