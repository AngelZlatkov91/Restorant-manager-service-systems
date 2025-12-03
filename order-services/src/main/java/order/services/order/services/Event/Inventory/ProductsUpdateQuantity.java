package order.services.order.services.Event.Inventory;

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
