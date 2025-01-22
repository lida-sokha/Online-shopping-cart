public class User {
    private String name;
    // private int userID;
    private String address;
    private String email;
    private String password;
    private String phoneNumber;

    //contructor 
    public User(String name, String email, String address, String password, String phoneNumber){
        this.name=name;
        this.email=email;
        this.address=address;
        this.password=password;
        this.phoneNumber=phoneNumber;
    }
    
    // //getter
    // public String name() {
    //     return name;
    // }
    // public String email(){
    //     return email;
    // }
    // public String address(){
    //     return address;
    // }
    // public String password(){
    //     return password;
    // }
    // public String phoneNumber(){
    //     return phoneNumber;
    // }

    // //setter
    // public void setName(String name){
    //     this.name=name;
    // }
    // public void setEmail(String email){
    //     this.email=email;
    // }
    // public void setAddress(String address){
    //     this.address=address;
    // }
    // public void setPassword(String password){
    //     this.password=password;
    // }
    // public void setPhoneNumber(String phoneNumber){
    //     this.phoneNumber=phoneNumber;
    // }
    public String toString() {
        return "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Address: " + address + "\n" +
                "Phone Number: " + phoneNumber;
    }
    public static void main(String[] args) {
        // Create a User object with valid arguments
        User lida = new User("U001", "Lida", "lida@example.com", "123 Elm Street", "555-1234");
        User lida1 = new User("U002", "Lid1a", "lida@example1.com", "123 1Elm Street", "550-1234");
        // Print the User object
        System.out.println(lida);
        System.out.println(lida1);
    }
    
}
