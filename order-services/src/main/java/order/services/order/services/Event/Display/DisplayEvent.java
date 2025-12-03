package order.services.order.services.Event.Display;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import printOrder.ProductEventSentDTO;

@Component
@Slf4j
public class DisplayEvent {

    private final KafkaTemplate<String, Object> kafkaTemplate;

     @Autowired
    public DisplayEvent(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void kitchenSendEvent(ProductEventSentDTO event) {
         kafkaTemplate.send("kitchen-display", event);
    }

    public void barSendEvent(ProductEventSentDTO event) {
        kafkaTemplate.send("bar-display", event);
    }

    public void checkDisplay(ProductEventSentDTO event) {
         kafkaTemplate.send("check-display", event);
    }

}
