import java.util.ArrayList;

public class Customer extends User {
    public Customer(String name, String email, String address, String password, String phoneNumber) {
        super(name, email, address, password, phoneNumber, "customer"); 
    }
}
