package order.services.order.services.Services;

import order.services.order.services.Models.DTO.CheckOrders;
import order.services.order.services.Models.DTO.OrderDTO;
import order.services.order.services.Models.DTO.OrderResp;

import java.util.List;

public interface OrderService {

    void createOrder(OrderDTO order);
    void updateOrder(OrderResp order);

    OrderResp getOrder(Long orderId);

    List<CheckOrders> checkOrders (CheckOrders checkOrders);
}
