package order.services.order.services.Services.OrderServ;

import order.services.order.services.Models.DTO.OrderDTO;
import order.services.order.services.Models.DTO.OrderResp;

public interface CreateAndUpdateOrderServ {


    void createOrder(OrderDTO order) ;
    void updateOrder(OrderResp order);
}
