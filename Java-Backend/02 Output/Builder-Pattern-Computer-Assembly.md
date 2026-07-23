# Output — Builder Pattern: Computer Assembly (C)

## Mô tả
Tạo object `Computer` có trường bắt buộc (cpu, ram, ssd) và tùy chọn (gpu) mà không cần constructor dài dòng hay nhiều overload.

## Cơ chế
- `Computer` có `private constructor` nhận `Builder` — không ai tạo trực tiếp
- `Builder` là static inner class, giữ các field giống Computer
- Constructor của Builder nhận trường bắt buộc, setter cho trường tùy chọn (return `this` để chaining)
- `build()` gọi `new Computer(this)`

## Code hoàn chỉnh

```java
public class BuilderPatternPractice {
    public static void main(String[] args) {
        Computer basic = new Computer.Builder("i5", "16GB", "512GB").build();
        Computer gaming = new Computer.Builder("i9", "32GB", "1TB").setGpu("RTX 4090").build();

        System.out.println(basic.toString());
        System.out.println(gaming.toString());
    }
}

class Computer {
    private final String cpu;
    private final String ram;
    private final String ssd;
    private final String gpu;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.ssd = builder.ssd;
        this.gpu = builder.gpu;
    }

    @Override
    public String toString() {
        return "Computer [cpu=" + cpu + ", ram=" + ram + ", ssd=" + ssd +
                (gpu != null ? ", gpu=" + gpu : "") + "]";
    }

    public static class Builder {
        private final String cpu;
        private final String ram;
        private final String ssd;
        private String gpu;

        public Builder(String cpu, String ram, String ssd) {
            this.cpu = cpu;
            this.ram = ram;
            this.ssd = ssd;
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

## Bài học rút ra
- Builder tách rõ trường bắt buộc (constructor) vs tùy chọn (setter chaining).
- Object tạo ra là immutable (`private final` fields + private constructor).
- Setter return `this` → method chaining: `.setGpu("RTX 4090").build()`.
