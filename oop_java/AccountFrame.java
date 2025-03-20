package oop_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountFrame extends JFrame{
    private JButton loginButton;
    private JButton signUpButton;

    public AccountFrame() {
        setTitle("User Authentication");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2, 10, 10));

        // Buttons
        loginButton = new JButton();
        loginButton.setText("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
        add(loginButton);

        signUpButton = new JButton();
        signUpButton.setText("Sign up");
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignupFrame signupFrame = new SignupFrame();
                signupFrame.setVisible(true);
            }
        });
        add(signUpButton);
    setVisible(true);
    }
    public static void main(String[] args) {
        new AccountFrame();
    }
}
