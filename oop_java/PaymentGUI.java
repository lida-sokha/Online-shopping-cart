package oop_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PaymentGUI extends JFrame {
    private Payment payment;
    private JTextArea paymentDetailsArea;
    private JButton processButton;
    private JButton cancelButton;

    public PaymentGUI(double amount, String paymentMethod, String customerEmail) {
        this.payment = new Payment(amount, paymentMethod, customerEmail);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Payment Processing");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // Payment details panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        paymentDetailsArea = new JTextArea(getPaymentDetails());
        paymentDetailsArea.setEditable(false);
        paymentDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(paymentDetailsArea);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        processButton = new JButton("Process Payment");
        cancelButton = new JButton("Cancel");

        processButton.addActionListener(this::processPayment);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(processButton);
        buttonPanel.add(cancelButton);

        // Add components to frame
        add(detailsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String getPaymentDetails() {
        return String.format(
            "=== Payment Details ===\n" +
            "Amount: $%.2f\n" +
            "Method: %s\n" +
            "Customer: %s\n" +
            "Status: %s\n" +
            "Date: %s\n" +
            "Payment ID: %d",
            payment.getAmount(),
            payment.getPaymentMethod(),
            payment.getCustomerEmail(),
            payment.getPaymentStatus(),
            payment.getformatePayment(),
            payment.getPaymentId()
        );
    }

    private void processPayment(ActionEvent e) {
        processButton.setEnabled(false);
        cancelButton.setEnabled(false);

        // Simulate payment processing in background thread
        new Thread(() -> {
            boolean success = payment.makePayment();

            SwingUtilities.invokeLater(() -> {
                paymentDetailsArea.setText(getPaymentDetails());
                
                if (success) {
                    JOptionPane.showMessageDialog(this,
                        "Payment processed successfully!\n" + payment.toString(),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Payment failed: " + payment.getPaymentStatus(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    processButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                }
            });
        }).start();
    }

    public static void main(String[] args) {
        // For testing
        SwingUtilities.invokeLater(() -> {
            PaymentGUI gui = new PaymentGUI(99.99, "Credit Card", "customer@example.com");
            gui.setVisible(true);
        });
    }
}