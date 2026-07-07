// CollectionsPractice.java  (C) — HashSet/HashMap & hợp đồng hashCode/equals
// Mục tiêu: (A) thấy HashSet chống trùng gọn; (B) TỰ TAY dính cái bẫy "quên override
//           hashCode/equals" rồi sửa — thứ interviewer fintech hay hỏi.
//
// Chạy (trong 01 Practice/, nhớ `chcp 65001` để hiện tiếng Việt):
//   javac CollectionsPractice.java && java CollectionsPractice

import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Objects;

public class CollectionsPractice {

    public static void main(String[] args) {
        // ── Phần A: chống trùng ID bằng HashSet ───────────────────────────
        Set<String> seen = new HashSet<>();
        String[] incoming = {"TXN-1", "TXN-2", "TXN-1", "TXN-3", "TXN-2"};

        // TODO(A): duyệt `incoming`. Với mỗi id: nếu seen.contains(id) -> in "TRÙNG: <id>";
        //          ngược lại seen.add(id) và in "OK: <id>".
        //          -> contains/add ở đây là ~O(1), không quét cả tập.
        for (String key : incoming) {
            if (seen.contains(key)){
                System.out.println("TRÙNG: "+ key);
                continue;
            }
            seen.add(key);
            System.out.println("OK: "+ key);           
        }

        // ── Phần B: cái bẫy dùng object tự định nghĩa làm key ─────────────
        Map<TxnKey, Integer> ledger = new HashMap<>();
        ledger.put(new TxnKey("ACC-1", 1001), 500);

        // Cùng nội dung ("ACC-1", 1001) nhưng là object MỚI:
        Integer amount = ledger.get(new TxnKey("ACC-1", 1001));
        System.out.println("Tra cứu số tiền: " + amount);
        // TODO(B1): chạy thử NGAY BÂY GIỜ. Kết quả in ra là gì? Vì sao lại thế,
        //           dù nội dung key y hệt? (gợi ý: TxnKey chưa override hashCode/equals)

        // TODO(B2): override hashCode() và equals() trong class TxnKey bên dưới cho đúng,
        //           rồi chạy lại. Giờ tra cứu ra 500 chưa? Giải thích bằng 1 câu:
        //           hashCode giúp bước nào, equals giúp bước nào?
    }
}

// Khóa giao dịch: tài khoản + số thứ tự. Đang THIẾU hashCode/equals — đó là bài của bạn.
class TxnKey {
    private final String account;
    private final int seq;

    public TxnKey(String account, int seq) {
        this.account = account;
        this.seq = seq;
    }

    // TODO(B2): thêm @Override public boolean equals(Object o) { ... }
    //           và    @Override public int hashCode() { ... }
    //           Gợi ý: dùng Objects.equals(...) và Objects.hash(account, seq).
}
