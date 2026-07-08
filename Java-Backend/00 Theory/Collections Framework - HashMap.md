---
status: Understood
tags: [java, collections, dsa, review]
sr-due: 2026-07-12
sr-interval: 4
sr-ease: 270
---

# Collections Framework — HashMap / HashSet & Big-O (Java) (C)

> Note do mình (Claudian) soạn sau khi bạn tự suy ra vì sao tra cứu là O(1) và cách xử lý đụng độ (bucket + equals).

## Bài toán mở đầu
Giữ 10 triệu ID giao dịch; liên tục hỏi "ID này đã xử lý chưa?" (chống trùng / double-spend).

## Trục quyết định (chọn cấu trúc)
> Việc chính là **tra cứu theo khóa / kiểm tồn tại nhanh** hay **giữ thứ tự + truy cập theo chỉ số**?

- Tra cứu / kiểm tồn tại nhanh → **`HashSet` / `HashMap`** (~O(1)).
- Giữ thứ tự chèn, duyệt tuần tự, lấy theo index → **`ArrayList`** (nhưng `contains` là O(n)).

## Vì sao List chậm còn HashMap nhanh
| | `ArrayList.contains(x)` | `HashMap.get(k)` / `HashSet.contains(k)` |
|---|---|---|
| Cách làm | **quét tuyến tính**, hỏi từng phần tử "phải mày không?" | **tính** thẳng ra vị trí từ nội dung key |
| Chi phí | **O(n)** — tăng theo số phần tử | **~O(1)** — một phép tính, không phụ thuộc n |

## Cơ chế (chuỗi từ key tới ô)
```
key "TXN-8842"
  → (hàm băm / hash function)  → hash code   (một số, có thể rất lớn)
  → (nén về cỡ mảng: % hoặc &) → index       (0 .. capacity-1)
  → ô (bucket) trong mảng nội bộ `table` (object nằm trên heap/RAM)
```
- `hash code` **không phải** là index. Số to phải được **nén** về khoảng chỉ số hợp lệ mới thành địa chỉ ô.
- Vì O(1) chỉ là "một phép tính trên key", tra cứu với 10 hay 10 triệu phần tử **như nhau**.

## Đụng độ (collision) — cùng ô ≠ cùng value
Hai key khác nhau có thể nén ra **cùng một ô**. Khi đó ô giữ **một danh sách các cặp (key, value)** riêng biệt:
```
ô 5 → [ ("TXN-8842", v1) , ("TXN-0033", v2) ]
```
Tra cứu dùng **hai công cụ, hai vai trò**:
1. `hashCode()` → **nhảy tới đúng ô** (nhanh).
2. `equals()` → trong ô, **so key thật** để chọn đúng cặp.

## Hệ quả interview: hợp đồng hashCode / equals
Dùng **object tự định nghĩa làm key** → phải override **cả hai** cho đúng:
- `hashCode()` sai → tính ra **nhầm ô** → không tìm thấy dù key "giống hệt".
- `equals()` sai → tới đúng ô nhưng **không nhận ra** cặp cần tìm.
- Quy tắc: hai object mà `equals()` bằng nhau thì **bắt buộc** cùng `hashCode()`.

## Big-O đầy đủ
- Trung bình: O(1). Xấu (nhiều đụng độ, băm kém): suy biến về O(n) trong một ô. Java 8+ biến chuỗi dài trong ô thành **cây cân bằng** → O(log n). [[Big-O]] · [[DSA]]
