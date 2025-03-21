package oop_java;
public interface SellerInterface {
    void addProduct(Product product);       // Add a product to the catalog
    void removeProduct(String productId);   // Remove a product from the catalog
    void viewProductDetails(String productId); // View details of a specific product
    boolean sellProduct(String productId, int quantitySold); // Sell a product
    void viewAllProducts(); // View all products in the catalog
}
