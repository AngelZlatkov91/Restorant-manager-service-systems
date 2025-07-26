package order.services.order.services.Event.Display;

import lombok.AllArgsConstructor;
import lombok.Data;
import order.services.order.services.Models.DTO.AddProductToTableDTO;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Data
public class ProductEventSentDTO {

    private String tableName;
    private String personal;
    private List<OrderProductsDTO> products;


    public ProductEventSentDTO(){
        this.products = new ArrayList<>();
    }
}
