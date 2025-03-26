package oop_java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class CustomerGUI extends JFrame {
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JTextField quantityField;
    private Connection conn;
    private JTextArea cartArea;

    public CustomerGUI() {
        setTitle("Customer Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        conn = MySQLConnection.getConnection();

        // Main panel setup
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        
        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Customer Portal", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        
        // Product table setup
        productTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Price", "Category", "Description"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        productTable = new JTable(productTableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Available Products"));
        
        // Load products
        if (conn != null) {
            loadProductsToTable();
        } else {
            // Demo products if no database
            productTableModel.addRow(new Object[]{"1", "Product 1", 19.99, "Electronics", "Sample product"});
            productTableModel.addRow(new Object[]{"2", "Product 2", 29.99, "Clothing", "Sample product"});
            productTableModel.addRow(new Object[]{"3", "Product 3", 9.99, "Food", "Sample product"});
        }
        
        // Cart and quantity controls
        JPanel cartPanel = new JPanel(new BorderLayout());
        
        // Quantity controls
        JPanel quantityPanel = new JPanel(new FlowLayout());
        quantityField = new JTextField(5);
        JButton addToCartButton = new JButton("Add to Cart");
        
        quantityPanel.add(new JLabel("Quantity:"));
        quantityPanel.add(quantityField);
        quantityPanel.add(addToCartButton);
        
        // Cart display area
        cartArea = new JTextArea(10, 30);
        cartArea.setEditable(false);
        JScrollPane cartScrollPane = new JScrollPane(cartArea);
        cartScrollPane.setBorder(BorderFactory.createTitledBorder("Your Shopping Cart"));
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton viewCartButton = new JButton("View Cart Details");
        JButton checkoutButton = new JButton("Checkout");
        
        buttonPanel.add(viewCartButton);
        buttonPanel.add(checkoutButton);
        
        // Add components to cart panel
        cartPanel.add(quantityPanel, BorderLayout.NORTH);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
        cartPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add components to main panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, cartPanel);
        splitPane.setResizeWeight(0.6);
        
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        // Event handlers
        addToCartButton.addActionListener(this::addToCart);
        viewCartButton.addActionListener(this::viewCart);
        checkoutButton.addActionListener(this::checkout);
        
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadProductsToTable() {
        productTableModel.setRowCount(0); // Clear existing data
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, price, category, description FROM products")) {
            while (rs.next()) {
                productTableModel.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("category"),
                    rs.getString("description")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }

    private void addToCart(ActionEvent e) {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product first");
            return;
        }
        
        String quantity = quantityField.getText();
        if (!quantity.matches("\\d+") || quantity.equals("0")) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid quantity (1 or more).", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String productId = (String) productTableModel.getValueAt(selectedRow, 0);
        String productName = (String) productTableModel.getValueAt(selectedRow, 1);
        double price = (Double) productTableModel.getValueAt(selectedRow, 2);
        
        cartArea.append(String.format("%s (ID: %s) x%s - $%.2f each\n", 
            productName, productId, quantity, price));
        quantityField.setText("");
    }

    private void viewCart(ActionEvent e) {
        if (cartArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty", "Cart", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                new JScrollPane(new JTextArea(cartArea.getText())), 
                "Cart Contents", 
                JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void checkout(ActionEvent e) {
        if (cartArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty", "Checkout", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Proceed with checkout?\n\n" + cartArea.getText(),
            "Confirm Checkout",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Here you would typically save the order to database
            JOptionPane.showMessageDialog(this, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cartArea.setText("");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new CustomerGUI();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to start application: " + e.getMessage());
            }
        });
    }
}