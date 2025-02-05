    public class User {
        private String name;
        private static int nextuserID=0;
        private int userID;
        private String address;
        private String email;
        private String password;
        private String phoneNumber;

        //Constructor
        public User(String name, String email, String address, String password, String phoneNumber){
            this.userID=++nextuserID;
            this.name=name;
            this.email=email;
            this.address=address;
            this.password=password;
            this.phoneNumber=phoneNumber;
        }
        //getter and setter need to have contraction 
        // public int getUserId() {
        //     return userID;
        // }
        public String toString() {
            return  "User: " +userID + "\n"+
                    "Name: " + name + "\n" +
                    "Email: " + email + "\n" +
                    "Address: " + address + "\n" +
                    "Password: " + password + "\n" +
                    "Phone Number: " + phoneNumber;
        }

    }
