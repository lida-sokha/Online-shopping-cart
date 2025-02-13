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
    

        public boolean signUp(){
            for(User user: users){
                // looking at in the list match the email address of the current user (this) who is trying to sign up?"
                if(user.getEmail().equals(this.email)){
                    System.out.println("Email is ready exit");
                    return false;
                }
            }
            users.add(this);//add current user to the user arraylist
            System.out.println("Sign up successful");
            return true;
        }
        public User login(String email, String password){
            for(User user : users){
                if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                    return user;
                }
            }
            System.out.println("Invalid email or password.");
            return null;
        }
    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                '}';
        }
    }
