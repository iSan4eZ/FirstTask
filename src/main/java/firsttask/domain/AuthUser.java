package firsttask.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "authusers")
@Data
public class AuthUser {


    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String decodedPassword;
    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;
}
