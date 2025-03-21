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
    private static HashMap<String, Product> productCatalog = new HashMap<>();

    public Seller(String name, String email, String address, String password, String phoneNumber) {
        super(name, email, address, password, phoneNumber, "Seller");
    }

    // Add a product to the catalog
    @Override
    public void addProduct(Product product) {
        productCatalog.put(product.getProductId(), product);
        System.out.println("Product added: " + product.getName());
    }

    // Remove a product from the catalog
    @Override
    public void removeProduct(String productId) {
        if (productCatalog.containsKey(productId)) {
            Product product = productCatalog.remove(productId);
            System.out.println("Product removed: " + product.getName());
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    // View details of a specific product
    @Override
    public void viewProductDetails(String productId) {
        Product product = productCatalog.get(productId);
        if (product != null) {
            product.viewProductDetails();
        } else {
            System.out.println("Product not found.");
        }
    }

    // Sell a product
    @Override
    public boolean sellProduct(String productId, int quantitySold) {
        Product product = productCatalog.get(productId);
        if (product != null) {
            return product.sellProduct(quantitySold);
        } else {
            System.out.println("Product not found.");
            return false;
        }
    }

    // View all products in the catalog
    @Override
    public void viewAllProducts() {
        if (productCatalog.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : productCatalog.values()) {
                product.viewProductDetails();
            }
        }
    }
    
    
    @Override
    public String toString() {
        return "Seller{" +
                "Name='" + getName() + '\'' +
                ", Email='" + getEmail() + '\'' +
                ", Address='" + getAddress() + '\'' +
                ", Phone='" + getPhoneNumber() + '\'' +
                ", Products=" + productCatalog.values() +
                '}';
    }
}