import java.util.ArrayList;
import java.util.List;//array list or link list 

public class Cart {
    private String cartId;
    private User userID; //want to use the same userid from the user class
    private List<CartItem> items;

    // Constructor
    public Cart(String cartId, String userId) {
        this.cartId = cartId;
        this.userID = userID;
        this.items = new ArrayList<>();//array list
    }
    //getter
    public int getuserID(){
        return userID.getuserID();
    }

    public String toString(){
        return "cartID: "+ cartId +"\n"+
                "userID: "+ getuserID()+"\n";
    }

}
    