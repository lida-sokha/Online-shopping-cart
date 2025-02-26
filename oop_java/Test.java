import java.util.HashMap;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Creating a User object for testing
        HashMap<String, User> users = new HashMap<>();
        SellerDirectory sellerDirectory = new SellerDirectory(); // Initialize SellerDirectory
        while (!exit) {
            System.out.println("Welcome! Please choose an option:");
            System.out.println("1. Sign up");
            System.out.println("2. Log in");
            System.out.println("3. Exit");

            // Get the user's choice
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Sign up
                    System.out.println("Do you want to sign up as a seller or customer? (Enter 'seller' or 'customer')");
                    String accountChoice = scanner.nextLine().toLowerCase();

                    if (accountChoice.equals("seller")) {
                        // Sign up as Seller
                        System.out.println("Enter seller's name: ");
                        String sellerName = scanner.nextLine();
                        System.out.println("Enter seller's email: ");
                        String sellerEmail = scanner.nextLine();
                        System.out.println("Enter seller's address: ");
                        String sellerAddress = scanner.nextLine();
                        System.out.println("Enter seller's phone number: ");
                        String sellerPhoneNumber = scanner.nextLine();
                        System.out.println("Enter seller's password: ");
                        String sellerPassword = scanner.nextLine();

                        Seller newSeller = new Seller(sellerName, sellerEmail, sellerAddress, sellerPassword, sellerPhoneNumber);
                        users.put(sellerEmail, newSeller);  // Add seller to users map

                        // Also add the seller to the seller directory
                        sellerDirectory.addSeller(newSeller);

                        System.out.println("Seller account created successfully!");
                        System.out.println(sellerDirectory.toString()); // Display the seller directory

                    } else if (accountChoice.equals("customer")) {
                        // Sign up as Customer
                        // (Similar logic for creating a Customer would go here)
                    }
                    break;

                case 2:
                    // Log in
                    System.out.println("Enter email: ");
                    String loginEmail = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    if (users.containsKey(loginEmail)) {
                        User loggedInUser = users.get(loginEmail);
                        if (loggedInUser.getPassword().equals(loginPassword)) {
                            System.out.println("Login successful.");
                            loggedInUser.displayUserInfo();
                        } else {
                            System.out.println("Incorrect password.");
                        }
                    } else {
                        System.out.println("Email not found. Please sign up first.");
                    }
                    break;

                case 3:
                    // Exit the program
                    System.out.println("Exiting program.");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice, please select 1, 2, or 3.");
            }
        }

        scanner.close();
    }
}
