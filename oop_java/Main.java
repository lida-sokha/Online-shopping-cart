    public static void main(String[] args) {
        User newUser = new User("sokha", "sok@gmail.com", "pp", "123", "888","staff");
        if (newUser.signUp()) {  // Check the return value!
            System.out.println("Signup successful!");
        } else {
            System.out.println("Signup failed. Please check your details or try again.");
        }
    }