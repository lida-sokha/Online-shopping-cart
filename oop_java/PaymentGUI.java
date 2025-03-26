package oop_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentGUI extends JFrame {
    private JTextField amountField;
    private JComboBox<String> paymentMethodComboBox;
    private JTextArea statusArea;

    public PaymentGUI() {
        setTitle("Payment Gateway");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Payment Processing", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Main panel for form inputs and buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));

        // Amount field
        JLabel amountLabel = new JLabel("Amount ($):");
        amountField = new JTextField();
        mainPanel.add(amountLabel);
        mainPanel.add(amountField);

        // Payment method dropdown
        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        paymentMethodComboBox = new JComboBox<>(new String[]{"Credit Card", "PayPal"});
        mainPanel.add(paymentMethodLabel);
        mainPanel.add(paymentMethodComboBox);

        // Add main panel to the center of the window
        add(mainPanel, BorderLayout.CENTER);

        // Status area to display payment results
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(statusArea);
        add(scrollPane, BorderLayout.SOUTH);

        // Bottom panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton payButton = new JButton("Make Payment");
        JButton refundButton = new JButton("Refund Payment");

        buttonPanel.add(payButton);
        buttonPanel.add(refundButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Pay button action
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountText = amountField.getText();
                double amount = 0;
                try {
                    amount = Double.parseDouble(amountText);
                } catch (NumberFormatException ex) {
                    statusArea.setText("Invalid amount entered.");
                    return;
                }

                String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
                String customerEmail = "customer@example.com"; // Example email, you can customize it
                Payment payment = new Payment(amount, paymentMethod, customerEmail);

                boolean paymentStatus = payment.makePayment();
                if (paymentStatus) {
                    statusArea.setText("Payment successful! " + payment.toString());
                } else {
                    statusArea.setText("Payment failed. Try again.");
                }
            }
        });

        // Refund button action
        refundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountText = amountField.getText();
                double amount = 0;
                try {
                    amount = Double.parseDouble(amountText);
                } catch (NumberFormatException ex) {
                    statusArea.setText("Invalid amount entered.");
                    return;
                }

                String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
                String customerEmail = "customer@example.com"; // Example email, you can customize it
                Payment payment = new Payment(amount, paymentMethod, customerEmail);

                boolean refundStatus = payment.refundPayment();
                if (refundStatus) {
                    statusArea.setText("Refund successful! " + payment.toString());
                } else {
                    statusArea.setText("Refund failed. Payment was not successful.");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new PaymentGUI();
    }
}
