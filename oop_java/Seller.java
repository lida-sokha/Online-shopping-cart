public class Seller extends User{
    private String storeName;
    public Seller(String name, String email, String address, String password, String phoneNumber, String storeName) {
        this.storeName = storeName;
        this.email=email;
    }
}