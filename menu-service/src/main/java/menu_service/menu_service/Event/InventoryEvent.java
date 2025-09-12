package menu_service.menu_service.Event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class InventoryEvent {

    private final KafkaTemplate<String, InventoryDTO> kafkaTemplate;

    @Autowired
    public InventoryEvent(KafkaTemplate<String, InventoryDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void sendEvent(InventoryDTO event) {
        kafkaTemplate.send("inventory-create-service", event);
    }
}
