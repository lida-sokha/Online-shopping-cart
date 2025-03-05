public interface CustomerInterface {
        // void addToCart(String productID, int quantity);
        public boolean addToCart(String productId, int quantity);
        boolean purchaseProduct(String productID, int quantityToBuy);
        void viewCart();
        void leaveReview(String productId, String review);
        void searchProductByID(String productID);
        void viewProduct();
}
