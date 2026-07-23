---
status: Understood
tags: [java, collections, dsa, review]
sr-due: 2026-07-26
sr-interval: 11
sr-ease: 270
---

# List — ArrayList vs LinkedList (C)

> Note do mình (Claudian) soạn. Bạn đã hiểu hướng cơ chế nhưng chưa tái hiện đầy đủ cả hai phía ở Feynman — cần chốt lại ở review sau.

## Trục quyết định
> Việc chính là **truy cập nhanh theo index** hay **chèn/xóa thường xuyên ở đầu/giữa**?

- Truy cập theo index, ít chèn/xóa → **`ArrayList`**.
- Chèn/xóa ở đầu/giữa nhiều, không cần random access → **`LinkedList`** (nhưng xem trade-off bên dưới).

## Cấu trúc bộ nhớ — điểm khác biệt gốc
| | `ArrayList` | `LinkedList` |
|---|---|---|
| Bộ nhớ | **Mảng liên tục** (contiguous) | **Node rải rác** trên RAM, nối bằng con trỏ `next` |
| Truy cập index `i` | O(1) — `base_address + i * element_size` | O(n) — duyệt tuần tự từ đầu |

## Chèn vào giữa — cơ chế từng bước
### ArrayList
1. Trỏ thẳng đến vị trí giữa — O(1).
2. **Dịch chuyển / copy** tất cả phần tử từ vị trí đó trở về sau lùi 1 ô — O(n).
→ Tổng: **O(n)** (chi phí nằm ở bước dịch chuyển).

### LinkedList
1. **Duyệt tuần tự** từ đầu đến vị trí giữa — O(n).
2. Sửa 2 con trỏ `next` — O(1).
→ Tổng: **O(n)** (chi phí nằm ở bước duyệt).

## Vì sao LinkedList chậm hơn trong thực tế (dù cùng O(n))
- ArrayList dịch chuyển khối nhớ **liên tục** → CPU tận dụng **cache prefetch**, memcpy tối ưu.
- LinkedList duyệt qua node **rải rác** trên RAM → **cache miss** liên tục, chậm hơn nhiều.

## Feynman — đã chốt (2026-07-09)
- Giải thích đầy đủ cơ chế chèn vào giữa cả hai phía: ArrayList dịch phần tử O(n), LinkedList duyệt tuần tự O(n) rồi sửa con trỏ O(1).
- Nêu được **cache locality**: ArrayList nằm liên tục trên RAM → CPU kéo khối bộ nhớ vào cache hiệu quả; LinkedList rải rác → **cache miss** liên tục → chậm hơn thực tế dù cùng O(n).
