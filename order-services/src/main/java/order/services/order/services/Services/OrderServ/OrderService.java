package order.services.order.services.Services.OrderServ;

import order.services.order.services.Models.DTO.Order.CheckOrders;
import order.services.order.services.Models.DTO.Order.OrderResp;
import order.services.order.services.Models.DTO.Order.ReqOrder;

import java.util.List;

public interface OrderService {


    OrderResp getOrder(ReqOrder reqOrder);



    List<OrderResp> getAll();
}
