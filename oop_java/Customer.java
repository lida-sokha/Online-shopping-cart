public class Customer extends User {
    private String name;
    private String email;
    private String address;
    private String password;
    private String phoneNumber;
    
    public Customer(String name, String email, String address, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
