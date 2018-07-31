package firsttask.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
@Data
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String username;
    private Double balance;

    public User(String username, Double balance){
        this.username = username;
        this.balance = balance;
    }

    public User() {

    }

    public void setLanguages(String... languages) {
    }

    public void setRole(String developer) {
    }
}