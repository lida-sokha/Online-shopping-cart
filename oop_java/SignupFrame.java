package oop_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupFrame extends JFrame{
    private JTextField nameField, emailField, addressField, phoneNumberField, roleField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public SignupFrame() {
        setTitle("Sign up");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6,2,10,10));

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
        addressField = new JTextField();
        add(phoneNumberField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        registerButton = new JButton("Register");
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });
        setVisible(true);
    }
    private void signUp(){
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneNumberField.getText();
        String address = addressField.getText();
        String password = new String(passwordField.getPassword());

        if(phone.length() != 9 || !phone.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Invalid phone Number! Mush be 9 digit");
            return; 
        }
        try (Connection conn = MySQLConnection.getConnection()){
            String sql = "INSERT INTO user (fullname, email, address, password, phone_number) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, password); // Consider hashing it
                pstmt.setString(4, phone);
                pstmt.setString(5, address);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Sign-up Successful!");
                this.dispose(); // Close sign-up window
            }
        }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }
}