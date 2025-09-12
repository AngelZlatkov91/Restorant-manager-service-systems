package order.services.order.services.Models.DTO.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteProduct {
    private Long orderId;
    private int indexProduct;
}
