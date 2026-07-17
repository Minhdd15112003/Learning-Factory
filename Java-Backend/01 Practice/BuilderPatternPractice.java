// BuilderPatternPractice.java (C)
// Bài thực hành: Builder Pattern — Computer Assembly

public class BuilderPatternPractice {
    public static void main(String[] args) {
        // TODO 4: Tạo 2 object Computer bằng Builder
        // 1. Máy cơ bản: chỉ có cpu ("i5"), ram ("16GB"), ssd ("512GB")
        // 2. Máy gaming: cpu ("i9"), ram ("32GB"), ssd ("1TB"), gpu ("RTX 4090")

        Computer basic = new Computer.Builder("i5", "16GB", "512GB").build();
        Computer gaming = new Computer.Builder("i9",  "32GB",  "1TB").setGpu( "RTX 4090").build();

        System.out.println(basic.toString());
        System.out.println(gaming.toString());
    }
}

class Computer {
    // 3 trường bắt buộc
    private final String cpu;
    private final String ram;
    private final String ssd;

    // 1 trường tùy chọn
    private final String gpu;

    // TODO 1: Viết private constructor nhận vào Builder
    private  Computer(Builder builder){
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.ssd = builder.ssd;
        this.gpu = builder.gpu;
    };

    // In ra cho đẹp
    @Override
    public String toString() {
        return "Computer [cpu=" + cpu + ", ram=" + ram + ", ssd=" + ssd +
                (gpu != null ? ", gpu=" + gpu : "") + "]";
    }

    // TODO 2: Khai báo static inner class Builder
    public static class Builder {
        // Copy lại các trường của Computer vào đây
        private final String cpu;
        private final String ram;
        private final String ssd;
        private String gpu; // Không final vì có thể set sau

        // TODO 3: Hoàn thiện Builder
        // a. Viết constructor nhận 3 trường bắt buộc (cpu, ram, ssd)
        public Builder(String cpu, String ram, String ssd){
            this.cpu = cpu;
            this.ram = ram;
            this.ssd = ssd;
        }
        // b. Viết method setGpu(String gpu) có hỗ trợ chaining
        public Builder setGpu(String gpu) {
          this.gpu = gpu;
          return this;
        }
        // c. Viết method build() trả về Computer
        public Computer build(){
            return new Computer(this);
        }

    }
}
