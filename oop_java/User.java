import java.util.ArrayList;

    public class User {
        private String name;
        private static int nextuserID=0;
        private int userID;
        private String address;
        private String email;
        private String password;
        private String phoneNumber;

        private static ArrayList<User> users= new ArrayList<>();
        //Constructor
        public User(String name, String email, String address, String password, String phoneNumber){
            this.userID=++nextuserID;
            this.name=name;
            this.email=email;
            this.address=address;
            this.password=password;
            this.phoneNumber=phoneNumber;

        
        }
        public String getname(){
            if(name==null || name.trim().isEmpty()){
                throw new IllegalArgumentException("Name can't be empty!");
            }
            return name;
        }
        //the requirement of email
        public String getemail(){
            if(email==null || !email.contains("@")){
                throw new IllegalArgumentException("Invalid email address.");
            }
            return email;
        }
        //the requirement of password
        public boolean checkpassword(String inputpassword){
            return this.password.equals(inputpassword);
        } 
        // Get users list
        public static ArrayList<User> getUsers() {
            return users;
        }

        public static boolean signUp(String name, String email, String address, String password, String phoneNumber){
            if(name==null || name.trim().isEmpty()){
                System.out.println("Name can't be empty!");
            return false;
            }
            if(email==null || !email.contains("@")){
                System.out.println("Invalid email");
                return false;
            }
            if(password==null || password.isEmpty()){
                System.out.println("Password can't be empty!");
                return false;
            }
            if(address==null || address.isEmpty()){
                System.out.println("address can't be empty!");
                return false;
            }
            if(password==null || password.isEmpty()){
                System.out.println("password can't be empty!");
                return false;
            }
            User newUser = new User(name, email, address, password, phoneNumber);
            users.add(newUser);
            return true;
        }
        public static boolean login(String email, String password){
            for(User user : users){
                if(user.getemail().equalsIgnoreCase(email)){
                    if(user.checkpassword(password)){
                        return true;
                    }
                    else{
                        System.out.println("Error incorrect password");
                        return false;
                    }
                }
            }
            System.out.println("Error: Email not found.");
            return false;
        }   
        

    }
