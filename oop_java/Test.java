import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Creating a User object for testing
        User user1 = new User("John Doe", "john.doe@example.com", "123 Elm Street", "password123", "1234567890", "customer");

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
                    user1.signUp();
                    break;
                case 2:
                    // Log in
                    System.out.println("Enter email: ");
                    String loginEmail = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    // Log in the user
                    User loggedInUser = user1.login(loginEmail, loginPassword);
                    if (loggedInUser != null) {
                        System.out.println("Login successful.");
                        loggedInUser.displayUserInfo();
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
