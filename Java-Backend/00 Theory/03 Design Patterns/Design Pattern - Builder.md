---
status: Understood
tags: [java, design-pattern, review]
sr-due: 2026-07-22
sr-interval: 4
sr-ease: 270
---

# Design Pattern - Builder

[[Builder]] là mẫu thiết kế giúp khởi tạo các đối tượng phức tạp có nhiều thuộc tính (đặc biệt là khi có nhiều tham số tùy chọn). Thay vì dùng hàng loạt constructor chồng chéo (Telescoping Constructor), Builder tách quá trình xây dựng đối tượng ra thành từng bước.

## 1. Vấn đề giải quyết
- Tránh việc phải truyền quá nhiều tham số vào Constructor (dễ nhầm thứ tự).
- Tránh việc phải truyền `null` cho các tham số tùy chọn không dùng tới.
- Đảm bảo tính **Bất biến (Immutability)**: Các thuộc tính của object chính thường là `private final` và không có setter sau khi đã `build()` xong.

## 2. Cấu trúc chuẩn
1. **Class chính (Product)**: Có constructor `private` nhận vào Builder. Các thuộc tính thường là `final`.
2. **Static Inner Class (Builder)**: 
   - Chứa các thuộc tính tương ứng với class chính.
   - Constructor nhận các tham số **bắt buộc**.
   - Các method `set...` cho các tham số **tùy chọn**, mỗi method đều `return this` để hỗ trợ **Method Chaining**.
   - Method `build()` gọi constructor của class chính và truyền chính nó (`this`) vào.

## 3. Code mẫu

```java
public class Computer {
    private final String cpu; // bắt buộc
    private final String ram; // bắt buộc
    private final String gpu; // tùy chọn

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.gpu = builder.gpu;
    }

    public static class Builder {
        private final String cpu;
        private final String ram;
        private String gpu;

        public Builder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }

        public Builder setGpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}
```

## 4. Tại sao cần `this` trong `build()`?
Method `build()` gọi `new Computer(this)`. Ở đây `this` chính là instance hiện tại của **Builder** chứa đầy đủ các thông số mà người dùng đã "ráp" nãy giờ. Class `Computer` sau đó sẽ trích xuất dữ liệu từ đối tượng Builder này để tự hoàn thiện mình.
