package oop_java;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Cart {
    private int cartId;
    private User user;
    private ArrayList<CartItem> items;
    private static int nextCartId = 1;

    public Cart(User user) {
        this.cartId = nextCartId++;
        this.user = user;
        this.items = new ArrayList<>();
    }
    public int getCartId() {
        return cartId;
    }

    public User getUser() {
        return user;
    }

    public void addProduct(Product product){
        items.add(new CartItem(product, 1));
        System.out.println("Item added: " + product.getName());
    }

    public boolean removeItem(String productName) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equalsIgnoreCase(productName)) {
                items.remove(item);
                System.out.println("Item removed: " + item);
                return true;
            }
        }
        System.out.println("Item not found!");
        return false;
    }


    public CartItem selectCartItem(String productName) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equalsIgnoreCase(productName)) {
                return item;
            }
        }
        return null;
    }

    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("Cart ID: " + cartId);
        System.out.println("User ID: " + user.getID());
        System.out.println("Items:");
        for (CartItem item : items) {
            System.out.println(item);
        }
    }


    public void saveCartToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Cart ID: " + cartId + "\n");
            writer.write("User ID: " + user.getID() + "\n");
            writer.write("Items in Cart:\n");

            for (CartItem item : items) {
                writer.write(item.getProduct().getName() + " - Quantity: " + item.getQuantity() + "\n");
            }

            writer.write("Total Price: $" + calculateTotal() + "\n");
            System.out.println("Cart saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving cart: " + e.getMessage());
        }
    }

    public double applyDiscount(double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            System.out.println("Invalid discount percentage!");
            return calculateTotal();
        }

        double discountAmount = calculateTotal() * (discountPercentage / 100);
        double discountedTotal = calculateTotal() - discountAmount;
        System.out.println("Discount applied: " + discountPercentage + "% (-$" + discountAmount + ")");
        return discountedTotal;
    }

    public void checkout() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty. Add items before checking out.");
            return;
        }

        System.out.println("Checking out...");
        displayCart();
        System.out.println("Final Total: $" + calculateTotal());
        
        items.clear();
        System.out.println("Checkout complete! Thank you for your purchase.");
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

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        @Override
        public String toString() {
            return "CartItem [product=" + product.getName() + ", quantity=" + quantity + "]";
        }
    }
}
