/**
 * Notify
 */
interface Notify {
    void send();

}

abstract class abstractNotify {
    protected String recipient;

    protected abstract void formatTimestamp();

}

public class Interface_vs_Abstract_Class {
    public class EmailNotification {

    }

    public class SmsNotification {

    }

    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
