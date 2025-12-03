package Restaurant.service.managment.Inventory.Service.Event;
import Inventory.menu.InventoryDTO;
import order.inventory.InventoryProductsDTO;
import Restaurant.service.managment.Inventory.Service.Service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ListenerEvent {

    private final InventoryService inventoryService;

    @Autowired
    public ListenerEvent(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(
            topics = "inventory-create-item",
            groupId = "inventory-group"
    )
    public void itemCreateListener(InventoryDTO event)  {
        log.info("Received InventoryDTO: {}", event);
        inventoryService.addInventory(event);
    }

    @KafkaListener(
            topics = "inventory-delete-item",
            groupId = "inventory-group"
    )
    public void itemDeleteListener(InventoryDTO inventoryDTO) {
        log.info("Received delete InventoryDTO: {}", inventoryDTO);
        inventoryService.deleteInventory(inventoryDTO.getNameItems());
    }

    @KafkaListener(
            topics = "inventory-products",
            groupId = "inventory-group"
    )
    public void ordersProducts(InventoryProductsDTO inventoryProductsDTO) {
        log.info("Received InventoryProductsDTO: {}", inventoryProductsDTO);
        inventoryService.updateQuantity(inventoryProductsDTO);
    }
}
