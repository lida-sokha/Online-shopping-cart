public interface SellerInterface {
    void addProduct(String productId, String name, double price, int quantity, String category, String description);
    void removeProduct(String productId);
    void checkProduct(String productId);
    void sellProduct(String productId, int quantitySold);
    void displayMyProducts();
}
