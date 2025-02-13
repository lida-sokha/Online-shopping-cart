import java.util.Date;
import java.util.List;

public class Order {
    // Private fields to protect data
    private  String orderId;
    private  String userId;
    private  List<CartItem> items;  // Assuming CartItem class is already defined
    private  double totalPrice;
    private  double taxRate;  // Tax rate in percentage
    private  double tax;
    private  Payment paymentMethod;  // Assuming Payment class is defined
    private  Date orderDate;
    private String status;

    // Constructor to initialize fields
    public Order(String orderId, String userId, List<CartItem> items, double totalPrice, double taxRate, Payment paymentMethod, Date orderDate, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.taxRate = taxRate;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.status = status;
        this.tax = calculateTax(); // Calculate tax based on the total price and tax rate
    }

    // Method to calculate tax as a percentage of the total price
    private double calculateTax() {
        return (totalPrice * taxRate) / 100;
    }

    // Getters for controlled access to fields
    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public double getTax() {
        return tax;
    }

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
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
}
