package Restaurant.service.managment.Inventory.Service.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductsUpdateQuantity {
    private String productName;
    private int quantity;
}
