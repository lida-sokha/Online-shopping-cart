import java.util.HashMap;

public class Product {
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

    // Getters
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

    // Customer functions

    // View product details
    public void viewProductDetails() {
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Price: $" + price);
        System.out.println("Available Quantity: " + quantity);
        System.out.println("Category: " + category);
        System.out.println("Description: " + description);
    }

    // Simulate adding a product to cart
    public void addToCart() {
        System.out.println(name + " has been added to your cart.");
    }

    // Simulate purchasing a product
    public boolean purchaseProduct(int quantityToBuy) {
        if (quantityToBuy > 0 && quantityToBuy <= quantity) {
            this.quantity -= quantityToBuy;
            System.out.println("You have successfully purchased " + quantityToBuy + " of " + name + ".");
            return true;
        } else {
            System.out.println("Not enough stock available.");
            return false;
        }
    }

    // Leave a review
    public void leaveReview(String review) {
        System.out.println("Review for " + name + ": " + review);
    }

    // Static methods for catalog management
    public static void addProduct(Product product) {
        productCatalog.put(product.getProductId(), product);
    }

    public static Product getProductById(String productId) {
        return productCatalog.get(productId);
    }

    public static Product removeProduct(String productId) {
        return productCatalog.remove(productId);
    }

    public static void displayAllProducts() {
        for (Product product : productCatalog.values()) {
            System.out.println(product);
        }
    }

    @Override
    public String toString() {
        return "Product [ID=" + productId + ", Name=" + name + ", Price=" + price + ", Quantity=" + quantity + ", Category=" + category + "]";
    }
}
