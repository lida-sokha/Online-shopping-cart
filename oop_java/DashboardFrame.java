package oop_java;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class DashboardFrame extends JFrame{
    private JTable productTable;
    private JScrollPane scrollPane;

    public DashboardFrame(){
        setTitle("Deshboard");
        setSize(1000,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create JTable to display products
        String[] columnNames = {"ID", "Name", "Price", "Quantity", "Category", "Description", "Seller ID"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(model);
        scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load product data from the database
        loadProducts();

        setVisible(true);
    }

       // Method to load all products from the database
       private void loadProducts() {
        try (Connection conn = MySQLConnection.getConnection()) {
            String sql = "SELECT * FROM products";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                DefaultTableModel model = (DefaultTableModel) productTable.getModel();
                while (rs.next()) {
                    // Add a row for each product
                    Object[] row = new Object[7];
                    row[0] = rs.getInt("id");
                    row[1] = rs.getString("name");
                    row[2] = rs.getInt("price");
                    row[3] = rs.getInt("quantity");
                    row[4] = rs.getString("category");
                    row[5] = rs.getString("description");
                    row[6] = rs.getInt("seller_id");
                    model.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching products from the database.");
        }
    }

    public static void main(String[] args) {
        new DashboardFrame(); // This will create and show the GUI window
    }
}