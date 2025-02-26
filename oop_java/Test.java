import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = null;

        while (true) {
            System.out.println("1. Sign up as Seller");
            System.out.println("2. Sign up as Customer");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();

                    user = new Seller(name, email, address, password, phoneNumber);
                    System.out.println("Sign up successful as Seller!");
                    break;
                case 2:
                    System.out.print("Enter name: ");
                    name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    email = scanner.nextLine();
                    System.out.print("Enter address: ");
                    address = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    phoneNumber = scanner.nextLine();

                    user = new Customer(name, email, address, password, phoneNumber);
                    System.out.println("Sign up successful as Customer!");
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (user instanceof Seller) {
                Seller seller = (Seller) user;
                System.out.print("Enter product name: ");
                String productName = scanner.nextLine();
                System.out.print("Enter product price: ");
                double productPrice = scanner.nextDouble();
                scanner.nextLine(); // Consume newline left-over
                System.out.print("Enter product quantity: ");
                int productQuantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                seller.addProduct(productName, productPrice, productQuantity);
                System.out.println("Product added successfully!");
            } else if (user instanceof Customer) {
                Customer customer = (Customer) user;
                System.out.print("Enter product name: ");
                String productName = scanner.nextLine();

                customer.viewProduct(productName);
                System.out.println("Product viewed successfully!");
            }
        }
    }
}