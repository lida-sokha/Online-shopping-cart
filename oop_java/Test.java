    public static void main(String[] args) {
        UserInterface user1 = new Customer("Alice", "alice@example.com", "123 Street", "password123", "555-1234");
        UserInterface user2 = new Staff("Bob", "bob@example.com", "456 Avenue", "securePass", "555-5678", "IT");

        user1.displayUserInfo();
        System.out.println();
        user2.displayUserInfo();
    }
