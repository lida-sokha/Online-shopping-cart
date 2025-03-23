package oop_java;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Customer extends User implements CustomerInterface {
    private HashMap<String, Customer> customerMap = new HashMap<>(); // Store customers using email as key
    private HashMap<String, Integer> cart; // Store productID and quantity
    private List<Payment> paymentHistory; // Store customer's payment history

    public Customer(String name, String email, String address, String password, String phoneNumber) {
        super(name, email, address, password, phoneNumber, "customer");
        this.cart = new HashMap<>();
        this.paymentHistory = new ArrayList<>(); // Initialize payment history
        customerMap.put(email, this); // Store customer in HashMap using email as a unique key
    }

    @Override
    public void viewProductDetails(Product product) {
        System.out.println("Product Name: " + product.getName());
        System.out.println("Price: $" + product.getPrice());
        System.out.println("Quantity: " + product.getQuantity());
        System.out.println("Category: " + product.getCategory());
        System.out.println("Description: " + product.getDescription());
    }


    @Override 
    public void addToCart(String productID, int quantity) {
        // Check if product is in stock
        Product product = findProductById(productID);
        if (product != null){
            int currentQuantity = cart.getOrDefault(productID, 0);
        if (currentQuantity + quantity <= product.getQuantity()) {
            cart.put(productID, currentQuantity + quantity);
            System.out.println("Item added: " + productID);
        } else {
            System.out.println("Not enough stock to add to cart.");
        }
    } else {
        System.out.println("Product not found.");
    }
    }
    @Override 
    public void displayCart() {
        // TODO: Implement cart view for the customer
        if(cart.isEmpty()){
            System.out.println("Cart is empty.");
            return;
        }
        else{
            System.out.println("Cart ID:" + cartId);

        }
    }
    @Override
    public void checkout(Cart cart) {
        // Implement checkout for the customer
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Please add items to cart.");
            return;
        }
        System.out.println("Checking out...");
        double total = cart.calculateTotal();
        System.out.println("Final Total: $" + total);
        // Payment method
        System.out.print("Enter payment method (Credit Card, PayPal, etc.): ");
        String paymentMethod = scanner.nextLine();
        // Payment amount
        System.out.print("Enter payment amount: ");
        double paymentAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        // Make payment
        makePayment(paymentAmount, paymentMethod);
        // Save order to database
        saveOrderToDatabase(cart);
        // Clear cart
        cart.clear();
        System.out.println("Checkout complete! Thank you for your purchase.");
    }

    @Override
    public void addReview(String review) {
        // Implement adding review for the customer
        System.out.println("Adding review...");
        System.out.println("Review: " + review);
        // Add review to database
        addReviewToDatabase(review);
        System.out.println("Review added successfully!");
    }


}
