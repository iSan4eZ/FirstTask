package firsttask.dtos;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;

@Entity
public class SlackDTO {

    public static void sendMessage(Object... object){

        String message = "";

        for (Object obj : object) {
            message += (obj != null) ? obj.toString() + "\n" : "";
        }

        String url = "https://hooks.slack.com/services/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";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = "{ \"text\" : \"" + message + "\" }";
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println("Response: " + response);
    }

}
