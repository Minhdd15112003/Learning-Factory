// ListPractice.java (C)
// Thực hành: ArrayList vs LinkedList — trade-off chèn, truy cập, cache locality.
//
// Hướng dẫn:
//   javac ListPractice.java && java ListPractice
//
// Bạn cần hoàn thành các TODO bên dưới. Không import thêm gì ngoài java.util.*.

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListPractice {

    // === Bài 1: Chèn vào giữa ===
    // Tạo một ArrayList và một LinkedList, mỗi cái chứa 100_000 phần tử (0..99_999).
    // Chèn số -1 vào đúng vị trí giữa (index = size / 2) của mỗi list.
    // Đo thời gian (System.nanoTime) của RIÊNG thao tác chèn (không tính thời gian tạo list).
    // In ra thời gian của cả hai và nhận xét cái nào nhanh hơn.
    public static void bai1_insertMiddle() {
        // Tạo ArrayList chứa 100_000 phần tử (0..99_999)
        List<Integer> arrayList = new ArrayList<>(100_000);
        for (int i = 0; i < 100_000; i++) arrayList.add(i);

        // Tạo LinkedList chứa 100_000 phần tử (0..99_999)
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 100_000; i++) linkedList.add(i);

        // Đo thời gian chèn -1 vào giữa ArrayList
        int midAL = arrayList.size() / 2;
        long startAL = System.nanoTime();
        arrayList.add(midAL, -1);
        long durationAL = System.nanoTime() - startAL;

        // Đo thời gian chèn -1 vào giữa LinkedList
        int midLL = linkedList.size() / 2;
        long startLL = System.nanoTime();
        linkedList.add(midLL, -1);
        long durationLL = System.nanoTime() - startLL;

        System.out.println("ArrayList insert middle: " + durationAL + " ns");
        System.out.println("LinkedList insert middle: " + durationLL + " ns");
        String faster = durationAL < durationLL ? "ArrayList" : "LinkedList";
        System.out.println("Nhanh hơn: " + faster);
    }

    // === Bài 2: Truy cập theo index ===
    // Dùng lại 2 list từ bài 1 (hoặc tạo mới, 100_000 phần tử).
    // Truy cập (get) phần tử ở vị trí 50_000 của mỗi list.
    // Đo thời gian, in kết quả, giải thích vì sao chênh lệch.
    public static void bai2_getByIndex() {
        List<Integer> arrayList = new ArrayList<>(100_000);
        for (int i = 0; i < 100_000; i++) arrayList.add(i);

        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 100_000; i++) linkedList.add(i);

        int idx = 50_000;
        long startAL = System.nanoTime();
        Integer valAL = arrayList.get(idx);
        long durationAL = System.nanoTime() - startAL;

        long startLL = System.nanoTime();
        Integer valLL = linkedList.get(idx);
        long durationLL = System.nanoTime() - startLL;

        System.out.println("ArrayList get(50000): " + durationAL + " ns (val=" + valAL + ")");
        System.out.println("LinkedList get(50000): " + durationLL + " ns (val=" + valLL + ")");
        String faster = durationAL < durationLL ? "ArrayList" : "LinkedList";
        System.out.println("Nhanh hơn: " + faster);
    }

    // === Bài 3: Chọn đúng List ===
    // Cho mỗi tình huống bên dưới, trả về "ArrayList" hoặc "LinkedList".
    // Uncomment dòng System.out.println và điền đáp án.
    public static void bai3_chooseRight() {
        // Tình huống A: Hệ thống cần đọc giao dịch theo số thứ tự (index) liên tục,
        //               hiếm khi thêm/xóa ở giữa.
        System.out.println("A: " + "ArrayList");

        // Tình huống B: Hàng đợi xử lý lệnh — liên tục thêm vào cuối, lấy ra ở đầu,
        //               không bao giờ truy cập theo index.
        System.out.println("B: " + "LinkedList");

        // Tình huống C: Cache danh sách 10_000 sản phẩm, client gọi API phân trang
        //               (offset + limit) rất nhiều.
        System.out.println("C: " + "ArrayList");
    }

    public static void main(String[] args) {
        System.out.println("=== Bài 1: Chèn vào giữa ===");
        bai1_insertMiddle();

        System.out.println("\n=== Bài 2: Truy cập theo index ===");
        bai2_getByIndex();

        System.out.println("\n=== Bài 3: Chọn đúng List ===");
        bai3_chooseRight();
    }
}
