package oop_java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
public class Test {
    private static Scanner scanner = new Scanner(System.in);
    

    public static void main(String[] args) {
        MySQLConnection.getConnection();

        while (true) {
            System.out.println("\n1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline 

            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                login();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void signUp() {
    System.out.println("\nSign Up as:");
    System.out.println("1. Seller");
    System.out.println("2. Customer");
    System.out.print("Choose a role: ");
    int roleChoice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    System.out.print("Enter name: ");
    String name = scanner.nextLine();
    System.out.print("Enter email: ");
    String email = scanner.nextLine();
    System.out.print("Enter address: ");
    String address = scanner.nextLine();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();
    
    // Validate phone number
    String phoneNumber = "";
    while (true) {
        System.out.println("Enter phone number (9 digits): ");
        phoneNumber = scanner.nextLine();
        if (phoneNumber.length() == 9 && phoneNumber.matches("[0-9]+")) {
            break;
        }
        System.out.println("Invalid phone number. Please enter exactly 9 digits.");
    }

    String userType = (roleChoice == 1) ? "seller" : "customer";

    // Save user to database
    try (Connection conn = MySQLConnection.getConnection()) {
        String sql = "INSERT INTO user (fullname, email, password, phone_number, address, user_type) VALUES (?, ?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password); // Consider hashing the password
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, address);
            pstmt.setString(6, userType);
            pstmt.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error saving user to database.");
        return;
    }

    System.out.println("Sign-up successful as a " + userType + "!");

    // Navigate to appropriate menu
    if (roleChoice == 1) {
        Seller seller = new Seller(name, email, address, password, phoneNumber);
        sellerMenu(seller);
    } else {
        Customer customer = new Customer(name, email, address, password, phoneNumber);
        customerMenu(customer);
    }
}

    
private static void login() {
    System.out.print("\nEnter email: ");
    String email = scanner.nextLine();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();

    try (Connection conn = MySQLConnection.getConnection()) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    if (storedPassword.equals(password)) { // Compare passwords
                        String name = rs.getString("fullname");
                        String userType = rs.getString("user_type");

                        System.out.println("Login successful! Welcome, " + name);

                        if (userType.equals("seller")) {
                            Seller seller = new Seller(name, email, rs.getString("address"), password, rs.getString("phone_number"));
                            sellerMenu(seller);
                        } else {
                            Customer customer = new Customer(name, email, rs.getString("address"), password, rs.getString("phone_number"));
                            customerMenu(customer);
                        }
                    } else {
                        System.out.println("Incorrect password. Please try again.");
                    }
                } else {
                    System.out.println("No user found with email: " + email);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Database error during login.");
    }
}

    private static void sellerMenu(Seller seller) {
        while (true) {
            System.out.println("\nSeller Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Remove Product");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    seller.addProduct(null);
                    break;
                case 2:
                    seller.viewAllProducts();
                    break;
                case 3:
                    seller.removeProduct(null);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }


    private static void customerMenu(Customer customer) {
        while(true){
        System.out.println("\nCustomer Menu:");
        System.out.println("1. View Products");
        System.out.println("2. Buy Product");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // Assuming a method to view all available products
                customer.viewProductDetails(null);
                break;
            case 2:
            //need to check again 
            while(true){
                System.out.print("Enter product name to buy: ");
                String productId = scanner.nextLine();
                customer.searchProductByName(null);
                switch (choice) {
                    case 1:
                        //make to the payment 
                        System.out.print("Enter quantity to buy: ");
                            int quantityToBuy = scanner.nextInt(); 
                            scanner.nextLine(); // Consume the newline

                            boolean purchaseSuccess = customer.addToCart(productId, quantityToBuy);

                            if (purchaseSuccess) {
                                System.out.print("Enter payment amount: ");
                                double amount = scanner.nextDouble();
                                scanner.nextLine(); // Consume newline

                                System.out.print("Enter payment method (Credit Card, PayPal, etc.): ");
                                String paymentMethod = scanner.nextLine();

                                customer.makePayment();
                            } else {
                                System.out.println("Purchase failed. Either product not found or insufficient stock.");
                            }
                        break;
                        case 2:
                    // Add to cart first and then they have a choice to keep in the cart or make the payment
                    System.out.print("Enter quantity to add to cart: ");
                    int quantityToAdd = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline

                    // Call the addToCart method to add the product to the cart
                    boolean addSuccess = customer.addToCart(productId, quantityToAdd);  // Make sure addToCart returns a boolean

                    if (addSuccess) {
                        System.out.println("Product added to cart successfully.");
                        System.out.println("1. Make payment for items in cart");
                        System.out.println("2. Keep in cart");
                        System.out.print("Choose an option: ");
                        int paymentChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        if (paymentChoice == 1) {
                            // Ask for the payment amount
                            System.out.print("Enter payment amount: ");
                            double amount = scanner.nextDouble();
                            scanner.nextLine(); // Consume newline

                            // Ask for the payment method
                            System.out.print("Enter payment method (Credit Card, PayPal, etc.): ");
                            String paymentMethod = scanner.nextLine();

                            // Make the payment using the customer object
                            customer.checkout(null);
                        }
                        else if(paymentChoice == 2) {
                            System.out.println("Items are still in the cart.");
                        }
                        else {
                            System.out.println("Invalid choice. Try again.");
                        }
                    } else {
                        System.out.println("Add to cart failed. Either product not found or insufficient stock.");
                    }
                    break;
                
            }
        //     case 3:
        //         System.out.println("Logging out...");
        //         return;
    }
    default:
        System.out.println("Invalid choice. Try again.");
        }
    }
    }
}
