package oop_java;
public interface CustomerInterface {
        // void addToCart(String productID, int quantity);
        void addToCart(Cart cart,Product product, int quantity);
        void viewCart(Cart cart);
        void checkout(Cart cart);
        void addReview(String review);
        void viewProductDetails();
}
