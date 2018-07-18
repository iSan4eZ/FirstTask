package FirstTask.user;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String username;
    private Double balance;

    public User(){

    }

    public User(String username, Double balance){
        this.username = username;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
