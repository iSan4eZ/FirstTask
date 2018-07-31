package firsttask.controllers;

import firsttask.domain.AuthUser;
import firsttask.domain.Transaction;
import firsttask.dtos.SlackDTO;
import firsttask.kafka.MessageStorage;
import firsttask.services.AuthService;
import firsttask.services.TransactionService;
import firsttask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import firsttask.domain.User;

import java.util.Arrays;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AuthService authService;

    @Autowired
    MessageStorage storage;


    @RequestMapping(method=RequestMethod.POST, value="/signup")
    public String addAuthUser(@RequestBody AuthUser user){
        return authService.signUp(user);
    }


    @RequestMapping(method=RequestMethod.POST, value="/signup/{role}")
    public String addAuthUserWithRole(@PathVariable String role, @RequestBody AuthUser user){
        return authService.signUp(user, role);
    }

    @RequestMapping("/authusers")
    public List<AuthUser> getAllAuthUsers(){
        return authService.getAll();
    }

    @RequestMapping("/signin/{username}/{password}")
    public String login(@PathVariable String username, @PathVariable String password){
        System.out.println("Username: " + username + "\nPass: " + password);
        AuthUser user = new AuthUser();
        user.setUsername(username);
        user.setPassword(password);
        return authService.signIn(user);
    }

    @RequestMapping("/signin")
    public String login(@RequestBody AuthUser user)
    {
        return authService.signIn(user);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/users")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @RequestMapping(method=RequestMethod.POST, value="/users")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @RequestMapping(method=RequestMethod.POST, value="/users/add")
    public void addUser(@RequestBody Transaction transaction){
        transactionService.addAmount(transaction);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/users")
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/users/log")
    public String getAllRecievedMessage(){
        String messages = storage.toString();
        return messages;
    }

    @RequestMapping("/users/{id}/transactions")
    public List<Transaction> getTransactionsByUserId(@PathVariable int id) {
        return transactionService.getTransactionsByUserId(id);
    }
}
