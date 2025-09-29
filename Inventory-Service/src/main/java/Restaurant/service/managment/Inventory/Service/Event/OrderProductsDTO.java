package Restaurant.service.managment.Inventory.Service.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderProductsDTO {
    private String productName;
    private String category;
    private int quantity;
}
