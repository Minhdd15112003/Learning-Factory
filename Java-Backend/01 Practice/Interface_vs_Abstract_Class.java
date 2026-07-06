/**
 * Notify
 */
interface INotify {
    void send();

}

abstract class ANotify {
    protected String recipient;

    public ANotify(String recipient) {
        this.recipient = recipient;
    }

    protected String formatTimestamp() {
        return "time";
    }

}

public class Interface_vs_Abstract_Class {
    public class EmailNotification extends ANotify implements INotify {
        private String type;

        public EmailNotification(String type, String recipient) {
            super(recipient);
            this.recipient = recipient;
            this.type = type;
        }

        public void send() {

            System.out.println(formatTimestamp() + " send to:");
        }
    }

    public class SmsNotification {

    }

    public class context() {

    }

    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
