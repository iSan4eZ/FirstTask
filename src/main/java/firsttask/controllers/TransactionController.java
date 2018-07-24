package firsttask.controllers;

import firsttask.domain.Transaction;
import firsttask.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import firsttask.domain.User;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    KafkaProducerServer sender;

    @Autowired
    MessageStorage storage;

    @RequestMapping("/users")
    public List<User> getAllUsers(){
        return transactionService.getUsers();
    }

    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return transactionService.getUserById(id);
    }

    @RequestMapping(method=RequestMethod.POST, value="/users")
    public void addUser(@RequestBody User user){
        transactionService.addUser(user);
    }

    @RequestMapping(method=RequestMethod.POST, value="/users/add")
    public void addUser(@RequestBody Transaction transaction){
        transactionService.addAmount(transaction);
        sender.send(transaction);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/users/{id}")
    public void addUser(@RequestBody User user, @PathVariable String id){
        transactionService.updateUser(user);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/users/{id}")
    public void deleteUser(@PathVariable int id) {
        transactionService.deleteUser(id);
    }

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
