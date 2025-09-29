package Restaurant.service.managment.Inventory.Service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventorytODTO {
    private Long id;

    private String name;

    private String category;

    private int quantity;

    private boolean active;

    private boolean isCheck;
}
