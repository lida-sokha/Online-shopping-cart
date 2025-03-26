package oop_java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SellerGUI extends JFrame {
    private JTextField idField, nameField, priceField, quantityField, categoryField, descriptionField, sellerIdField;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private Connection conn;

    public SellerGUI() {
        setTitle("Seller Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main container with BorderLayout
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Database connection
        conn = MySQLConnection.getConnection();
        
        // Table for products with improved appearance
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Quantity", "Category", "Description", "Seller ID", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = productTable.getSelectedRow();
                if (row >= 0) {
                    idField.setText(tableModel.getValueAt(row, 0).toString());
                    nameField.setText(tableModel.getValueAt(row, 1).toString());
                    priceField.setText(tableModel.getValueAt(row, 2).toString());
                    quantityField.setText(tableModel.getValueAt(row, 3).toString());
                    categoryField.setText(tableModel.getValueAt(row, 4).toString());
                    descriptionField.setText(tableModel.getValueAt(row, 5).toString());
                    sellerIdField.setText(tableModel.getValueAt(row, 6).toString());
                }
            }
        });
        
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Product Inventory"));
        add(tableScrollPane, BorderLayout.CENTER);

        // Input panel with GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(220, 220, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // First Column
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Product ID:"), gbc);
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Price:"), gbc);
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Quantity:"), gbc);

        // Second Column
        gbc.gridx = 1; gbc.gridy = 0;
        idField = new JTextField(20);
        inputPanel.add(idField, gbc);
        gbc.gridy = 1;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);
        gbc.gridy = 2;
        priceField = new JTextField(20);
        inputPanel.add(priceField, gbc);
        gbc.gridy = 3;
        quantityField = new JTextField(20);
        inputPanel.add(quantityField, gbc);

        // Third Column
        gbc.gridx = 2; gbc.gridy = 0;
        inputPanel.add(new JLabel("Category:"), gbc);
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Seller ID:"), gbc);

        // Fourth Column
        gbc.gridx = 3; gbc.gridy = 0;
        categoryField = new JTextField(20);
        inputPanel.add(categoryField, gbc);
        gbc.gridy = 1;
        descriptionField = new JTextField(20);
        inputPanel.add(descriptionField, gbc);
        gbc.gridy = 2;
        sellerIdField = new JTextField(20);
        inputPanel.add(sellerIdField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(220, 220, 220));
        
        JButton addButton = createStyledButton("Add Product", new Color(76, 175, 80));
        JButton removeButton = createStyledButton("Remove Product", new Color(244, 67, 54));
        JButton sellButton = createStyledButton("Sell Product", new Color(33, 150, 243));
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(sellButton);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.CENTER;
        inputPanel.add(buttonPanel, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addProduct());
        removeButton.addActionListener(e -> removeProduct());
        sellButton.addActionListener(e -> sellProduct());

        // Load products from database
        loadProducts();
        
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.black);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 30));
        return button;
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

    private void addProduct() {
        try {
            if (validateFields()) {
                try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO products (id, name, price, quantity, category, description, seller_id) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                    ps.setString(1, idField.getText());
                    ps.setString(2, nameField.getText());
                    ps.setDouble(3, Double.parseDouble(priceField.getText()));
                    ps.setInt(4, Integer.parseInt(quantityField.getText()));
                    ps.setString(5, categoryField.getText());
                    ps.setString(6, descriptionField.getText());
                    ps.setString(7, sellerIdField.getText());
                    ps.executeUpdate();
                    loadProducts();
                    clearFields();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding product: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeProduct() {
        try {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a product to remove", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to remove this product?", 
                "Confirm Removal", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM products WHERE id = ?")) {
                    ps.setString(1, idField.getText());
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Product removed successfully");
                        loadProducts();
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Product not found");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error removing product: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sellProduct() {
        try {
            if (validateFields()) {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be positive", 
                        "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?")) {
                    ps.setInt(1, quantity);
                    ps.setString(2, idField.getText());
                    ps.setInt(3, quantity);
                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Product sold successfully");
                        loadProducts();
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Not enough stock or product not found!", 
                            "Operation Failed", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error selling product: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateFields() {
        try {
            if (idField.getText().isEmpty() || nameField.getText().isEmpty() || 
                sellerIdField.getText().isEmpty()) {
                throw new IllegalArgumentException("Required fields cannot be empty");
            }
            Double.parseDouble(priceField.getText());
            Integer.parseInt(quantityField.getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price must be a number and Quantity must be an integer", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
        categoryField.setText("");
        descriptionField.setText("");
        sellerIdField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SellerGUI();
        });
    }
}