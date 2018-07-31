package firsttask.services;

import firsttask.domain.AuthUser;
import firsttask.domain.Role;
import firsttask.repositories.AuthUserRepository;
import firsttask.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthUserRepository authUserRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String signUp(AuthUser user){
        if (getUser(user.getUsername()) == null) {
            user.setDecodedPassword(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList(Role.ROLE_USER));
            authUserRepository.save(user);
            return "You've successfully signed up!";
        }
        return "User already exists";
    }

    public String signUp(AuthUser user, String role){
        if (getUser(user.getUsername()) == null && Role.valueOf(role) != null) {
            user.setDecodedPassword(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList(Role.ROLE_USER, Role.valueOf(role)));
            authUserRepository.save(user);
            return "You've successfully signed up!";
        }
        return "User already exists or role is not exists!";
    }


    public String signIn(AuthUser user){
        AuthUser foundUser = authUserRepository.findByUsername(user.getUsername());
        if (foundUser != null &&
                passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {

            return jwtTokenProvider.createToken(foundUser);
        } else {
            return "Invalid username/password supplied";
        }
    }

    public List<AuthUser> getAll(){
        List<AuthUser> users = new ArrayList<>();
        authUserRepository.findAll().forEach(users::add);
        return users;
    }

    public AuthUser getUser(String username){
        return authUserRepository.findByUsername(username);
    }

}
