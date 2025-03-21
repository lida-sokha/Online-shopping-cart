package oop_java;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public void viewProductDetails(){
        System.out.println("Viewing product details...");
    }
    
    @Override 
    public void addToCart(Cart cart, Product product, int quantity) { // Corrected method
        if(product.getQuantity() >= quantity) {
            cart.addItem(product, quantity);
            System.out.println(quantity + " " + product.getName() + " added to cart.");
        }
        else{
            System.out.println("Not enough stock available for " + quantity + " " + product.getName() + ".");
        }
    }

    @Override
    public void viewCart(Cart cart) {
        if(cart.getItems().isEmpty()) {
            System.out.println("Cart is empty.");
        }
        else{
            cart.displayCart();
        }
    }

    @Override
    public void checkout(Cart cart) {
        if(cart.isEmpty()) {
            System.out.println("Cart is empty. Add items before checking out.");
            return;
        }
        double total = cart.calculateTotal();
        Payment payment = new payment(this.getemail(), totalAmount);
        paymentHistory.add(payment);

        cart.deductstock(total);
        cart.clearCart();   
        System.out.println("Checkout successful! Total Amount: $" + totalAmount);
    }
    @Override
    public void addReview(String review) {
        Review review1 = new Review(this.getemail(), review);
        ReviewSystem.addReview(review1);
        System.out.println("Review added successfully!");
    }

}
