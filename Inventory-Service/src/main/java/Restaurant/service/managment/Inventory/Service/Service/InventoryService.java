package Restaurant.service.managment.Inventory.Service.Service;

import order.inventory.InventoryProductsDTO;

import Restaurant.service.managment.Inventory.Service.Models.CheckInventoryDTO;
import Restaurant.service.managment.Inventory.Service.Models.UpdateInventoryDTO;

import java.util.List;

public interface InventoryService {

    List<CheckInventoryDTO> getAllInventory();
    void addInventory(Inventory.menu.InventoryDTO inventory) ;

    void updateInventory(UpdateInventoryDTO updateInventoryDTO);

    void deleteInventory(String itemNme);

    void updateQuantity(InventoryProductsDTO inventoryProductsDTO);

    CheckInventoryDTO getById(Long id);
}
