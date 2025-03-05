import java.util.HashMap;

public class Product {
    private String productId;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String description;

    private static HashMap<String, Product> productCatalog = new HashMap<>();

    public Product(String productId, String name, double price, int quantity, String category, String description) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
        productCatalog.put(productId, this);
    }

    // Getters
    public String getProductId() {
        return productId;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }
    //seller
    public boolean sell(int quantitySold) {
        if (quantitySold > 0 && quantitySold <= quantity) {
            this.quantity -= quantitySold;
            return true;
        } else {
            System.out.println("Not enough stock to sell.");
            return false;
        }
    }

    // Customer functions

    // View product details
    public static void viewProductDetails() {
        if (productCatalog.isEmpty()) {
            System.out.println("No products available in the catalog.");
        } else {
            System.out.println("Available Products:");
            for (Product product : productCatalog.values()) {
                System.out.println("ID:"+ product.getProductId()+
                "| Name:"+ product.getName()+
                "| Price: $"+ product.getPrice()+
                "| Quantity:"+ product.getQuantity()+
                "| Category:"+ product.getCategory());
            }
        }
    }

    // Simulate adding a product to cart
    public void addToCart() {
        System.out.println(name + " has been added to your cart.");
    }

    // Simulate purchasing a product
    public boolean purchaseProduct(int quantityToBuy) {
        if (quantityToBuy > 0 && quantityToBuy <= quantity) {
            this.quantity -= quantityToBuy;
            System.out.println("You have successfully purchased " + quantityToBuy + " of " + name + ".");
            return true;
        } else {
            System.out.println("Not enough stock available.");
            return false;
        }
    }

    // Leave a review
    public void leaveReview(String review) {
        System.out.println("Review for " + name + ": " + review);
    }

    // Static methods for catalog management
    public static void addProduct(Product product) {
        productCatalog.put(product.getProductId(), product);
    }

    public static Product getProductById(String productId) {
        return productCatalog.get(productId);
    }

    public static Product removeProduct(String productId) {
        return productCatalog.remove(productId);
    }

    // public static Product viewProductDetails() {
    //     if (productCatalog.isEmpty()) {
    //         System.out.println("No products available in the catalog.");
    //     } else {
    //         System.out.println("Available Products:");
    //         for (Product product : productCatalog.values()) {
    //             System.out.println("ID:"+ product.getProductId()+
    //             "| Name:"+ product.getName()+
    //             "| Price:"+ product.getPrice()+
    //             "| Quantity:"+ product.getQuantity()+
    //             "| Category:"+ product.getCategory());
    //         }
    //     }
    // }
    public static void searchProduct(String searchName){
        boolean found = false;
        for (Product product : productCatalog.values()){
            //check if the product name is in the list
            if(product.getName().toLowerCase().contains(searchName.toLowerCase())){
                if(!found){
                    System.out.println("The item is available in the store:");
                    found = true; // Set flag to true once we find a matching product
                }
                System.out.println("ID: " + product.getProductId() +
                                " | Name: " + product.getName() +
                                " | Price: $" + String.format("%.2f", product.getPrice()) +
                                " | Quantity: " + product.getQuantity() +
                                " | Category: " + product.getCategory());
            }
        }
         // If no product was found, inform the user
        if (!found) {
            System.out.println("Can't find the product!");
        }
    }
    @Override
    public String toString() {
        return "Product [ID=" + productId + 
        ", Name=" + name + 
        ", Price= $" + price + 
        ", Quantity=" + quantity + 
        ", Category=" + category + "]";
    }
}
