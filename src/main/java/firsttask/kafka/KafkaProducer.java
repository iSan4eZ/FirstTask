package firsttask.kafka;

import firsttask.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${jsa.kafka.topic}")
    String kafkaTopic = "${jsa.kafka.topic}";

    public void send(Transaction transaction) {
        log.info("sending data='{}'", transaction.toString());

        kafkaTemplate.send(kafkaTopic, transaction.toString());
    }
}
