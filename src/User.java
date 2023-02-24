import java.util.ArrayList;

public class User {
    private String user;
    private String pass;
    private String role;

    static ArrayList<User> users = new ArrayList<>();

    public User(){

    }

    public User(String user, String pass, String role) {
        this.user = user;
        this.pass = pass;
        this.role = role;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void createUsers() {

        users.clear();

        users.add(new User("admin","admin", "admin"));
        users.add(new User("cust","cust","customer"));
    }

    @Override
    public String toString() {
        return "aerolinea.User{" +
                "user='" + this.user + '\'' +
                ", pass='" + this.pass + '\'' +
                ", role='" + this.role + '\'' +
                '}';
    }
}
