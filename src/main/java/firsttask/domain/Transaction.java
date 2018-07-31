package firsttask.domain;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
//@Table("money_events_v2")
@Table("money_events_fix")
@Data
public class Transaction {

    @Id
    @PrimaryKey
    private final UUID id;
    private String username;
    private Double amount;
    private Long timestamp;

    public Transaction(){
        id = UUID.randomUUID();
    }
//    @PrimaryKey
//    private TransactionID transactionID;
//    private Double amount;
//
//    public String getUsername(){
//        return transactionID.getUsername();
//    }
//
//    public void setUsername(String username){
//        transactionID.setUsername(username);
//    }
//
//    public Long getTimestamp(){
//        return transactionID.getTimestamp();
//    }
//
//    public void setTimestamp(Long timestamp){
//        transactionID.setTimestamp(timestamp);
//    }
}