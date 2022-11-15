package Models;
//this class will be the model for employees
public class User {
    private int id;
    private String email;
    private String password;
    private boolean isManager;

    public User(){}

    public User(int id, String email, String password, boolean isManager){
        this.id = id;
        this.email = email;
        this.password = password;
        this.isManager = isManager;
    }

    public int getId(){
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public boolean getIsManager(){
        return this.isManager;
    }
}