package Restaurant.service.managment.Inventory.Service.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InventoryDTO {
    private String nameItems;
    private String category;
}
