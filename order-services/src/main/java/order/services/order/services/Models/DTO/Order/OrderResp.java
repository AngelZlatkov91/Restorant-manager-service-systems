package order.services.order.services.Models.DTO.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.services.order.services.Models.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderResp {
    private Long id;
    private String tableName;
    private String personalName;
    private OrderStatus status;
    private LocalDateTime createdAd;
    private List<AddProductToTableDTO> products;

    public OrderResp(String tableName, String personalName) {
        this.tableName = tableName;
        this.personalName = personalName;
    }
}
