package oop_java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerGUI extends JFrame {
    private JComboBox<String> productDropdown;
    private JTextField quantityField;
     private Connection conn;
    private DefaultTableModel tableModel;
    private JTextArea cartArea;
    private JButton addToCartButton, viewCartButton, checkoutButton;

    public CustomerGUI() {
        setTitle("Customer Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Customer Portal", SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        
        // Product selection panel
        JPanel productPanel = new JPanel();
        productDropdown = new JComboBox<>(new String[]{"Product 1", "Product 2", "Product 3"}); // Example products
        quantityField = new JTextField(5);
        addToCartButton = new JButton("Add to Cart");
        
        productPanel.add(new JLabel("Select Product:"));
        productPanel.add(productDropdown);
        productPanel.add(new JLabel("Quantity:"));
        productPanel.add(quantityField);
        productPanel.add(addToCartButton);
        
        // Cart display area
        cartArea = new JTextArea(10, 30);
        cartArea.setEditable(false);
        JScrollPane cartScrollPane = new JScrollPane(cartArea);
        
        // Buttons for cart actions
        JPanel buttonPanel = new JPanel();
        viewCartButton = new JButton("View Cart");
        checkoutButton = new JButton("Checkout");
        
        buttonPanel.add(viewCartButton);
        buttonPanel.add(checkoutButton);
        
        // Add components to main panel
        mainPanel.add(productPanel, BorderLayout.CENTER);
        // mainPanel.add(cartScrollPane, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
        
        // Button actions
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });
        
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCart();
            }
        });
        
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });
        
        setVisible(true);
    }
     private void loadProducts() {
        tableModel.setRowCount(0);
        try (Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), 
                    rs.getString("name"), 
                    rs.getDouble("price"), 
                    rs.getInt("quantity"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getString("seller_id"),
                    "seller" // Hardcoded role for seller interface
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void addToCart() {
        String product = (String) productDropdown.getSelectedItem();
        String quantity = quantityField.getText();
        if (quantity.matches("\\d+")) {
            cartArea.append(product + " x" + quantity + "\n");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewCart() {
        JOptionPane.showMessageDialog(this, cartArea.getText(), "Cart Contents", JOptionPane.INFORMATION_MESSAGE);
    }

    private void checkout() {
        JOptionPane.showMessageDialog(this, "Proceeding to checkout...", "Checkout", JOptionPane.INFORMATION_MESSAGE);
        cartArea.setText(""); // Clear cart after checkout
    }
    
    public static void main(String[] args) {
        new CustomerGUI();
    }
}
