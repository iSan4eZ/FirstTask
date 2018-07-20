package FirstTask.Kafka;

import FirstTask.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerServer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${jsa.kafka.topic}")
    String kafkaTopic = "money_events";

    public void send(Transaction transaction) {
        log.info("sending data='{}'", transaction.toString());

        kafkaTemplate.send(kafkaTopic, transaction.toString());
    }
}
