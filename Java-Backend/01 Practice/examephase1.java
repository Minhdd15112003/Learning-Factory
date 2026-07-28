
public class examephase1 {

  public static void main(String[] args) {
    TransactionProcessor processor = new TransactionProcessor();
    IntraBank intraBank = new IntraBank();
    Adapter adapter = new Adapter(intraBank);
    Decorator decorator = new Decorator(adapter);
    processor.setStrategy(decorator);
    processor.processTransfer(100.1);

  }

  interface TransactionStrategy {
    public void transfer(Double amount);
  }

  public static class TransactionProcessor {
    TransactionStrategy strategy;

    public TransactionStrategy setStrategy(TransactionStrategy strategy) {
      this.strategy = strategy;

      return strategy;
    }

    public void processTransfer(Double amount) {
      strategy.transfer(amount);
    }
  }

  public static class IntraBank {

    public void sendData(int amount) {
      System.out.println("transfer with intra-bank: " + amount);
    }
  }

  public static class Decorator implements TransactionStrategy {
    TransactionStrategy strategy;

    public Decorator(TransactionStrategy strategy) {
      this.strategy = strategy;
    }

    @Override
    public void transfer(Double amount) {
      System.out.println("[LOG] Processing: " + amount);
      this.strategy.transfer(amount);
      System.out.println("[LOG] Done: " + amount);
    }
  }

  public static class Adapter implements TransactionStrategy {
    IntraBank intraBank;

    public Adapter(IntraBank intraBank) {
      this.intraBank = intraBank;
    }

    @Override
    public void transfer(Double amount) {
      intraBank.sendData(amount.intValue());

    }
  }

  public static class Interbank implements TransactionStrategy {
    @Override
    public void transfer(Double amount) {
      System.out.println("transfer with interbank");
    }
  }
}
