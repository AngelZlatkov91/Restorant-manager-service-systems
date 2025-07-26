package order.services.order.services.Event.Display;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DisplayEvent {
    private final KafkaTemplate<String, ProductEventSentDTO> kafkaTemplate;
     @Autowired
    public DisplayEvent(KafkaTemplate<String, ProductEventSentDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void kitchenSendEvent(ProductEventSentDTO event) {
         kafkaTemplate.send("kitchen-display", event);
    }

    public void barSendEvent(ProductEventSentDTO event) {
        kafkaTemplate.send("bar-display", event);
    }
}
