import  java.util.List;
import java.util.ArrayList;


public class Seller extends User {
    private List<String> productId_list;
    public Seller(String name, String email,String address,String password,String phoneNumber){
        super(name, email, address, password, phoneNumber, phoneNumber);
        this.productId_list= new ArrayList<>();
    }
    @Override 
    public String getRole(){
        return "seller";
    }
    //add item
    public void addProduct(String product,String name,double price, int quantity,String category,String description){
        if (Product.getProductById(product) != null){
            System.out.println("the product is order exit! add other product");
            return;
        }
        else{
            Product new_product = new Product(product, name, price, quantity, category, description);
            productId_list.add(product);
            System.out.println("product add successfully" + name);
        }
    }
    //remove 
    public void removeProduct(String productID){
        if(productId_list.contains(productID)){
            Product removed = Product.removeProduct(productID);
            if(removed != null){
                productId_list.remove(productID);
                System.out.println("Product have been remove");
            }
            else{
                System.out.println("Failed to remove the product");
            }
        }
    }
    //check the detail of the product
    public void checkProduct(String productID){
        Product product = Product.getProductById(productID);
        if(product != null){
            System.out.println(product);
        }
        else{
            System.out.println("Product not found");
        }
    }
    //sell the product
    public void sellProduct(String productID, int quantitySold){
        Product product = Product.getProductById(productID);
        if(product !=null && productId_list.contains(productID)){   
            if(product.sell(quantitySold)){
                System.out.println(quantitySold + " units of " + product.getName() + " sold.");
            }
            else{
                System.out.println("Product not found or you don't have permission to sell this.");
            }
        }
    }
    // Display all products added by the seller
    public void displayMyProducts() {
        System.out.println("Your Products:");
        for (String productId : productId_list) {
            Product product = Product.getProductById(productId);
            if (product != null) {
                System.out.println(product);
            }
        }
    }
}