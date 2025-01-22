public class Payment {
    private double amount;
    private int day;
    private int month;
    private int year;
    private int payID;

    public Payment(int payID, int day, int month, int year, double amount){
        this.payID=payID;
        this.amount=amount;
        this.day=day;
        this.month=month;
        this.year=year;
    }
    
    public String toString(){
        return "PayID: " + payID + "\n" +
                "Amount: " + amount + "\n" +
                "Pay Date: " + day + "-" + month + "-" + year;
    }
    public static void main(String[] args) {
        Payment lika = new Payment(123, 1, 1, 2025, 12.5);
        System.out.println(lika);
    }
}
