package oop_java;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import oop_java.Cart.CartItem;

public class Customer extends User implements CustomerInterface {
    private HashMap<String, Customer> customerMap = new HashMap<>(); // Store customers using email as key
    private HashMap<String, Integer> cart; // Store productID and quantity
    private List<Payment> paymentHistory; // Store customer's payment history
    private static Scanner scanner = new Scanner(System.in);

    public Customer(String name, String email, String address, String password, String phoneNumber) {
        super(name, email, address, password, phoneNumber, "customer");
        this.cart = new HashMap<>();
        this.paymentHistory = new ArrayList<>(); // Initialize payment history
        customerMap.put(email, this); // Store customer in HashMap using email as a unique key
    }

    @Override
    public void viewProductDetails(Product product) {
        System.out.println("Product Name: " + product.getName());
        System.out.println("Price: $" + product.getPrice());
        System.out.println("Quantity: " + product.getQuantity());
        System.out.println("Category: " + product.getCategory());
        System.out.println("Description: " + product.getDescription());
    }


    @Override 
    public void addToCart(String productID, int quantity) {
        // Check if product is in stock
        Product product = findProductById(productID);
        if (product != null){
            int currentQuantity = cart.getOrDefault(productID, 0);
        if (currentQuantity + quantity <= product.getQuantity()) {
            cart.put(productID, currentQuantity + quantity);
            System.out.println("Item added: " + productID);
        } else {
            System.out.println("Not enough stock to add to cart.");
        }
    } else {
        System.out.println("Product not found.");
    }
    }
    
    @Override
    public void displayCart() {
    if (cart.isEmpty()) {
        System.out.println("Cart is empty.");
        return;
    }

    System.out.println("Cart Contents:");
    double total = 0;

    // Iterate over the cart's items (productID and quantity)
    for (String productID : cart.keySet()) {
        Product product = findProductById(productID); // Retrieve product by ID
        int quantity = cart.get(productID);
        if (product != null) {
            double itemTotal = product.getPrice() * quantity; // Calculate item total
            total += itemTotal;
            System.out.println("Product: " + product.getName() + " | Quantity: " + quantity + " | Price: $" + product.getPrice() + " | Subtotal: $" + itemTotal);
        } else {
            System.out.println("Product with ID " + productID + " not found.");
        }
    }

    System.out.println("Total: $" + total);
}


@Override
    public void checkout(Cart cart) {
    // Check if the cart is empty
    if (cart.getItems().isEmpty()) {
        System.out.println("Cart is empty. Please add items to cart.");
        return;
    }

    System.out.println("Checking out...");
    double total = cart.calculateTotal();
    System.out.println("Final Total: $" + total);

    // Payment method
    System.out.print("Enter payment method (Credit Card, PayPal, etc.): ");
    String paymentMethod = scanner.nextLine();

    // Payment amount
    System.out.print("Enter payment amount: ");
    double paymentAmount = scanner.nextDouble();
    scanner.nextLine(); // Consume newline

    // Create Payment object and process payment
    Payment payment = new Payment(paymentAmount, paymentMethod, cart.getUser().getEmail());
    if (payment.processPayment()) {
        System.out.println("Payment successful!");

        // Save order to database
        saveOrderToDatabase(cart);

        // Clear the cart after checkout
        //fix clear function 
        cart.clear();
        System.out.println("Checkout complete! Thank you for your purchase.");
    } else {
        System.out.println("Payment failed. Please try again.");
    }
}

public void saveOrderToDatabase(Cart cart) {
    // SQL query to insert order into the orders table
    String query = "INSERT INTO orders (cartID, userID, totalAmount) VALUES (?, ?, ?)";
    
    // Get a database connection
    try (Connection conn = MySQLConnection.getConnection()) {
        
        // Check if the connection is valid
        if (conn == null) {
            System.out.println("Failed to establish database connection.");
            return;
        }

        // Prepare the statement
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the parameters for the query
            stmt.setInt(1, cart.getCart());  // Set Cart ID
            stmt.setInt(2, cart.getUser().getID());  // Set User ID
            stmt.setDouble(3, cart.calculateTotal());  // Set Total Amount

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            // Check if the order was successfully saved
            if (rowsAffected > 0) {
                System.out.println("Order saved to database.");
            } else {
                System.out.println("Failed to save order.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing the insert statement.");
            e.printStackTrace();
        }

    } catch (SQLException e) {
        // Handle database connection error
        System.out.println("Error connecting to the database.");
        e.printStackTrace();
    }
}

public void addReviewToDatabase(String reviewText) {
    // Example pseudocode, adjust as needed
    String query = "INSERT INTO reviews (userID, productID, reviewText) VALUES (?, ?, ?)";
    
    // Get a database connection
    try (Connection conn = MySQLConnection.getConnection()) {
        
        // Check if the connection is valid
        if (conn == null) {
            System.out.println("Failed to establish database connection.");
            return;
        }
        //not yet create function getCustomer and getProduct    
        // Prepare the statement
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Assuming you have access to the customer and product details
            Customer customer = getCustomer(); // Get current customer (assuming this is implemented)
            Product product = getProduct(); // Get current product (assuming this is implemented)

            // Set the parameters for the query
            stmt.setInt(1, customer.getID());  // Set the User ID
            stmt.setString(2, product.getProductId());  // Set the Product ID
            stmt.setString(3, reviewText);  // Set the Review Text

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            // Check if the review was successfully added
            if (rowsAffected > 0) {
                System.out.println("Review added to database.");
            } else {
                System.out.println("Failed to add review.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing the insert statement.");
            e.printStackTrace();
        }

    } catch (SQLException e) {
        // Handle database connection error
        System.out.println("Error connecting to the database.");
        e.printStackTrace();
    }
}


    @Override
    public void addReview(String review) {
        // Implement adding review for the customer
        System.out.println("Adding review...");
        System.out.println("Review: " + review);
        // Add review to database
        addReviewToDatabase(review);
        System.out.println("Review added successfully!");
    }
//error not yet fix
    @Override
    public Product findProductById(String productID) {
        // Assuming a product list exists
        for (Product product : Product.getAllProducts()) {
            if (product.getProductId().equals(productID)) {
                return product;
            }
        }
        return null;
    }

}
