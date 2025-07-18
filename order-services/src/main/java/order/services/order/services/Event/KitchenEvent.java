package order.services.order.services.Event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class KitchenEvent {
    private final KafkaTemplate<String, KitchenDTO> kafkaTemplate;
     @Autowired
    public KitchenEvent(KafkaTemplate<String, KitchenDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendEvent(KitchenDTO event) throws ExecutionException, InterruptedException {

        SendResult<String, KitchenDTO> stringKitchenDTOSendResult = kafkaTemplate.send("kitchen-display", event).get();
        System.out.println();
        log.info(event.getPersonal().formatted("Successfully published registered event for user [%s]"));
    }
}
