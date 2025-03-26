package oop_java;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

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
    public boolean addToCart(String productId, int quantity) {
        // Check if the product is available
        Product product = findProductById(productId);
        if (product != null) {
            int currentQuantity = cart.getOrDefault(productId, 0);
            if (currentQuantity + quantity <= product.getQuantity()) {
                cart.put(productId, currentQuantity + quantity); // Add the product to the cart
                return true; // Successfully added
            } else {
                System.out.println("Not enough stock to add to cart.");
            }
        } else {
            System.out.println("Product not found.");
        }
        return false; // If something went wrong (product not found, stock issue)
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


public void checkout() {
    if (cart.isEmpty()) {
        System.out.println("Your cart is empty");
        return;
    }

    // Calculate total
    double total = 0;
    for (Map.Entry<String, Integer> entry : cart.entrySet()) {
        Product product = findProductById(entry.getKey());
        if (product != null) {
            total += product.getPrice() * entry.getValue();
        }
    }

    System.out.printf("Proceed with checkout?%n%nTotal: $%.2f%n", total);
    System.out.print("Confirm (yes/no): ");
    String input = scanner.nextLine().trim().toLowerCase();

    if (input.equals("yes")) {  // Fixed missing parenthesis
        boolean paymentSuccess = makePayment();
        
        if (paymentSuccess) {
            System.out.println("Payment successful! Order completed.");
            saveOrderToDatabase(total);  // Pass the total amount
            cart.clear();
        } else {
            System.out.println("Payment failed. Please try again.");
        }
    } else {
        System.out.println("Checkout cancelled.");
    }
}

// Updated saveOrderToDatabase method
public void saveOrderToDatabase(double totalAmount) {
    String query = "INSERT INTO orders (customer_id, total_amount, order_date) VALUES (?, ?, ?)";
    
    try (Connection conn = MySQLConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, this.getID());
        stmt.setDouble(2, totalAmount);
        stmt.setString(3, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        int rowsAffected = stmt.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Order saved to database successfully.");
            saveOrderItems();  // Save individual items
        } else {
            System.out.println("Failed to save order.");
        }
    } catch (SQLException e) {
        System.out.println("Database error: " + e.getMessage());
        e.printStackTrace();
    }
}

private void saveOrderItems() throws SQLException {
    String query = "INSERT INTO order_items (order_id, product_id, quantity, price) " +
                   "SELECT LAST_INSERT_ID(), ?, ?, price FROM products WHERE product_id = ?";
    
    try (Connection conn = MySQLConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            stmt.setString(1, entry.getKey());
            stmt.setInt(2, entry.getValue());
            stmt.setString(3, entry.getKey());
            stmt.addBatch();
        }
        
        stmt.executeBatch();
        System.out.println("Order items saved successfully.");
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
public Customer getCustomerById(int customerId) {
    // Query to retrieve the customer details from the database
    String query = "SELECT * FROM customers WHERE customerID = ?";

    try (Connection conn = MySQLConnection.getConnection()) {
        if (conn == null) {
            System.out.println("Failed to establish database connection.");
            return null;
        }

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);  // Set the customer ID

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Assuming you have a Customer class with a constructor to initialize the customer details
                    return new Customer(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("password"),
                        rs.getString("phoneNumber")
                    );
                } else {
                    System.out.println("Customer not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing the query.");
            e.printStackTrace();
            return null;
        }
    } catch (SQLException e) {
        System.out.println("Error connecting to the database.");
        e.printStackTrace();
        return null;
    }
}

public Product getProductById(String productID) {
    // Query to retrieve the product details from the database
    String query = "SELECT * FROM products WHERE productID = ?";

    try (Connection conn = MySQLConnection.getConnection()) {
        if (conn == null) {
            System.out.println("Failed to establish database connection.");
            return null;
        }

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productID);  // Set the product ID

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Assuming you have a Product class with a constructor to initialize the product details
                    return new Product(
                        rs.getString("productID"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category"),
                        rs.getString("description")
                    );
                } else {
                    System.out.println("Product not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing the query.");
            e.printStackTrace();
            return null;
        }
    } catch (SQLException e) {
        System.out.println("Error connecting to the database.");
        e.printStackTrace();
        return null;
    }
}

public void addReviewToDatabase(int customerId, String productID, String reviewText) {
    String query = "INSERT INTO reviews (userID, productID, reviewText) VALUES (?, ?, ?)";
    
    try (Connection conn = MySQLConnection.getConnection()) {
        if (conn == null) {
            System.out.println("Failed to establish database connection.");
            return;
        }

        // Get the customer by ID and the product by ID
        Customer customer = getCustomerById(customerId);
        Product product = getProductById(productID);

        if (customer == null || product == null) {
            System.out.println("Customer or Product not found.");
            return;
        }

        // Prepare the statement
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customer.getID());  // Set the User ID
            stmt.setString(2, product.getProductId());  // Set the Product ID
            stmt.setString(3, reviewText);  // Set the Review Text

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

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
        System.out.println("Error connecting to the database.");
        e.printStackTrace();
    }
}


@Override
public void addReview(String review) {
    System.out.println("Adding review...");
    System.out.println("Review: " + review);

    // Pass the customerId and productID to the method
    int customerId = this.getID();  // Assuming this refers to the current customer
    String productID = "exampleProductId";  // Set the product ID as needed

    // Add review to database
    addReviewToDatabase(customerId, productID, review);
    System.out.println("Review added successfully!");
}

    @Override
    public Product findProductById(String productID) {
        Product product = getProductById(productID);
        if(product != null){
            return product;
        }
        return null;
    }
    @Override
    public Product searchProductByName(String productName) {
        Product product = searchProductByName(productName);
        if(product != null){
            return product;
        }
        return null;
    }
    @Override
    public boolean makePayment() {
        return false;
    }
}

