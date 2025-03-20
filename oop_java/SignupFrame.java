package oop_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupFrame extends JFrame{
    private JTextField nameField, emailField, addressField, phoneNumberField;
    private JComboBox<String> roleComboBox;
    private JPasswordField passwordField;
    private JButton registerButton;

    public SignupFrame() {
        setTitle("Sign up");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7,2,10,10));

        //field label 
        add(new JLabel("Full name"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField();
        add(phoneNumberField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Role:"));
        // Create a ComboBox with options 'Customer' and 'Seller'
        roleComboBox = new JComboBox<>(new String[] {"Customer", "Seller"});
        add(roleComboBox);

        registerButton = new JButton("Register");
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        setVisible(true);
    }
    private void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String password = new String(passwordField.getPassword());
        String userType = roleComboBox.getSelectedItem().toString().toLowerCase(); // Get selected role
    
        try (Connection conn = MySQLConnection.getConnection()) {
            String sql = "INSERT INTO user (fullname, email, address, phone_number, password, user_type) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name); // Full name
                pstmt.setString(2, email); // Email
                pstmt.setString(3, address); // Address
                pstmt.setString(4, phoneNumber); // Phone number
                pstmt.setString(5, password); // Password
                pstmt.setString(6, userType); // Role (customer/seller)
    
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "User registered successfully!");
                    new DashboardFrame();
                    // dispose(); // Close the sign-up window after successful registration
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }
    public static void main(String[] args) {
        new SignupFrame();
    }
}