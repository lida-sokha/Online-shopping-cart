public class Customer extends User {
    public Customer(String name, String email, String password, String phoneNumber){
        super(name, email, email, password, phoneNumber, phoneNumber);  
    }
    @Override
    public String getRole(){
        return "Customer";
    }
}