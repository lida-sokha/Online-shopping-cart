import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class User{
    private String name;
    private static int nextUserID = 0;
    private int userID;
    private String address;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;

    private static Map<String, User> users = new HashMap<>();

    // Constructor
    public User(String name, String email, String address, String password, String phoneNumber, String role) {
        this.userID = ++nextUserID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void displayUserInfo() {
        System.out.println("User ID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
    }

    public User signUp() {
        Scanner scanner = new Scanner(System.in);
    
        // Ask for role first
        String role = "";
        while (true) {
            System.out.println("Enter role (staff/customer): ");
            role = scanner.nextLine().toLowerCase();
    
            if (role.equals("staff") || role.equals("customer")) {
                break;
            } else {
                System.out.println("Invalid role. Please enter 'staff' or 'customer'.");
            }
        }
    
        // Getting the user input after role selection
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        
        // Check if the email already exists
        if (users.containsKey(email)) {
            System.out.println("Email already exists.");
            return null;
        }
    
        System.out.println("Enter address: ");
        String address = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
    
        // Create the new user
        User newUser = new User(name, email, address, password, phoneNumber, role);
        users.put(email, newUser);
        System.out.println("Sign up successful as " + role);
    
        return newUser;
    }
    

    public User login(String email, String password) {
        User user = users.get(email);
        if (user != null && user.password.equals(password)) {
            return user;
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
