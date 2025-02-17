import java.util.ArrayList;

public class Cart {
    private String cartId;
    private User userID; //want to use the same userid from the user class
    private ArrayList<CartItem> items;
    // Constructor
    public Cart(String cartId, String userId) {
        this.cartId = cartId;
        this.userID = userID;
        this.items = new ArrayList<>();//array list
    }
    //getter
    public User getuserID(){
        return userID;
    }

    public String toString(){
        return "cartID: "+ cartId +"\n"+
                "userID: "+ getuserID()+"\n";
    }

    
}
    