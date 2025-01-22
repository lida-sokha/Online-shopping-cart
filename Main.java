public class Main {
    public static void main(String[] args) {
        // try {
        //     User user = new User("Alice", "alice@example.com", "123 Main St", "short1234567", "1234567890");
        //     System.out.println(user.password());
        // } catch (IllegalArgumentException e) {
        //     System.out.println("Error: " + e.getMessage());
        // }
        User user1 = new User("Alice", "alice@example.com", "123 Main St", "password123", "9876543210");
        User user2 = new User("Bob", "bob@example.com", "456 Elm St", "securePass456", "1234567890");
        User user3 = new User("Charlie", "charlie@example.com", "789 Oak St", "myPassword789", "1122334455");

    System.out.println("//-----------------------------------------//"+ "\n");
        System.out.println(user1);
        System.out.println();
        System.out.println(user2);
        System.out.println();
        System.out.println(user3);
        System.out.println("//-----------------------------------------//"+ "\n");
        Payment lika = new Payment(1,1,2025, 12.5);
        System.out.println(lika);
        Payment lika1 = new Payment(1,1,2025, 12.5);
        System.out.println(lika1);
    }
}
