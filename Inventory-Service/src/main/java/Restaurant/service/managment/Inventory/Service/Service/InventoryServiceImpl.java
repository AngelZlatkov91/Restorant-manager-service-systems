package Restaurant.service.managment.Inventory.Service.Service;

import Restaurant.service.managment.Inventory.Service.Event.InventoryDTO;
import Restaurant.service.managment.Inventory.Service.Event.InventoryProductsDTO;
import Restaurant.service.managment.Inventory.Service.Models.Inventory;
import Restaurant.service.managment.Inventory.Service.Models.InventorytODTO;
import Restaurant.service.managment.Inventory.Service.Models.UpdateInventoryDTO;
import Restaurant.service.managment.Inventory.Service.Repo.InventoryRepositories;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepositories inventoryRepositories;

    public InventoryServiceImpl(InventoryRepositories inventoryRepositories) {
        this.inventoryRepositories = inventoryRepositories;
    }

    @Override
    public List<InventorytODTO> getAllInventory() {
     return inventoryRepositories
             .findAll()
             .stream()
             .map(this::mapInventoryDTO)
             .toList();
    }

    @Override
    public void addInventory(InventoryDTO inventory) {
        Inventory inventory1 = new Inventory();
        inventory1.setName(inventory.getNameItems());
        inventory1.setCategory(inventory.getCategory());
        inventory1.setCheck(false);
        inventoryRepositories.save(inventory1);
    }

    @Override
    @Transactional
    public void updateInventory(UpdateInventoryDTO updateInventoryDTO) {
        Optional<Inventory> byId = inventoryRepositories.findById(updateInventoryDTO.getId());
        if (byId.isPresent()) {
            int quantity = byId.get().getQuantity();
            byId.get().setCheck(true);
            byId.get().setActive(true);
            byId.get().setQuantity(quantity + updateInventoryDTO.getQuantity());
            inventoryRepositories.save(byId.get());
        }
    }

    @Override
    @Transactional
    public void deleteInventory(String itemName) {
        Optional<Inventory> byName = inventoryRepositories.findByName(itemName);
        inventoryRepositories.deleteById(byName.get().getId());
    }

    @Override
    public void updateQuantity(InventoryProductsDTO inventoryProductsDTO) {
        inventoryProductsDTO.getProducts().forEach(p -> {
            Optional<Inventory> byName = inventoryRepositories.findByName(p.getProductName());
            int quantity = byName.get().getQuantity();
            byName.get().setQuantity(quantity - p.getQuantity());
            inventoryRepositories.save(byName.get());
        });
    }


    private InventorytODTO mapInventoryDTO(Inventory inventory) {
        return new InventorytODTO(
                inventory.getId()
                ,inventory.getName()
                ,inventory.getCategory()
                ,inventory.getQuantity()
                ,inventory.isActive()
                ,inventory.isCheck());
    }

}
