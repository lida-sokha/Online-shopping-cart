public class Customer extends User {
    public void placeOrder(Order order) {
        this.orders.add(order);
    }
}
