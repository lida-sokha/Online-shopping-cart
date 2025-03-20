package oop_java;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Seller extends User implements SellerInterface {
    private ArrayList<String> productIdList;

    public Seller(String name, String email, String address, String password, String phoneNumber) {
        super(name, email, address, password, phoneNumber, "Seller");
        this.productIdList = new ArrayList<>();
    }
//store the seller in array list

// Getter for email (used as the key for the HashMap)
public String getEmail() {
    return super.getEmail();
}

@Override
    public String toString() {
        return "Seller: " + getName() + " | Email: " + getEmail();
    }

    @Override
    public void addProduct(String productId, String name, double price, int quantity, String category, String description) {
        if (Product.getProductById(productId) != null) {
            System.out.println("Product already exists. Please add a different product.");
        } else {
            Product newProduct = new Product(productId, name, price, quantity, category, description);
            productIdList.add(productId);
            System.out.println(name + " has been added successfully!");
        }
    }

    @Override
    public void removeProduct(String productId) {
        if (productIdList.contains(productId)) {
            Product removedProduct = Product.removeProduct(productId);
            if (removedProduct != null) {
                productIdList.remove(productId);
                System.out.println("Product has been removed.");
            } else {
                System.out.println("Failed to remove the product.");
            }
        } else {
            System.out.println("Product not found in your catalog.");
        }
    }

    @Override
    public void checkProduct(String productId) {
        Product product = Product.getProductById(productId);
        if (product != null) {
            product.viewProductDetails();
        } else {
            System.out.println("Product not found.");
        }
    }

    @Override
    public void sellProduct(String productId, int quantitySold) {
        Product product = Product.getProductById(productId);
        if (product != null && productIdList.contains(productId)) {
            if (product.sell(quantitySold)) {
                System.out.println(quantitySold + " units of " + product.getName() + " sold.");
            } else {
                System.out.println("Not enough stock to sell.");
            }
        } else {
            System.out.println("Product not found or you don't have permission to sell this product.");
        }
    }
@Override
public void displayMyProducts() {
    try (Connection conn = MySQLConnection.getConnection()) {
        // Query to fetch products based on seller_id
        String sql = "SELECT * FROM products WHERE seller_id = (SELECT id FROM user WHERE email = ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, this.getEmail()); // Use current seller's email to find their products
        ResultSet rs = pstmt.executeQuery();

        System.out.println("\nProduct list:");
        System.out.println("ID | Name | Price | Quantity | Category | Description");

        while (rs.next()) {
            // Displaying the products of the current seller
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            String category = rs.getString("category");
            String description = rs.getString("description");

            // Print product details
            System.out.println(id + " | " + name + " | " + price + " | " + quantity + " | " + category + " | " + description);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        System.out.println("Error fetching products from the database.");
    }
}
    public static class SellerDirectory {
        private Map<String, Seller> sellers = new HashMap<>();

        public void addSeller(Seller seller) {
            if (sellers.containsKey(seller.getEmail())) {
                System.out.println("Seller with this email already exists.");
            } else {
                sellers.put(seller.getEmail(), seller);
                System.out.println("Seller " + seller.getName() + " added successfully.");
            }
        }

        public Seller getSeller(String email) {
            Seller seller = sellers.get(email);
            if (seller != null) {
                return seller;
            } else {
                System.out.println("Seller not found.");
                return null;
            }
        }

        public void removeSeller(String email) {
            if (sellers.containsKey(email)) {
                sellers.remove(email);
                System.out.println("Seller removed.");
            } else {
                System.out.println("Seller not found.");
            }
        }

        @Override
        public String toString() {
            return "SellerDirectory{" + "sellers=" + sellers + '}';
        }
    }
}