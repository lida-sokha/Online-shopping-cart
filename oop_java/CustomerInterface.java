package oop_java;
public interface CustomerInterface {
        void viewProductDetails(Product product);
        void addReview(String review);
        void addToCart(String productID, int quantity);
        void displayCart();
        void checkout(Cart cart);
        Product findProductById(String productID);
}
