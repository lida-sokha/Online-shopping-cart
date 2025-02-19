import java.util.ArrayList;

public class Seller extends User {
    private ArrayList<String> productIdList;

    public Seller(String name, String email, String address, String password, String phoneNumber) {
        super(name, email, address, password, phoneNumber, "Seller");
        this.productIdList = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Seller";
    }

    // Add a product
    public void addProduct(String productId, String name, double price, int quantity, String category, String description) {
        if (Product.getProductById(productId) != null) {
            System.out.println("Product already exists. Please add a different product.");
        } else {
            Product newProduct = new Product(productId, name, price, quantity, category, description);
            productIdList.add(productId);
            System.out.println(name + " has been added successfully!");
        }
    }

    // Remove a product
    public void removeProduct(String productId) {
        if (productIdList.contains(productId)) {
            Product removedProduct = Product.removeProduct(productId);
            if (removedProduct != null) {
                productIdList.remove(productId);
                System.out.println("Product has been removed.");
            } else {
                System.out.println("Failed to remove the product.");
            }
        } else {
            System.out.println("Product not found in your catalog.");
        }
    }

    // View product details
    public void checkProduct(String productId) {
        Product product = Product.getProductById(productId);
        if (product != null) {
            product.viewProductDetails();  // Using Product method to view details
        } else {
            System.out.println("Product not found.");
        }
    }

    // Sell a product
    public void sellProduct(String productId, int quantitySold) {
        Product product = Product.getProductById(productId);
        if (product != null && productIdList.contains(productId)) {
            if (product.sell(quantitySold)) {
                System.out.println(quantitySold + " units of " + product.getName() + " sold.");
            } else {
                System.out.println("Not enough stock to sell.");
            }
        } else {
            System.out.println("Product not found or you don't have permission to sell this product.");
        }
    }

    // Display all products added by the seller
    public void displayMyProducts() {
        System.out.println("Your Products:");
        for (String productId : productIdList) {
            Product product = Product.getProductById(productId);
            if (product != null) {
                System.out.println(product);
            } else {
                System.out.println("Product not found.");
            }
        }
    }
}
