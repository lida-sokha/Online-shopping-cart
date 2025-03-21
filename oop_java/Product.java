package oop_java;
import java.util.HashMap;

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
            cart.addItem(product, quantity);
            product.setQuantity(product.getQuantity() - quantity);  // Decrease quantity in stock
        } else {
            System.out.println("Not enough stock to add to cart.");
        }
    }
    public static void viewCart(Cart cart) {
        cart.displayCart();
    }

    public static void checkout(Cart cart) {
        cart.checkout();
    }

    public static void saveCartToFile(Cart cart, String filename) {
        cart.saveCartToFile(filename);
    }
}
