public class User {
    private String name;
    private static int nextuserID=1;
    private int userID;
    private String address;
    private String email;
    private String password;
    private String phoneNumber;

    //Constructor
    public User(String name, String email, String address, String password, String phoneNumber){
        this.userID = nextuserID++;
        this.name=name;
        // this.email=email;
        setEmail(email);
        this.address=address;
        // this.password=password;
        setPassword(password);
        this.phoneNumber=phoneNumber;
    }
    
    //getter= use to access to private class 
    public String name() {
        return name;
    }
    public String email(){
        return email;
    }
    public String address(){
        return address;
    }
    public String password(){
        return password;
    }
    public String phoneNumber(){
        return phoneNumber;
    }

    //setter are essential for providing controlled modification of the private fields in the User class
    public void setName(String name){
        this.name=name;
    }
    public void setEmail(String email) {
        if (email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email address!");
        }
    }    
    public void setAddress(String address){
        this.address=address;
    }
    public void setPassword(String password){
        if(password.length()>=8){
            this.password=password;
        }
        else{
            throw new IllegalArgumentException("Password must be at least 8 characters long!");
        }
    }
    public void setPhoneNumber(String phoneNumber){
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
