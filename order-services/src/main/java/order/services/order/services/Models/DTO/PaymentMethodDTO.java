package order.services.order.services.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentMethodDTO {
    private Long id;
    private String paymentMethod;
    private String personal;
}
