package oop_java;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Product{
    private String productId;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String description;

    private static HashMap<String, Product> productCatalog = new HashMap<>();

    public Product(String productId, String name, double price, int quantity, String category, String description) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
        productCatalog.put(productId, this);
    }

    public void viewProductDetails() {
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Price: $" + price);
        System.out.println("Quantity: " + quantity);
        System.out.println("Category: " + category);
        System.out.println("Description: " + description);
    }

    public boolean sellProduct(int quantitySold) {
        if (quantitySold > 0 && quantitySold <= quantity) {
            this.quantity -= quantitySold;  // Reduce stock
            return true;
        } else {
            System.out.println("Not enough stock to sell.");
            return false;
        }
    }

    public void addReview(String review) {
        System.out.println("Review for " + name + ": " + review);
    }

    // Getter and Setter methods
    public String getProductId() {
        return productId;
    }


    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getCategory() {
        return category;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public static void addProduct(Product product) {
        productCatalog.put(product.productId, product);
    }

    public static void removeProduct(String productId) {
        productCatalog.remove(productId);
    }
    // Adding product to the cart
    public static void addToCart(Cart cart, Product product, int quantity) {
        if (quantity <= product.getQuantity()) {
            cart.addProduct(product);
            product.setQuantity(product.getQuantity() - quantity);  // Decrease quantity in stock
        } else {
            System.out.println("Not enough stock to add to cart.");
        }
    }

    public static void checkout(Cart cart) {
        cart.checkout();
    }   

    public static void saveCartToFile(Cart cart, String filename) {
        cart.saveCartToFile(filename);
    }
    public Product findProductById(String productID) {
        for (Product product : productCatalog.values()) {  // Correct way to access values of the map
            if (product.getProductId().equals(productID)) {
                return product;  // Product found
            }
        }
        return null;  // Product not found
    }
    public Product searchProductByName(String productName) {
    // SQL query to search for products by name
    String query = "SELECT * FROM products WHERE name LIKE ?";

    try (Connection conn = MySQLConnection.getConnection()) {
        if (conn == null) {
            System.out.println("Failed to establish database connection.");
            return null;
        }

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Use LIKE for partial matching (case-insensitive search)
            stmt.setString(1, "%" + productName + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                // Check if any product matches
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
                    System.out.println("No product found with the name: " + productName);
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

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
