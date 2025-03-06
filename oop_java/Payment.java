package oop_java;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private static int nextpaymentID=0;
    private int payID;
    private double amount;
    private LocalDateTime paymenDateTime;
    private String paymentMethod;
    private String paymentStatus;
    private String customerEmail; 

    public Payment(double amount,String paymentMethod, String customerEmail){//class scope is initialize the payid, amount, paymendate
        this.payID=++nextpaymentID;
        this.amount=amount;
        this.paymentMethod=paymentMethod;
        this.paymentStatus="Pending";
        this.customerEmail=customerEmail;
        this.paymenDateTime=LocalDateTime.now();
    }
    //getter and setter
    public int getPaymentId() {
        return payID;
    }

    public void setPaymentId(String paymentId) {
        this.payID = payID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    public String getformatePayment(){// class scope it format the paymentdate to string 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return paymenDateTime.format(formatter);
    }
//method to process the payment
    public boolean processPayment(){
        if("Credit card".equals(paymentMethod)){
            this.paymentStatus="completed";
            return true;
        }
        else {
            this.paymentStatus = "Failed";
            return false;
        }
    }
    // Method to refund a payment
    public boolean refundPayment() {
        // Simulate refund process
        if ("Completed".equals(paymentStatus)) {
            this.paymentStatus = "Refunded";
            return true;
        } else {
            return false;  // Refund not possible if payment wasn't successful
        }
    }
    @Override
    public String toString(){// class scope return to string 
        return "Payment{" +
                "paymentId='" + payID + '\'' +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                '}' +
                "Pay Date: " +getformatePayment();
    }
}
