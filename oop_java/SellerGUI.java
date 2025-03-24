package oop_java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SellerGUI extends JFrame {
    private JTextField idField, nameField, priceField, quantityField;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private Connection conn;

    public SellerGUI() {
        setTitle("Seller Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Database connection
        connectToDatabase();
        
        // Table for products
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Quantity"}, 0);
        productTable = new JTable(tableModel);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("Product ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        // Buttons
        JButton addButton = new JButton("Add Product");
        JButton removeButton = new JButton("Remove Product");
        JButton sellButton = new JButton("Sell Product");

        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(sellButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addProduct());
        removeButton.addActionListener(e -> removeProduct());
        sellButton.addActionListener(e -> sellProduct());

        // Load products from database
        loadProducts();
        
        setVisible(true);
    }

    // private void connectToDatabase() {
    //     try {
    //         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password");
    //         System.out.println("Connected to database");
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

    private void loadProducts() {
        tableModel.setRowCount(0);
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getString("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addProduct() {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO products (id, name, price, quantity) VALUES (?, ?, ?, ?)");) {
            ps.setString(1, idField.getText());
            ps.setString(2, nameField.getText());
            ps.setDouble(3, Double.parseDouble(priceField.getText()));
            ps.setInt(4, Integer.parseInt(quantityField.getText()));
            ps.executeUpdate();
            loadProducts();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeProduct() {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM products WHERE id = ?");) {
            ps.setString(1, idField.getText());
            ps.executeUpdate();
            loadProducts();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sellProduct() {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?");) {
            int quantity = Integer.parseInt(quantityField.getText());
            ps.setInt(1, quantity);
            ps.setString(2, idField.getText());
            ps.setInt(3, quantity);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Not enough stock or product not found!");
            }
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }

    public static void main(String[] args) {
        new SellerGUI();
    }
}