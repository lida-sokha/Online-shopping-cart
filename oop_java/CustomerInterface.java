public interface CustomerInterface {
        void addToCart(String productID, int quantity);
        boolean purchaseProduct(String productID, int quantityToBuy);
        void viewCart();
        void leaveReview(String productId, String review);
}
