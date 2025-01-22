import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private String userId;
    private List<CartItem> items;
    private double totalPrice;
    private double tax;
    private Payment paymentMethod;
    private Date orderDate;
    private String status;

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

    // Calculate the final total price including tax
    public double calculateFinalPrice() {
        return totalPrice + tax;
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public List<CartItem> getItems() { return items; }
    public double getTotalPrice() { return totalPrice; }
    public double getTax() { return tax; }
    public Payment getPaymentMethod() { return paymentMethod; }
    public Date getOrderDate() { return orderDate; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
