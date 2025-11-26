package Restaurant.service.managment.Inventory.Service.Service;

import Restaurant.service.managment.Inventory.Service.Event.InventoryDTO;
import Restaurant.service.managment.Inventory.Service.Event.InventoryProductsDTO;
import Restaurant.service.managment.Inventory.Service.Models.InventorytODTO;
import Restaurant.service.managment.Inventory.Service.Models.UpdateInventoryDTO;

import java.util.List;
import java.util.zip.DataFormatException;

public interface InventoryService {

    List<InventorytODTO> getAllInventory();
    void addInventory(InventoryDTO inventory) throws DataFormatException;

    void updateInventory(UpdateInventoryDTO updateInventoryDTO);

    void deleteInventory(String itemNme);

    void updateQuantity(InventoryProductsDTO inventoryProductsDTO);

    InventorytODTO getById(Long id);
}
