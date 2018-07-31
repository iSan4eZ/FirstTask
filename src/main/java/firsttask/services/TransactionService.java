package firsttask.services;

import firsttask.domain.Transaction;
import firsttask.dtos.SlackDTO;
import firsttask.kafka.KafkaProducer;
import firsttask.repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import firsttask.domain.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    KafkaProducer sender;


    public void addAmount(Transaction transaction) {
        User user = userService.getUserByUsername(transaction.getUsername());
        transaction.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());

        SlackDTO.sendMessage("New transaction!", transaction);

        sender.send(transaction);
        transactionRepository.save(transaction);
        if (user != null){
            user.setBalance(user.getBalance() + transaction.getAmount());
            userService.updateUser(user);
        } else {
            userService.addUser(new User(transaction.getUsername(),transaction.getAmount()));
        }
    }

    public List<Transaction> getTransactionsByUserId(int id){
        return new ArrayList<Transaction>();//transactionRepository.findByUsername(userService.getUserById(id).getUsername());
    }
}