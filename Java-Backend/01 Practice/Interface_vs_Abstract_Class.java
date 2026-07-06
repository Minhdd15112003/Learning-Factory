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

        public EmailNotification(String recipient) {
            super(recipient);
            this.recipient = recipient;
            this.type = "Email";
        }

        public void send() {
            System.out.println(formatTimestamp() + " " + this.type + " send to:" + this.recipient);
        }
    }

    public class SmsNotification extends ANotify implements INotify {
        private String type;

        public SmsNotification(String recipient) {
            super(recipient);
            this.recipient = recipient;
            this.type = "Sms";
        }

        public void send() {
            System.out.println(formatTimestamp() + " " + this.type + " send to:" + this.recipient);
        }
    }

    public class context {
        INotify context;

        public context(INotify context) {
            this.context = context;
        }

        public void send() {
            this.context.send();
        }
    }

    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
