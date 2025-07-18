package order.services.order.services.Services;

import order.services.order.services.Models.DTO.CheckOrders;
import order.services.order.services.Models.DTO.OrderDTO;
import order.services.order.services.Models.DTO.OrderResp;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OrderService {

    void createOrder(OrderDTO order) throws ExecutionException, InterruptedException;
    void updateOrder(OrderResp order) throws ExecutionException, InterruptedException;

    OrderResp getOrder(Long orderId);

    List<CheckOrders> checkOrders (CheckOrders checkOrders);
}
