package FirstTask.Kafka;

import FirstTask.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class KafkaController {
    @Autowired
    KafkaProducerServer sender;

    @Autowired
    MessageStorage storage;

    @RequestMapping(method=RequestMethod.POST, value="/send")
    public void producer(@RequestBody Transaction transaction){
        sender.send(transaction);
    }

    @GetMapping(value="/read")
    public String getAllRecievedMessage(){
        String messages = storage.toString();
        return messages;
    }
}
