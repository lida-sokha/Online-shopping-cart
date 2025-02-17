class Customer extends User {
    private int loyaltyPoints;

    public Customer(String name, String email, String address, String password, String phoneNumber) {
        super(name, email, address, password, phoneNumber, "customer");
        this.loyaltyPoints = 0;
    }

    public void addPoints(int points) {
        loyaltyPoints += points;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

}

class Staff extends User {
    private String department;

    public Staff(String name, String email, String address, String password, String phoneNumber, String department) {
        super(name, email, address, password, phoneNumber, "staff");
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public void displayUserInfo() {
        System.out.println("Department: " + department);
    }
}
