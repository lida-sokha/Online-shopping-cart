import java.util.Date;
import java.util.List;

public class Order {
    // Private fields to protect data
    private String orderId;
    private String userId;
    private List<CartItem> items;  // Assuming CartItem class is already defined
    private double totalPrice;
    private double tax;
    private Payment paymentMethod;  // Assuming Payment class is defined
    private Date orderDate;
    private String status;

    // Constructor to initialize fields
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

    // Getters and Setters for controlled access
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Optional: toString() for easy printing of order details
    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", userId=" + userId + ", items=" + items.size() + " items, totalPrice=" + totalPrice +
               ", tax=" + tax + ", paymentMethod=" + paymentMethod + ", orderDate=" + orderDate + ", status=" + status + "]";
    }

    // Additional function to calculate the total price (including tax)
    public double calculateTotalPrice() {
        return this.totalPrice + this.tax;
    }

    // Additional function to check if order status is completed
    public boolean isOrderCompleted() {
        return this.status.equalsIgnoreCase("Completed");
    }
}
