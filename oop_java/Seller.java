import java.util.ArrayList;
import java.util.List;

public class Seller extends User {
    
    public void addProduct(Product product) {
        this.products.add(product);
    }
}
