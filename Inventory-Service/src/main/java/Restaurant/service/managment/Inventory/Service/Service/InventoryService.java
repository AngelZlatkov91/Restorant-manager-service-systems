package Restaurant.service.managment.Inventory.Service.Service;

import Inventory.menu.InventoryDTO;
import order.inventory.InventoryProductsDTO;

import Restaurant.service.managment.Inventory.Service.Models.InventorytODTO;
import Restaurant.service.managment.Inventory.Service.Models.UpdateInventoryDTO;

import java.util.List;
import java.util.zip.DataFormatException;

public interface InventoryService {

    List<InventorytODTO> getAllInventory();
    void addInventory(InventoryDTO inventory) ;

    void updateInventory(UpdateInventoryDTO updateInventoryDTO);

    void deleteInventory(String itemNme);

    void updateQuantity(InventoryProductsDTO inventoryProductsDTO);

    InventorytODTO getById(Long id);
}
