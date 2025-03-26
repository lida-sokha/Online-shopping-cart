package oop_java;
public interface CustomerInterface {
        void viewProductDetails(Product product);
        void addReview(String review);
        boolean addToCart(String productId, int quantity);
        void displayCart();
        void checkout(Cart cart);
        Product findProductById(String productID);
        Product searchProductByName(String productName);
        boolean makePayment();
}
