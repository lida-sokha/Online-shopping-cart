package oop_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CustomerGUI {
    private JFrame frame;
    private JTextArea displayArea;
    private JTextField productIdField, quantityField, reviewField;
    private JButton viewProductBtn, addToCartBtn, displayCartBtn, checkoutBtn, addReviewBtn;
    private Customer customer;

    public CustomerGUI(Customer customer) {
        this.customer = customer;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Customer Dashboard");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        productIdField = new JTextField();
        quantityField = new JTextField();
        reviewField = new JTextField();
        
        viewProductBtn = new JButton("View Product");
        addToCartBtn = new JButton("Add to Cart");
        displayCartBtn = new JButton("Display Cart");
        checkoutBtn = new JButton("Checkout");
        addReviewBtn = new JButton("Add Review");
        
        panel.add(new JLabel("Product ID:"));
        panel.add(productIdField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Review:"));
        panel.add(reviewField);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        buttonPanel.add(viewProductBtn);
        buttonPanel.add(addToCartBtn);
        buttonPanel.add(displayCartBtn);
        buttonPanel.add(checkoutBtn);
        buttonPanel.add(addReviewBtn);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addActionListeners();
        frame.setVisible(true);
    }

    private void addActionListeners() {
        viewProductBtn.addActionListener(e -> viewProduct());
        addToCartBtn.addActionListener(e -> addToCart());
        displayCartBtn.addActionListener(e -> displayCart());
        checkoutBtn.addActionListener(e -> checkout());
        addReviewBtn.addActionListener(e -> addReview());
    }

    private void viewProduct() {
        String productId = productIdField.getText();
        Product product = customer.findProductById(productId);
        if (product != null) {
            displayArea.setText("Product Details:\n" +
                    "Name: " + product.getName() + "\n" +
                    "Price: " + product.getPrice() + "\n" +
                    "Quantity: " + product.getQuantity() + "\n" +
                    "Category: " + product.getCategory() + "\n" +
                    "Description: " + product.getDescription());
        } else {
            displayArea.setText("Product not found.");
        }
    }

    private void addToCart() {
        String productId = productIdField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        customer.addToCart(productId, quantity);
        displayArea.setText("Added " + quantity + " of product " + productId + " to cart.");
    }

    private void displayCart() {
        displayArea.setText("Cart Contents:\n");
        customer.displayCart();
    }

    private void checkout() {
        Cart cart = new Cart(customer);
        customer.checkout(cart);
        displayArea.setText("Checkout complete!");
    }

    private void addReview() {
        String review = reviewField.getText();
        customer.addReview(review);
        displayArea.setText("Review added successfully!");
    }

    public static void main(String[] args) {
        Customer customer = new Customer("John Doe", "john@example.com", "123 Street", "password", "1234567890");
        new CustomerGUI(customer);
    }
}
