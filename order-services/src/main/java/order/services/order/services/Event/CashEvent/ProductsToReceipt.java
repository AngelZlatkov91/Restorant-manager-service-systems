package order.services.order.services.Event.CashEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductsToReceipt {
    private String productName;
    private int quantity;
    private Double price;
}
