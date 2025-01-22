public class CartItem {
    private Product product;
    private int quantity;

    // Constructor
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    // Setter
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Calculate total price for this item
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
