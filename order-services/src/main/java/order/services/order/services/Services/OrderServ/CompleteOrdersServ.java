package order.services.order.services.Services.OrderServ;

import order.services.order.services.Models.DTO.Order.CompleteOrderDTO;
import order.services.order.services.Models.DTO.Order.PaymentMethodDTO;

public interface CompleteOrdersServ {

    String completeOrder(PaymentMethodDTO paymentMethodDTO);
}
