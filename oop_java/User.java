public class User {
    String name;
    int nextuserID=1;
    int userID;
    String address;
    String email;
    String password;
    String phoneNumber;

    //Constructor
    public User(String name, String email, String address, String password, String phoneNumber){
        this.userID = nextuserID++;
        this.name=name;
        this.email=email;
        this.address=address;
        this.password=password;
        this.phoneNumber=phoneNumber;
    }
    public String toString() {
        return  "UserID: " + userID + "\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Address: " + address + "\n" +
                "Password: " + password + "\n" +
                "Phone Number: " + phoneNumber;
    }

}
