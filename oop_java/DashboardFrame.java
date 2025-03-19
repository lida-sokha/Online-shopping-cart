package oop_java;

import javax.swing.*;

public class DashboardFrame extends JFrame{
    public DashboardFrame(){
        setTitle("Deshboard");
        setSize(1000,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel welcomeLabel = new JLabel("Welcome to the online system");
        add(welcomeLabel);
        setVisible(true);
    }
    public static void main(String[] args) {
        new DashboardFrame(); // This will create and show the GUI window
    }
}