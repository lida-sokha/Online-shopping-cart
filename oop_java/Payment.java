public class Payment {
    double amount;
    int day;
    int month;
    int year;
    int nextpayID=1;
    int payID;

    public Payment(int day, int month, int year, double amount){
        this.payID=nextpayID++;
        this.amount=amount;
        this.day=day;
        this.month=month;
        this.year=year;
    }
    
    public String toString(){
        return "PayID: " + payID + "\n" +
                "Amount: " + amount + "\n" +
                "Pay Date: " +day+ "-"+ month +"-"+ year;
    }
}
