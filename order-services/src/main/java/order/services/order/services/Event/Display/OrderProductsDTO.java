package order.services.order.services.Event.Display;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderProductsDTO {
    private String productName;
    private String productDescription;
    private int quantity;

}
