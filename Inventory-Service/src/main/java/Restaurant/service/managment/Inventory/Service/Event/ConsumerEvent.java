package Restaurant.service.managment.Inventory.Service.Event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerEvent {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public ConsumerEvent(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendItemStatus(ChangeStatusItem item) {
        log.info("Sending ChangeStatusItem: {}", item);
        kafkaTemplate.send("inventory-change-status", item);
    }
}
