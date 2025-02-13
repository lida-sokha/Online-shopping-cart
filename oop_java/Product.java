public class Product {
    private String productId;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String description;

    public Product(String productId, String name, double price, int quantity, String category, String description) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
    }

    // Getters (to read the fields)
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    // Update quantity
    public void updateQuantity(int newQuantity) {
        if (newQuantity >= 0) {
            this.quantity = newQuantity;
        } else {
            System.out.println("Quantity cannot be negative!");
        }
    }

    // Sell product (reduce quantity)
    public boolean sell(int quantitySold) {
        if (quantitySold > 0 && quantitySold <= quantity) {
            this.quantity -= quantitySold;
            return true;
        } else {
            System.out.println("Not enough stock to sell.");
            return false;
        }
    }

    // Apply discount
    public void applyDiscount(double percentage) {
        if (percentage > 0 && percentage < 100) {
            this.price = price - (price * (percentage / 100));
        } else {
            System.out.println("Invalid discount percentage.");
        }
    }

    // Check if in stock
    public boolean isInStock(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }

    // toString for easy display
    @Override
    public String toString() {
        return "Product [ID=" + productId + ", Name=" + name + ", Price=" + price + ", Quantity=" + quantity + ", Category=" + category + "]";
    }
}