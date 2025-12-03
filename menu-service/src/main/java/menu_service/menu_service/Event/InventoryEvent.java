package menu_service.menu_service.Event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import Inventory.menu.InventoryDTO;

@Component
@Slf4j
public class InventoryEvent {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public InventoryEvent(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendItemCreateEvent(InventoryDTO event) {
        kafkaTemplate.send("inventory-create-item", event);

    }

    public void sendItemDeleteEvent(InventoryDTO event){
        kafkaTemplate.send("inventory-delete-item",event);
    }
}
