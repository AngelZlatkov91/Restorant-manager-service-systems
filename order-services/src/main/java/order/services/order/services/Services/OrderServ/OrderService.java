package order.services.order.services.Services.OrderServ;

import order.services.order.services.Models.DTO.Order.CheckOrders;
import order.services.order.services.Models.DTO.Order.OrderResp;

import java.util.List;

public interface OrderService {


    OrderResp getOrder(Long orderId);

    List<CheckOrders> checkOrders (CheckOrders checkOrders);
}
