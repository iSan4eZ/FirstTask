package firsttask.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table("money_events_fix")
@Getter
@Setter
public class Transaction {

    @Id
    @PrimaryKey
    @Setter(AccessLevel.PRIVATE)
    private final UUID id;
    private String username;
    private Double amount;
    @Setter(AccessLevel.PRIVATE)
    private final Long timestamp;

    public Transaction(){
        timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "<b>Username:</b> " + username + "</br>" +
                "<b>Amount:</b> " + amount + "</br>" +
                "<b>Timestamp:</b> " + timestamp + "</br>";
    }
}
