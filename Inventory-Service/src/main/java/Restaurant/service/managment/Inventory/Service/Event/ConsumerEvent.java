package Restaurant.service.managment.Inventory.Service.Event;

import Restaurant.service.managment.Inventory.Service.Service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerEvent {

    private final InventoryService inventoryService;
    @Autowired
    public ConsumerEvent(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "inventory-create-service", groupId = "inventory-service")
    public void listen(InventoryDTO event) {
        inventoryService.addInventory(event);
    }
}
