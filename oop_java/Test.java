import java.util.*;

public class Test {
    private static Scanner scanner = new Scanner(System.in);
    private static HashMap<String, User> users = new HashMap<>();
    private static Seller.SellerDirectory sellerDirectory = new Seller.SellerDirectory();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void signUp() {
        System.out.println("\nSign Up as:");
        System.out.println("1. Seller");
        System.out.println("2. Customer");
        System.out.print("Choose a role: ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
       // Validate phone number
        String phoneNumber = "";
        while (true) {
            try {
                System.out.println("Enter phone number (9 digits): ");
                phoneNumber = scanner.nextLine();

                // Check if the phone number has exactly 9 digits and contains only numbers
                if (phoneNumber.length() != 9 || !phoneNumber.matches("[0-9]+")) {
                    throw new IllegalArgumentException("Invalid phone number. Please enter exactly 9 digits.");
                }

                // If valid phone number, exit the loop
                System.out.println("Valid phone number: " + phoneNumber);
                break;
            } catch (IllegalArgumentException e) {
                // Catch the exception and print the error message
                System.out.println(e.getMessage());
            }
        }


        if (users.containsKey(email)) {
            System.out.println("User with this email already exists! Try logging in.");
            return;
        }

        if (roleChoice == 1) {
            Seller seller = new Seller(name, email, address, password, phoneNumber);
            users.put(email, seller);
            sellerDirectory.addSeller(seller);
            System.out.println("Sign-up successful as a Seller!");
            //
            sellerMenu(seller);
        } else if (roleChoice == 2) {
            Customer customer = new Customer(name, email, address, password, phoneNumber);
            users.put(email, customer);
            customerMenu(customer);
            System.out.println("Sign-up successful as a Customer!");
        } else {
            System.out.println("Invalid role choice! Please try again.");
        }
    }

    private static void login() {
        System.out.print("\nEnter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(email);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful! Welcome, " + user.getName());

            if (user instanceof Seller) {
                sellerMenu((Seller) user);
            } else if (user instanceof Customer) {
                customerMenu((Customer) user);
            }
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }

    private static void sellerMenu(Seller seller) {
        while (true) {
            System.out.println("\nSeller Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Remove Product");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addProduct(seller);
                    break;
                case 2:
                    seller.displayMyProducts();
                    break;
                case 3:
                    removeProduct(seller);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addProduct(Seller seller) {
        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price:$ ");
        double price = scanner.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        seller.addProduct(productId, name, price, quantity, category, description);
    }

    private static void removeProduct(Seller seller) {
        System.out.print("Enter product ID to remove: ");
        String productId = scanner.nextLine();
        seller.removeProduct(productId);
    }

    private static void customerMenu(Customer customer) {
        while(true){
        System.out.println("\nCustomer Menu:");
        System.out.println("1. View Products");
        System.out.println("2. Buy Product");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // Assuming a method to view all available products
                customer.viewProduct();
                break;
            case 2:
                System.out.print("Enter product name to buy: ");
                String name = scanner.nextLine();
                customer.searchProductByName(name);
                break;
        //     case 3:
        //         System.out.println("Logging out...");
        //         return;
            default:
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
