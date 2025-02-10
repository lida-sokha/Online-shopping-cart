import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Display the main menu options
        System.out.println("Select an option:");
        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        System.out.println("0. Exit");
        System.out.println("enter your choice:");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character after reading integer

        while (choice != 0) {
            // Ask the user to choose an option until they choose to exit
            switch (choice) {
                case 1:
                    // Handle Sign Up
                    System.out.println("\nSign Up - Enter your details:");

                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter your address: ");
                    String address = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    System.out.print("Enter your phone number: ");
                    String phoneNumber = scanner.nextLine();

                    // Call the signUp method from User class
                    boolean signUpSuccess = User.signUp(name, email, address, password, phoneNumber);
                    if (!signUpSuccess) {
                        System.out.println("Sign up failed. Please try again.");
                    }
                    else{
                        System.out.println("Sign up sccessfully");
                    }
                    break;

                case 2:
                    // Handle Login
                    System.out.println("\nLogin - Please enter your credentials:");

                    System.out.print("Enter your email: ");
                    String loginEmail = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String loginPassword = scanner.nextLine();

                    // Call login method to handle validation and login process
                    boolean loginSuccess = User.login(loginEmail, loginPassword);
                    if (!loginSuccess) {
                        System.out.println("Login failed. Please check your credentials.");
                    }
                    break;

                default:
                    // Handle invalid input
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }

            // Prompt the user again for the next option
            System.out.println("Select an option:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        }

        // Exit message
        System.out.println("Exiting the program.");
        scanner.close();
    }
}
