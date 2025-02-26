
import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<String> Cart;
    public Customer(String name, String email, String address, String password, String phoneNumber) {
        super(name, email, address, password, phoneNumber, "customer");
        this.Cart = new ArrayList<>();
    }

    //view product 
    public void viewProductDetails(String productID){
        Product product = Product.getProductById(productID);
        if(product !=null){
            product.viewProductDetails(); //call method from product
        }
        else{
            System.out.println("Product not found!");
        }
    }
    //add new product to cart
    public void addToCart(String productID){
        Product product = Product.getProductById(productID);
        if(product != null){
            Cart.add(productID);
            System.out.println(product.getName()+"item has been add to the cart");
        }
        else{
            System.out.println("Product not found");
        }
    }
    //purchase
    public boolean purchaseProduct(String productID, int quantityToBuy) {
        Product product = Product.getProductById(productID);
        
        if (product == null) {
            return false; // Product does not exist
        }
    
        if (!product.sell(quantityToBuy)) {
            return false; // Not enough stock
        }
    
        Cart cart = Cart.getInstance(); // Assuming Cart is a singleton or globally managed
        cart.removeProduct(productID);  // Remove from cart after purchase
    
        return true; // Purchase successful
    }
    
    public void leaveReview(String productId, String review) {
        Product product = Product.getProductById(productId);
        if (product != null) {
            product.leaveReview(review);
        } else {
            System.out.println("Product not found.");
        }
    }
    // Display cart items
    public void viewCart() {
        if (Cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Your cart:");
            for (String productId : Cart) {
                Product product = Product.getProductById(productId);
                if (product != null) {
                    System.out.println(product);
                }
            }
        }
    }
}
