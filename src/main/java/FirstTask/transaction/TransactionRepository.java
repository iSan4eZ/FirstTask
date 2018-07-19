package FirstTask.transaction;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, String> {

    @Query("SELECT * FROM money_events_fix WHERE username=?0 ALLOW FILTERING")
    List<Transaction> findByUsername(String username);

}
