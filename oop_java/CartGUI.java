package oop_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class CartGUI extends JFrame {
    private JTextArea cartItemsArea;
    private JTextField amountField;
    private JComboBox<String> paymentMethodComboBox;
    private JTextArea statusArea;
    private double totalAmount = 0.0;
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private Customer currentCustomer;  // Should be initialized when user logs in
    private HashMap<String, Integer> cart = new HashMap<>();  // Your cart structure


    public CartGUI() {
        setTitle("Shopping Cart");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Your Shopping Cart", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Main panel for displaying cart items
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Cart items area (displaying cart details)
        cartItemsArea = new JTextArea();
        cartItemsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(cartItemsArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add cart panel to the center of the window
        add(mainPanel, BorderLayout.CENTER);

        // Bottom panel for payment input
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 2, 10, 10));

        // Amount field (Total price)
        JLabel amountLabel = new JLabel("Total Amount ($):");
        bottomPanel.add(amountLabel);
        amountField = new JTextField();
        amountField.setEditable(false);
        bottomPanel.add(amountField);

        // Payment method dropdown
        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        bottomPanel.add(paymentMethodLabel);
        paymentMethodComboBox = new JComboBox<>(new String[]{"Credit Card", "PayPal"});
        bottomPanel.add(paymentMethodComboBox);

        // Add bottom panel to the south of the window
        add(bottomPanel, BorderLayout.SOUTH);

        // Status area to display payment results
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane statusScrollPane = new JScrollPane(statusArea);
        add(statusScrollPane, BorderLayout.EAST); 
        
        

        // Button to proceed with payment
        JButton makePaymentButton = new JButton("Make Payment");
        makePaymentButton.addActionListener(e -> {
    // 1. Get payment details
    double amount = calculateTotal(); // You need to implement this
    String customerEmail = currentCustomer.getEmail(); // Get from your customer object
    
    // 2. Show payment method selection
    String[] methods = {"Credit Card", "PayPal"};
    String selectedMethod = (String) JOptionPane.showInputDialog(
        null, // or your parent component
        "Select payment method",
        "Payment Method",
        JOptionPane.QUESTION_MESSAGE,
        null,
        methods,
        methods[0]);
    
    // 3. Process payment if method was selected
    if (selectedMethod != null) {
        Payment payment = new Payment(amount, selectedMethod, customerEmail);
        boolean paymentSuccess = payment.makePayment();
        
        if (paymentSuccess) {
            JOptionPane.showMessageDialog(null, 
                "Payment successful!\n" + payment.toString(),
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            // Additional success handling (clear cart, etc.)
        } else {
            JOptionPane.showMessageDialog(null,
                "Payment failed: " + payment.getPaymentStatus(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
});
        // Populate sample cart items
        addSampleCartItems();

        setVisible(true);
    }

    private void addSampleCartItems() {
        cartItems.add(new CartItem("Product 1", 2, 20.00));
        cartItems.add(new CartItem("Product 2", 1, 50.00));
        cartItems.add(new CartItem("Product 3", 3, 10.00));
        updateCartItemsArea();
    }

    private void updateCartItemsArea() {
        StringBuilder cartDetails = new StringBuilder();
        totalAmount = 0.0;

        for (CartItem item : cartItems) {
            cartDetails.append(item.toString()).append("\n");
            totalAmount += item.getTotalPrice();
        }

        cartItemsArea.setText(cartDetails.toString());
        amountField.setText(String.format("%.2f", totalAmount));
    }

    private void processPayment() {
        // Retrieve payment details
        String amountText = amountField.getText();
        double amount = totalAmount;
        String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        String customerEmail = "customer@example.com"; // Example email

        // Create Payment object
        Payment payment = new Payment(amount, paymentMethod, customerEmail);

        // Process payment
        boolean paymentStatus = payment.makePayment();
        if (paymentStatus) {
            statusArea.setText("Payment successful! " + payment.toString());
        } else {
            statusArea.setText("Payment failed. Please try again.");
        }
    }

    public static void main(String[] args) {
        new CartGUI();
    }

    // CartItem class representing an item in the cart
    static class CartItem {
        private String productName;
        private int quantity;
        private double price;

        public CartItem(String productName, int quantity, double price) {
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
        }

        public double getTotalPrice() {
            return quantity * price;
        }

        @Override
        public String toString() {
            return productName + " x " + quantity + " = $" + String.format("%.2f", getTotalPrice());
        }
    }
}

