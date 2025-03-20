package oop_java;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class User{
    private String name;
    private int userID;
    private String address;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;

    private static Map<String, User> users = new HashMap<>();

    // Constructor
    User(String name, String email, String address, String password, String phoneNumber, String role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public String getRole()
    {
        return role;
    }
    public String getPassword() {
        return password;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress(){
        return address;
    }
    public int getID(){
        return userID;
    }
    public User signUp() {
        Scanner scanner = new Scanner(System.in);
    
        // Ask for role first
        String role;
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

        // Validate phone number
        System.out.println("Enter phone number:");
        String phoneNumber = scanner.nextLine();
        
        // Create the new user
        User newUser;
        if(role.equals("seller")){
            newUser = new Seller(name, email, address, password, phoneNumber);
        }
        else{
            newUser = new Customer(name, email, address, password, phoneNumber);
        }
        users.put(email, newUser);
        System.out.println("Sign up successful as " + role);
        return newUser;
    }
    
    public static void saveUserToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true))) {
            // Format: Role: Seller, Name: lida, Email: lida@gmail.com, Address: pp, Password: 12345, Phone: 061753730
            String role = (user instanceof Seller) ? "Seller" : "Customer";
            writer.write("Role: " + role + ", Name: " + user.getName() + ", Email: " + user.getEmail() + ", Address: " +
                            user.getAddress() + ", Password: " + user.getPassword() + ", Phone: " + user.getPhoneNumber());
            writer.newLine();
            System.out.println("User saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving user to file: " + e.getMessage());
        }
    }
    
    public User login(String email, String password) {
        String fileName = "user.txt"; // File where users are stored
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(","); // Split by comma
            
            if (userData.length >= 6) { // Ensure data is complete
                String role = userData[0].split(":")[1].trim();
                String name = userData[1].split(":")[1].trim();
                String storedEmail = userData[2].split(":")[1].trim(); // Email is at the 3rd position
                String storedPassword = userData[4].split(":")[1].trim(); // Password is at the 5th position

                 // Debugging prints to verify what is being compared
                    System.out.println("Trying to log in with email: " + email + " and password: " + password);
                    System.out.println("Stored email: " + storedEmail + " and password: " + storedPassword);

                if (email.equals(storedEmail) && password.equals(storedPassword)) {
                        String address = userData[3].split(":")[1].trim(); // Address is at the 4th position
                        String phoneNumber = userData[5].split(":")[1].trim(); // Phone is at the 6th position
                     // Debugging: Print success
                        System.out.println("User found: " + name + " (" + role + ")");

                        // Create and return the corresponding user object
                        if (role.equalsIgnoreCase("seller")) {
                            return new Seller(name, email, address, password, phoneNumber);
                        } else {
                            return new Customer(name, email, address, password, phoneNumber);
                        }
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading user file: " + e.getMessage());
    }

    System.out.println("Invalid email or password.");
    return null;
    }

    public static void loadUsersFromFile() {
        String fileName = "user.txt"; // File where users are stored
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(","); // Split by comma
    
                // Check if the line contains enough data
                if (userData.length >= 6) {
                    // Extract user information and trim extra spaces
                    String role = userData[0].split(":")[1].trim();
                    String name = userData[1].split(":")[1].trim();
                    String email = userData[2].split(":")[1].trim();
                    String address = userData[3].split(":")[1].trim();
                    String password = userData[4].split(":")[1].trim();
                    String phoneNumber = userData[5].split(":")[1].trim();
    
                    // Create the appropriate user object
                    User user;
                    if (role.equalsIgnoreCase("Seller")) {
                        user = new Seller(name, email, address, password, phoneNumber);
                    } else {
                        user = new Customer(name, email, address, password, phoneNumber);
                    }
    
                    // Store the user in the map
                    users.put(email, user);
                }
            }
    
            // Debugging: Print all loaded users
            System.out.println("Loaded users:");
            for (String emailKey : users.keySet()) {
                System.out.println("User: " + users.get(emailKey).getName() + " Email: " + emailKey);
            }
    
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
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
