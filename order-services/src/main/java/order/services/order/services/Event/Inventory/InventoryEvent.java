package order.services.order.services.Event.Inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryEvent {

    private final KafkaTemplate<String, InventoryProductsDTO> kafkaTemplate;

    @Autowired
    public InventoryEvent(KafkaTemplate<String, InventoryProductsDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void sendEvent(InventoryProductsDTO event) {
        kafkaTemplate.send("inventory-products", event);
    }


}
