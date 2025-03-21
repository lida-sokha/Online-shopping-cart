package oop_java;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Cart {
    private String cartId;
    private Customer user; // Ensure Cart is linked to Customer
    private ArrayList<CartItem> items;

    public Cart(Customer user) {
        this.cartId = "CART-" + user.getEmail();
        this.user = user;
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            System.out.println("Not enough stock available for " + product.getName());
            return;
        }

        CartItem item = new CartItem(product, quantity);
        items.add(item);
        System.out.println("Added " + quantity + " x " + product.getName() + " to cart.");
    }

    public boolean removeItem(String productName) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equalsIgnoreCase(productName)) {
                items.remove(item);
                System.out.println("Removed " + item);
                return true;
            }
        }
        System.out.println("Item not found!");
        return false;
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("Cart ID: " + cartId);
        System.out.println("User: " + user.getEmail());
        System.out.println("Items:");
        for (CartItem item : items) {
            System.out.println(item);
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public void checkout() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty. Cannot checkout.");
            return;
        }

        double totalAmount = calculateTotal();

        for (CartItem item : items) {
            item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity());
        }

        Payment payment = new Payment(user.getEmail(), totalAmount);
        System.out.println("Payment of $" + totalAmount + " processed!");

        items.clear();
        System.out.println("Checkout complete!");
    }

    public void saveCartToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Cart ID: " + cartId + "\n");
            writer.write("User ID: " + user.getEmail() + "\n");
            writer.write("Items Purchased:\n");

            for (CartItem item : items) {
                writer.write(item.getProduct().getName() + " - Quantity: " + item.getQuantity() + "\n");
            }

            writer.write("Total Price: $" + calculateTotal() + "\n");
            writer.write("Checkout Date: " + java.time.LocalDate.now() + "\n");

            System.out.println("Cart saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving cart: " + e.getMessage());
        }
    }

    public class CartItem {
        private Product product;
        private int quantity;

        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        @Override
        public String toString() {
            return product.getName() + " (x" + quantity + ")";
        }
    }
}
