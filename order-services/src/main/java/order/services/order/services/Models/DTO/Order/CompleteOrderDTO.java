package order.services.order.services.Models.DTO.Order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class CompleteOrderDTO {
    private String personal;
    private String tableName;
    private List<AddProductToTableDTO> products;
    private Double totalPrice;

    public CompleteOrderDTO() {
        this.products = new ArrayList<>();

    }
}
