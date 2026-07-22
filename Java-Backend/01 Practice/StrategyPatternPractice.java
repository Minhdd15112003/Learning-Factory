// StrategyPatternPractice.java (C)
// Bài thực hành: Strategy Pattern — Fee Calculation

/*
Yêu cầu:
Một hệ thống chuyển tiền có các loại phí giao dịch:
  - Nội bộ (INTERNAL): miễn phí
  - Liên ngân hàng (INTERBANK): phí cố định 10,000đ
  - Quốc tế (INTERNATIONAL): 1.5% số tiền, tối thiểu 50,000đ

Áp dụng Strategy Pattern để thêm loại phí thứ 4 (NAPAS 247) mà không sửa code cũ.
*/

// TODO 4: Ở main(), tạo TransactionProcessor với từng strategy, in kết quả
// Với số tiền 1_000_000 (1 triệu đồng):
//   1. Nội bộ → 0
//   2. Liên ngân hàng → 10,000
//   3. Quốc tế → max(50_000, 1_000_000 * 1.5%) = 50_000 (vì 15_000 < 50_000)
//   4. ĐỔI strategy lúc runtime từ Interbank → International giữa chừng
public class StrategyPatternPractice {
    public static void main(String[] args) {
        // TODO 4 code ở đây
        InternalFeeStrategy internalFeeStrategy = new InternalFeeStrategy();
        TransactionProcessor free = new TransactionProcessor(internalFeeStrategy);
        System.err.println(free.processTransaction(1_000_000));


        InterbankFeeStrategy interbankFeeStrategy = new InterbankFeeStrategy();
        free.setFeeStrategy(interbankFeeStrategy);
        System.err.println(free.processTransaction(1_000_000));


        InternationalFeeStrategy internationalFeeStrategy = new InternationalFeeStrategy();
        free.setFeeStrategy(internationalFeeStrategy);
        System.err.println(free.processTransaction(1_000_000));

        Napas247FeeStrategy napas247FeeStrategy = new Napas247FeeStrategy();
        free.setFeeStrategy(napas247FeeStrategy);
        System.err.println(free.processTransaction(100));
    }
}

// TODO 1: Khai báo interface FeeStrategy với method tính phí (đầu vào: số tiền, đầu ra: long)
// (Viết ở đây)
interface FeeStrategy {

    long calculateFee(long amount);
    
}

// TODO 2: Viết 3 class concrete implement FeeStrategy
// - InternalFeeStrategy: trả về 0
// - InterbankFeeStrategy: trả về 10_000
// - InternationalFeeStrategy: max(50_000, amount * 1.5%)
// HINT: (long) (amount * 0.015) để tính 1.5%

// (Viết ở đây)
class InternalFeeStrategy implements FeeStrategy{
    public long calculateFee(long amount){
        return 0;
    }
}

class InterbankFeeStrategy implements FeeStrategy{
    public long calculateFee(long amount){
        return 10_000;
    }
}

class InternationalFeeStrategy implements FeeStrategy{
    public long calculateFee(long amount){
        return Math.max(50_000, (long) (amount * 0.015));
    }
}

class Napas247FeeStrategy implements FeeStrategy{
     public long calculateFee(long amount){
        return 123123;
    }  
}

// TODO 3: Viết class TransactionProcessor (Context) giữ một FeeStrategy
// - Property: private FeeStrategy feeStrategy
// - Constructor nhận FeeStrategy
// - Method: public long processTransaction(long amount)
//   (gọi feeStrategy.calculateFee(amount) và in ra màn hình)
// - Setter để đổi strategy lúc runtime

// (Viết ở đây)
class TransactionProcessor{
    FeeStrategy fee;
    public TransactionProcessor(FeeStrategy fee){
        this.fee = fee;
    }

    public void setFeeStrategy(FeeStrategy fee){
        this.fee = fee;
    }

    public long processTransaction(long amount){
        return fee.calculateFee(amount);
    }

}



