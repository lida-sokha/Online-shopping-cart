package oop_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, signUpButton;

    public LoginFrame() {
        setTitle("User Authentication");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        // Labels & Fields
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Buttons
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");

        add(loginButton);
        add(signUpButton);

        // Action Listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SignupFrame().setVisible(true);
            }
        });

        setVisible(true);
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Basic validation
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password");
            return;
        }

        try (Connection conn = MySQLConnection.getConnection()) {
            String sql = "SELECT password, user_type FROM user WHERE email = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");
                        String userType = rs.getString("user_type"); // Get the role

                        if (storedPassword.equals(password)) {
                            JOptionPane.showMessageDialog(this, "Login Successful!");
                            
                            // Redirect based on role
                            if ("seller".equalsIgnoreCase(userType)) {
                                new SellerGUI().setVisible(true);
                            } else {
                                new CustomerDashboard().setVisible(true);
                            }
                            
                            this.dispose(); // Close login window
                        } else {
                            JOptionPane.showMessageDialog(this, "Incorrect password!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "User not found!");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}


