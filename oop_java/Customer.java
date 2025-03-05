import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer extends User implements CustomerInterface {
    private static HashMap<String, Customer> customerMap = new HashMap<>(); // Store customers using email as key
    private HashMap<String, Integer> cart; // Store productID and quantity
    private List<Payment> paymentHistory; // Store customer's payment history

    public Customer(String name, String email, String address, String password, String phoneNumber) {
        super(name, email, address, password, phoneNumber, "customer");
        this.cart = new HashMap<>();
        this.paymentHistory = new ArrayList<>(); // Initialize payment history
        customerMap.put(email, this); // Store customer in HashMap using email as a unique key
    }

    // Retrieve customer by email
    public static Customer getCustomerByEmail(String email) {
        return customerMap.get(email);
    }

    // Retrieve all customers
    public static HashMap<String, Customer> getAllCustomers() {
        return customerMap;
    }

    // Add product to cart
    @Override
    public void addToCart(String productID, int quantity) {
        Product product = Product.getProductById(productID);
        if (product != null) {
            cart.put(productID, cart.getOrDefault(productID, 0) + quantity);
            System.out.println(product.getName() + " (" + quantity + " items) added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    // Purchase product
    @Override
    public boolean purchaseProduct(String productID, int quantityToBuy) {
        Product product = Product.getProductById(productID);

        if (product == null || !product.sell(quantityToBuy)) {
            return false;
        }

        cart.remove(productID); // Remove from cart after purchase
        return true;
    }

    // Make a payment
    public boolean makePayment(double amount, String paymentMethod) {
        Payment payment = new Payment(amount, paymentMethod, this.getEmail()); // Create a new payment

        boolean success = payment.processPayment(); // Process the payment
        if (success) {
            paymentHistory.add(payment); // Store successful payments in history
            System.out.println("Payment successful! Transaction ID: " + payment.getPaymentId());
        } else {
            System.out.println("Payment failed! Please try again.");
        }

        return success;
    }

    // Request a refund
    public boolean requestRefund(int paymentId) {
        for (Payment payment : paymentHistory) {
            if (payment.getPaymentId() == paymentId) {
                boolean refundSuccess = payment.refundPayment();
                if (refundSuccess) {
                    System.out.println("Refund successful for Payment ID: " + paymentId);
                    return true;
                } else {
                    System.out.println("Refund failed! Payment may not be completed.");
                    return false;
                }
            }
        }
        System.out.println("Payment ID not found!");
        return false;
    }

    // View payment history
    public void viewPaymentHistory() {
        if (paymentHistory.isEmpty()) {
            System.out.println("No payment history available.");
        } else {
            System.out.println("Payment History for " + getEmail() + ":");
            for (Payment payment : paymentHistory) {
                System.out.println(payment);
            }
        }
    }

    // View cart
    @Override
    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Your cart:");
            for (String productId : cart.keySet()) {
                Product product = Product.getProductById(productId);
                if (product != null) {
                    System.out.println(product.getName() + " - " + cart.get(productId) + " items");
                }
            }
        }
    }

    // Leave a review 
    @Override
    public void leaveReview(String productId, String review) {
        Product product = Product.getProductById(productId);
        if (product != null) {
            product.leaveReview(review);
            System.out.println("Review added successfully!");
        } else {
            System.out.println("Product not found.");
        }
    }

    // View products
    public void viewProduct() {
        Product.viewProductDetails();
    }

    // Search product by name
    public void searchProductByName(String searchName) {
        Product.searchProduct(searchName);
    }
}
