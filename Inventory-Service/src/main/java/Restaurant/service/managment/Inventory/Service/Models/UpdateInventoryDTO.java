package Restaurant.service.managment.Inventory.Service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateInventoryDTO {
    private Long id;
    private int quantity;

}
