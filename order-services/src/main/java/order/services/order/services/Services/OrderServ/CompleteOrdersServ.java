package order.services.order.services.Services.OrderServ;

import order.services.order.services.Models.DTO.CompleteOrderDTO;
import order.services.order.services.Models.DTO.PaymentMethodDTO;

public interface CompleteOrdersServ {

    CompleteOrderDTO completeOrder(PaymentMethodDTO paymentMethodDTO);
}
