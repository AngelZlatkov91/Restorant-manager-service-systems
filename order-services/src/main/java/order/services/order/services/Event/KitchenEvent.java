package order.services.order.services.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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


    public void sendEvent(KitchenDTO event) {
         kafkaTemplate.send("kitchen-display", event);
    }
}
