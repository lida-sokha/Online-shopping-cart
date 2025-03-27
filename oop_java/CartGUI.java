package oop_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class CartGUI extends JFrame {
    private JTextArea cartItemsArea;
    private JTextField amountField;
    private JComboBox<String> paymentMethodComboBox;
    private JTextArea statusArea;
    private double totalAmount = 0.0;
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private Customer currentCustomer;
    private HashMap<String, Integer> cart = new HashMap<>();

    public CartGUI(Customer customer) {
        this.currentCustomer = customer;
        initializeUI();
        addSampleCartItems();
        setVisible(true);
    }

    private void initializeUI() {
        setTitle("Shopping Cart - " + currentCustomer.getName());
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Your Shopping Cart", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        cartItemsArea = new JTextArea();
        cartItemsArea.setEditable(false);
        mainPanel.add(new JScrollPane(cartItemsArea), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Total amount
        bottomPanel.add(new JLabel("Total Amount ($):"));
        amountField = new JTextField();
        amountField.setEditable(false);
        bottomPanel.add(amountField);

        // Payment method
        bottomPanel.add(new JLabel("Payment Method:"));
        paymentMethodComboBox = new JComboBox<>(new String[]{"Credit Card", "PayPal", "Bank Transfer"});
        bottomPanel.add(paymentMethodComboBox);

        // Buttons
        JButton calculateButton = new JButton("Calculate Total");
        calculateButton.addActionListener(e -> calculateTotal());
        bottomPanel.add(calculateButton);

        JButton paymentButton = new JButton("Make Payment");
        paymentButton.addActionListener(this::processPayment);
        bottomPanel.add(paymentButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Status area
        statusArea = new JTextArea(5, 20);
        statusArea.setEditable(false);
        add(new JScrollPane(statusArea), BorderLayout.EAST);
    }

    private void addSampleCartItems() {
        // This would normally come from your product database
        cartItems.add(new CartItem("Smartphone", 1, 599.99));
        cartItems.add(new CartItem("Headphones", 2, 89.99));
        updateCartDisplay();
    }

    private void updateCartDisplay() {
        StringBuilder sb = new StringBuilder();
        totalAmount = 0.0;
        for (CartItem item : cartItems) {
            sb.append(item.toString()).append("\n");
            totalAmount += item.getTotalPrice();
        }
        cartItemsArea.setText(sb.toString());
        amountField.setText(String.format("$%.2f", totalAmount));
    }

    public void calculateTotal() {
        totalAmount = 0.0;
        for (CartItem item : cartItems) {
            totalAmount += item.getTotalPrice();
        }
        amountField.setText(String.format("$%.2f", totalAmount));
        statusArea.append("Total calculated: " + amountField.getText() + "\n");
    }

    private void processPayment(ActionEvent e) {
        if (totalAmount <= 0) {
            JOptionPane.showMessageDialog(this, "Your cart is empty", "Checkout", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        String customerEmail = currentCustomer.getEmail();

        // Show confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(this,
            "Confirm payment of " + amountField.getText() + " via " + paymentMethod + "?",
            "Confirm Payment",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Create payment using customer's method
            boolean paymentSuccess = currentCustomer.makePayment();

            if (paymentSuccess) {
                statusArea.setText("Payment successful!\nCustomer: " + currentCustomer.getEmail());
                JOptionPane.showMessageDialog(this, "Payment processed successfully!", 
                                           "Success", JOptionPane.INFORMATION_MESSAGE);
                // Clear cart after successful payment
                cartItems.clear();
                updateCartDisplay();
                
                // Save order to database
                currentCustomer.saveOrderToDatabase(totalAmount);
            } else {
                statusArea.setText("Payment failed. Please try again.");
                JOptionPane.showMessageDialog(this, "Payment failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // CartItem inner class
    static class CartItem {
        private final String productName;
        private final int quantity;
        private final double unitPrice;

        public CartItem(String productName, int quantity, double unitPrice) {
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public double getTotalPrice() {
            return quantity * unitPrice;
        }

        @Override
        public String toString() {
            return String.format("%s x%d @ $%.2f = $%.2f", 
                productName, quantity, unitPrice, getTotalPrice());
        }
    }

    public static void main(String[] args) {
        // Example usage - in your real application, you would get the logged-in customer
        Customer sampleCustomer = new Customer("John Doe", "john@example.com", 
            "123 Main St", "password123", "555-1234");
        
        SwingUtilities.invokeLater(() -> new CartGUI(sampleCustomer));
    }
}