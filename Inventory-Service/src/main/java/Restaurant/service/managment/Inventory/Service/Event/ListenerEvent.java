package Restaurant.service.managment.Inventory.Service.Event;

import Restaurant.service.managment.Inventory.Service.Service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.zip.DataFormatException;

@Component
@Slf4j
public class ListenerEvent {

    private final InventoryService inventoryService;

    @Autowired
    public ListenerEvent(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "inventory-create-item", groupId = "inventory-service")
    public void itemCreateListener(InventoryDTO event) throws DataFormatException {
        inventoryService.addInventory(event);
    }

    @KafkaListener(topics = "inventory-delete-item",groupId = "inventory-service")
    public void itemDeleteListener(InventoryDTO inventoryDTO) {
        inventoryService.deleteInventory(inventoryDTO.getNameItems());
    }

    @KafkaListener(topics = "inventory-products", groupId = "inventory-service")
    public void ordersProducts(InventoryProductsDTO inventoryProductsDTO) {
        inventoryService.updateQuantity(inventoryProductsDTO);
    }



}
