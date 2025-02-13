    public static void main(String[] args) {
        // Sign up users
        User.signup();

        // Login and check role
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter email to login: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = User.login(email, password);
        if (loggedInUser != null) {
            System.out.println("Login successful! Role: " + loggedInUser.getRole());
            if ("Staff".equals(loggedInUser.getRole())) {
                System.out.println("Redirecting to Staff Portal...");
            } else {
                System.out.println("Redirecting to Customer Dashboard...");
            }
        }
    }