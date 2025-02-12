import java.util.ArrayList;

public class Seller extends User{
        private ArrayList<String> products;
    
        public Seller(String name, String email, String address, String password, String phoneNumber) {
            super(name, email, address, password, phoneNumber, "seller");
            this.products = new ArrayList<>();
        }
}
