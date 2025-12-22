package order.services.order.services.Services.OrderServ;

import order.services.order.services.Models.DTO.Order.OrderDTO;
import order.services.order.services.Models.DTO.Order.OrderResp;

public interface CreateAndUpdateOrderServ {


    void createOrder(OrderDTO order) ;
    void updateOrder(OrderResp order);
    void splitOrder(OrderResp order);
}
