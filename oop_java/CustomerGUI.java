package oop_java;

import javax.swing.*;
import java.awt.*;

public class CustomerGUI extends JFrame {
    public CustomerGUI() {
        setTitle("Customer Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JLabel("Welcome to Customer Portal", SwingConstants.CENTER), BorderLayout.CENTER);
        
        // Add your customer components here
        JButton browseButton = new JButton("Browse Products");
        mainPanel.add(browseButton, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
        setVisible(true);
    }
    public static void main(String[] args) {
        new CustomerGUI();
    }
}