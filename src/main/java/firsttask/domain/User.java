package firsttask.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.PRIVATE)
    private int id;
    private String username;
    private Double balance;

    public User(String username, Double balance){
        this.username = username;
        this.balance = balance;
    }
}
