// Polymorphism.java  (C) — bài thực hành Dynamic Dispatch
// Mục tiêu: THẤY tận mắt runtime chọn method theo dynamic type, còn compiler gác theo static type.
//
// Cách chạy (trong thư mục 01 Practice/):
//   javac Polymorphism.java && java Polymorphism
//
// Làm theo 3 thí nghiệm bên dưới. Chỉ điền vào các chỗ // TODO.

import java.util.List;

public class Polymorphism {
    public static void main(String[] args) {
        // Một danh sách kiểu INotify (static type) chứa lẫn Email + Sms (dynamic type khác nhau)
        List<INotify> channels = List.of(
                new EmailNotification("an@example.com"),
                new SmsNotification("0900123456"),
                new EmailNotification("binh@example.com")
        );

        // ── Thí nghiệm 1: dynamic dispatch trong một vòng lặp ──────────────
        // Chỉ có DUY NHẤT một chỗ gọi n.send(...) cho cả list.
        // TODO(1): viết vòng lặp for-each duyệt `channels`, mỗi phần tử gọi .send("Xin chào")
        //          Quan sát: cùng một dòng gọi, nhưng in ra khi "Email" khi "Sms".
        //          -> Ai chọn bản send() nào chạy? tại thời điểm nào?


        // ── Thí nghiệm 2: static type gác cửa ─────────────────────────────
        INotify n = new EmailNotification("test@example.com");
        n.send("hi");
        // TODO(2): BỎ COMMENT dòng dưới, chạy `javac` lại, ĐỌC lỗi tiếng Anh compiler in ra,
        //          rồi comment lại. Ghi vào đây 1 dòng: compiler dựa vào kiểu NÀO của `n` để chặn?
        // n.sendBulk(List.of("a@x.com", "b@x.com"));   // sendBulk() chỉ EmailNotification mới có


        // ── Thí nghiệm 3: ép về dynamic type (cast) ───────────────────────
        // TODO(3): dùng cast để lấy lại khả năng gọi sendBulk():
        //          ((EmailNotification) n).sendBulk(List.of("a@x.com", "b@x.com"));
        //          Vì sao lúc này compiler cho phép? (gợi ý: bạn vừa đổi static type mà compiler nhìn thấy)

    }
}

interface INotify {
    void send(String message);
}

abstract class ANotify {
    protected String recipient;

    public ANotify(String recipient) {
        this.recipient = recipient;
    }

    protected String formatTimestamp() {
        return "[2026-07-07 10:00]";
    }
}

class EmailNotification extends ANotify implements INotify {
    public EmailNotification(String recipient) {
        super(recipient);
    }

    @Override
    public void send(String message) {
        // TODO(1a): in ra dạng:  <timestamp> EMAIL -> <recipient>: <message>
    }

    // method CHỈ Email mới có — không nằm trong interface INotify
    public void sendBulk(List<String> recipients) {
        System.out.println(formatTimestamp() + " EMAIL BULK -> " + recipients);
    }
}

class SmsNotification extends ANotify implements INotify {
    public SmsNotification(String recipient) {
        super(recipient);
    }

    @Override
    public void send(String message) {
        // TODO(1b): in ra dạng:  <timestamp> SMS -> <recipient>: <message>
    }
}
