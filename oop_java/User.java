import java.util.ArrayList;
import java.util.Scanner;
    public class User {
        private String name;
        private static int nextuserID=0;
        private int userID;
        private String address;
        private String email;
        private String password;
        private String phoneNumber;
        private String role; // staff or customer

        private static ArrayList<User> users= new ArrayList<>();
        //Constructor
        public User(String name, String email, String address, String password, String phoneNumber, String role){
            this.userID=++nextuserID;
            this.name=name;
            this.email=email;
            this.address=address;
            this.password=password;
            this.phoneNumber=phoneNumber;
            this.role=role;
        }
        public String getEmail() {
            return email;
        }
        public String getPassword() {
            return password;
        }
        public String getRole() {
            return role;
        }
            //log in
        public boolean signup(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine();

            for(User user : users){
                if(user.getEmail().equals(email)){
                    System.out.println("User with this email already exists! Try again.");
                    return false;
                }
            }
            String role;
            if(email.endsWith("@company.com")){
                role="staff";
            }
            else{
                role="customer";
            }
            User newUser = new User(name, email, address, password, phoneNumber, role);
            users.add(newUser);
            System.out.println("User registered successfully as" + newUser.getRole());

            return true;
        }
        //login 
        public boolean login(String email, String password){
            for(User user : users){
                if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                    return true;
                }
            }
            System.out.println("Invalid email or password!");
            return false;
        }
    }
