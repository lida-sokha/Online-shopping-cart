import java.util.Date;
import java.util.List;

public class Order {
     String orderId;
     String userId;
     List<CartItem> items;//import from java library//
     double totalPrice;
     double tax;
     Payment paymentMethod;//payment is a class//
     Date orderDate;//import from java library//
     String status;

    // Constructor
    public Order(String orderId, String userId, List<CartItem> items, double totalPrice, double tax, Payment paymentMethod, Date orderDate, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.tax = tax;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.status = status;
    }

}
