public interface ProductInterface {
    // Define the methods that the Product class must implement
    String getProductId();
    String getName();
    double getPrice();
    int getQuantity();
    String getCategory();
    String getDescription();
    void updateQuantity(int newQuantity);
    boolean sell(int quantitySold);
    void applyDiscount(double percentage);
    boolean isInStock(int requestedQuantity);
    void displayProductInfo();
    
    // Static methods for catalog operations
    static void addProduct(Product product) {}
    static Product getProductById(String productId) { return null; }
    static Product removeProduct(String productId) { return null; }
    static void displayAllProducts() {}
}
