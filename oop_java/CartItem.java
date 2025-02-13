public class CartItem {
    // Private fields to protect data
    private Product product;
    private int quantity;

    // Constructor to initialize fields
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getter and Setter for product
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }
    
    // Optional: toString() for easy printing
    @Override
    public String toString() {
        return "CartItem [product=" + product.getName() + ", quantity=" + quantity + "]";
    }
}

