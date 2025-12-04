package Restaurant.service.managment.Inventory.Service.Api;
import Restaurant.service.managment.Inventory.Service.Models.CheckInventoryDTO;
import Restaurant.service.managment.Inventory.Service.Models.UpdateInventoryDTO;
import Restaurant.service.managment.Inventory.Service.Service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckInventoryDTO> getItemInventory(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CheckInventoryDTO>> getAll () {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @PostMapping ("/update")
    public ResponseEntity<String> updateInventory(@RequestBody UpdateInventoryDTO updateInventoryDTO) {
        inventoryService.updateInventory(updateInventoryDTO);
      return  ResponseEntity.ok("update");
    }

}
