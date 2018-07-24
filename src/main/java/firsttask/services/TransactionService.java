package firsttask.services;

import firsttask.domain.Transaction;
import firsttask.repositories.TransactionRepository;
import firsttask.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import firsttask.domain.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public List<User> getUsers() {
        List<User> result = Collections.emptyList();

        try (Stream<User> stream = userRepository.findAllByCustomQueryAndStream()) {
            result = stream.collect(Collectors.toList());
        } catch (Exception e){
            log.info(e.getMessage(), e);
        }

        return result;
    }

    public User getUserById(int id){
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username){
        return userRepository.findUserByUsernameIgnoreCase(username);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public void addAmount(Transaction transaction) {
        User user = getUserByUsername(transaction.getUsername());

        transactionRepository.save(transaction);
        if (user != null){
            user.setBalance(user.getBalance() + transaction.getAmount());
            updateUser(user);
        } else {
            addUser(new User(transaction.getUsername(),transaction.getAmount()));
        }
    }

    public List<Transaction> getTransactionsByUserId(int id){
        return transactionRepository.findByUsername(userRepository.findById(id).getUsername());
    }
}
