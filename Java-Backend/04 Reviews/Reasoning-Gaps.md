# Reasoning Gaps Log

> Nơi ghi lại các hiểu lầm và lỗ hổng suy luận phát hiện trong quá trình học.

## Active Gaps
(Trống)

## Resolved Gaps
### 2026-07-06 — Đảo trục "interface vs abstract class"
- **Hiểu lầm ban đầu:** chọn giữa interface / abstract class dựa trên *số lượng method* ("interface khi nhiều method", "abstract khi cần quy ước chung"). Cả hai đều ép class phải có method, nên đó không phải trục quyết định.
- **Đã sửa:** trục đúng = **có cần chia sẻ code/state thật không** (→ abstract, tốn suất kế thừa) hay **chỉ hứa một khả năng** (→ interface, gắn được nhiều). Bạn tự chốt lại đúng ở cuối buổi.
- **Cần theo dõi:** cách nói "nhiều method" từng tái xuất một lần — soi lại ở review kế tiếp xem trục đã dính chắc chưa.
