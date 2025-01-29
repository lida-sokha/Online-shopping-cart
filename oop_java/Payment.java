import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private static int nextpayID=1;
    private int payID;
    private double amount;
    private LocalDateTime paymenDateTime;

    public Payment(double amount){//class scope is initialize the payid, amount, paymendate
        this.payID=nextpayID++;
        this.amount=amount;
        this.paymenDateTime=LocalDateTime.now();
    }
    public String getformatePayment(){// class scope it format the paymentdate to string 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return paymenDateTime.format(formatter);
    }
    public String toString(){// class scope return to string 
        return "PayID: " + payID + "\n" +
                "Amount: " + amount + "\n" +
                "Pay Date: " +getformatePayment();
    }
}
