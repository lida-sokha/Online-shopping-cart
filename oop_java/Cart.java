import java.util.ArrayList;
import java.util.List;

public class Cart {
    // Inner class representing an item in the cart
    private class CartItem {
        private String productId;
        private String name;
        private double price;
        private int quantity;

        public CartItem(String productId, String name, double price, int quantity) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public double getSubtotal() {
            return price * quantity;
        }

        @Override
        public String toString() {
            return "Product ID: " + productId +
                   ", Name: " + name +
                   ", Price: $" + price +
                   ", Quantity: " + quantity +
                   ", Subtotal: $" + getSubtotal();
        }
    }

    // List to store CartItems
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    // Add an item to the cart
    public void addItem(String productId, String name, double price, int quantity) {
        items.add(new CartItem(productId, name, price, quantity));
    }

    // Remove an item from the cart by product ID
    public void removeItem(String productId) {
        items.removeIf(item -> item.productId.equals(productId));
    }

    // Calculate the total price of all items in the cart
    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Print all items in the cart
    public void printCart() {
        if (items.isEmpty()) {
            System.out.println("The cart is empty.");
        } else {
            System.out.println("Cart Items:");
            for (CartItem item : items) {
                System.out.println(item);
            }
            System.out.println("Total Price: $" + calculateTotal());
        }
    }
}
