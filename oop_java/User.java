import java.util.HashMap;
import java.util.Map;

public class User implements UserInterface {
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

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void displayUserInfo() {
        System.out.println("User ID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
    }

    public User signUp(String name, String email, String address, String password, String phoneNumber, String role) {
        if (users.equals(email)) {
            System.out.println("Email already exists.");
            return null;
        }

        User newUser = new User(name, email, address, password, phoneNumber, role);
        users.put(email, newUser);
        System.out.println("Sign up successful");
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
