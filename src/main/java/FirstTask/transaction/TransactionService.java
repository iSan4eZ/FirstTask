package FirstTask.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import FirstTask.user.User;
import FirstTask.user.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().
                forEach(users::add);
        return users;
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
}
