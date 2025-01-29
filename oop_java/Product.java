public class Product {
     String productId;
     String name;
     double price;
     int quantity;
     String category;
     String description;

    // Constructor
    public Product(String productId, String name, double price, int quantity, String category, String description) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
    }
    public String toString() {
        return "Product ID: " + productId + "\n" +
               "Name: " + name + "\n" +
               "Price: " + price + "\n" +
               "Quantity: " + quantity + "\n" +
               "Category: " + category + "\n" +
               "Description: " + description;
    }  
    public static void main(String[] args) {
        Product p1= new Product("11", "milk", 5, 1, "milk", "h");
        System.out.println(p1);
    }
}
