package order.services.order.services.Models.DTO.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.services.order.services.Models.OrderStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderResp {
    private Long id;
    private String table_name;
    private String personal_name;
    private OrderStatus status;
    private List<AddProductToTableDTO> products;

}
