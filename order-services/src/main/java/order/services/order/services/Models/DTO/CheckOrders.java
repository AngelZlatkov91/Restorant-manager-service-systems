package order.services.order.services.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.services.order.services.Models.OrderStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckOrders {
    private Long id;
    private String name;
    private OrderStatus status;
}
