package order.services.order.services.Event.CashEvent;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class CashReceiptDTO {
    private Long orderId;
    private String paymentMethod;
    private Double amount;
    private List<ProductsToReceipt> products;

    public CashReceiptDTO() {
        this.products = new ArrayList<>();
    }

}
