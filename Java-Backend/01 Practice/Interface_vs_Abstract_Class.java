/**
 * Notify
 */

public class Interface_vs_Abstract_Class {
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

    public class Context {
        INotify context;

        public Context(INotify context) {
            this.context = context;
        }

        public void send() {
            this.context.send();
        }
    }

    public static void main(String[] args) {
        SmsNotification sms = new SmsNotification("user 1");
        EmailNotification email = new EmailNotification("user 2");

        Context context = new Context(email);
        context.send();
    }
}
