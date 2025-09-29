package Restaurant.service.managment.Inventory.Service.Service;

import Restaurant.service.managment.Inventory.Service.Event.InventoryDTO;
import Restaurant.service.managment.Inventory.Service.Models.InventorytODTO;
import Restaurant.service.managment.Inventory.Service.Models.UpdateInventoryDTO;

import java.util.List;

public interface InventoryService {

    List<InventorytODTO> getAllInventory();
    void addInventory(InventoryDTO inventory);

    void updateInventory(UpdateInventoryDTO updateInventoryDTO);

    void deleteInventory(Long id);

    void updateQuantity();

}
