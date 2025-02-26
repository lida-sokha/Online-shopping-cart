import java.util.HashMap;

public class Customer extends User implements CustomerInterface {
    private static HashMap<String, Customer> customerMap = new HashMap<>(); // Store customers using email as key
    private HashMap<String, Integer> cart; // Store productID and quantity

    public Customer(String name, String email, String address, String password, String phoneNumber) {
        super(name, email, address, password, phoneNumber, "customer");
        this.cart = new HashMap<>();
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
    // Get customer details
    // @Override
    // public String toString() {
    //     return "Customer{name='" + getName() + "', email='" + getEmail() + "', phone='" + getPhoneNumber() + "'}";
    //                 }
}
