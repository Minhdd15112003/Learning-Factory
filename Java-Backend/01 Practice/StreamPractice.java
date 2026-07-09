// StreamPractice.java (C)
// Thực hành: Stream API — lazy evaluation, intermediate vs terminal, duyệt dọc.
//
// Hướng dẫn:
//   javac StreamPractice.java && java StreamPractice
//
// Hoàn thành các TODO. Chỉ dùng Stream API, không dùng vòng lặp for/while.

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamPractice {

    // === Bài 1: filter + map + collect ===
    // Cho danh sách giao dịch (số tiền). Lọc các giao dịch > 1_000_000,
    // nhân mỗi giao dịch với 1.1 (phí 10%), thu thập kết quả vào List<Double>.
    // In ra list kết quả.
    public static void bai1_filterMapCollect() {
        List<Integer> transactions = Arrays.asList(
                500_000, 2_000_000, 800_000, 5_000_000, 300_000, 1_500_000);

        // TODO: Dùng stream để lọc > 1_000_000, nhân 1.1, collect vào List<Double>]
        List<Double> result = transactions.stream().filter(x -> x > 1_000_000).map(x -> x * 1.1)
                .collect(Collectors.toList());
        // ...
        // .collect(Collectors.toList());

        // TODO: In kết quả
        System.out.println("result: " + result);
    }

    // === Bài 2: Chứng minh lazy evaluation ===
    // Thêm System.out.println vào bên trong filter và map để chứng minh
    // rằng phần tử bị filter loại sẽ KHÔNG chạy qua map.
    // Dùng list [1, 2, 3, 4, 5, 6], filter > 3, map * 10.
    public static void bai2_proveLazy() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // TODO: Viết stream pipeline với print bên trong filter và map
        // để thấy thứ tự xử lý (duyệt dọc, phần tử bị loại không qua map)
        numbers.stream().filter(t -> {

            if (t < 0) {
                return true;
            }
            System.out.println("filter: " + t);
            return false;
        }).map(n -> {
            System.out.println("map: " + n);
            if (n % 2 != 0) {
                return true;
            }
            return false;
        }).forEach(x -> System.out.println("result: " + x));
        // Gợi ý:
        // numbers.stream()
        // .filter(x -> {
        // System.out.println("filter: " + x);
        // return ???;
        // })
        // .map(x -> {
        // System.out.println("map: " + x);
        // return ???;
        // })
        // .forEach(x -> System.out.println("result: " + x));
    }

    // === Bài 3: Không có terminal = không chạy ===
    // Viết một stream pipeline (filter + map) KHÔNG có terminal operation.
    // Thêm print bên trong filter để chứng minh nó không chạy.
    // Sau đó thêm terminal operation và thấy nó chạy.
    public static void bai3_noTerminal() {
        List<Integer> numbers = Arrays.asList(10, 20, 30);

        System.out.println("--- Không có terminal ---");
        // TODO: Viết stream với filter (có print bên trong) + map, KHÔNG có terminal
        // Quan sát: không có dòng nào in ra.
        numbers.stream().filter(t -> {
            System.out.println("khong chay");
            if (t == 0) {
                t += 1;
                return true;
            }
            return false;

        }).map(t -> {
            System.out.println("map: " + t);
            return t;
        });

        System.out.println("--- Có terminal ---");
        // TODO: Viết lại stream giống hệt nhưng THÊM terminal operation
        // (forEach/collect/count)
        // Quan sát: filter chạy, in ra.
        var count = numbers.stream().filter(t -> {
            System.out.println("khong chay");
            if (t == 0) {
                t += 1;
                return true;
            }
            return false;

        }).map(t -> {
            System.out.println("map: " + t);
            return t;
        }).count();
        System.out.println("count: " + count);
    }

    // === Bài 4: Tình huống thực tế — xử lý danh sách tài khoản ===
    // Cho danh sách tên tài khoản. Lọc các tài khoản bắt đầu bằng "VIP-",
    // bỏ prefix "VIP-", chuyển thành chữ hoa, sắp xếp A-Z, thu thập vào
    // List<String>.
    public static void bai4_accountProcessing() {
        List<String> accounts = Arrays.asList(
                "VIP-nguyen", "standard-tran", "VIP-le", "standard-pham", "VIP-hoang");

        // TODO: stream pipeline: filter startsWith "VIP-" → bỏ "VIP-" → toUpperCase →
        // sorted → collect
        List<String> result = accounts.stream().filter(t -> t.startsWith("VIP-")).map(t -> {
            String newstr = t.substring(4).toUpperCase();
            return newstr;
        }).sorted().collect(Collectors.toList());
        System.out.println("result: " + result);

        // TODO: In kết quả. Kết quả mong đợi: [HOANG, LE, NGUYEN]
    }

    public static void main(String[] args) {
        System.out.println("=== Bài 1: filter + map + collect ===");
        bai1_filterMapCollect();

        System.out.println("\n=== Bài 2: Chứng minh lazy evaluation ===");
        bai2_proveLazy();

        System.out.println("\n=== Bài 3: Không terminal = không chạy ===");
        bai3_noTerminal();

        System.out.println("\n=== Bài 4: Xử lý tài khoản ===");
        bai4_accountProcessing();
    }
}
