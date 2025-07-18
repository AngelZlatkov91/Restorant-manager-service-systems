package order.services.order.services.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import order.services.order.services.Models.DTO.AddProductToTableDTO;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Data
public class KitchenDTO {

    private String tableName;
    private String personal;
    private List<AddProductToTableDTO> products;


    public KitchenDTO(){
        this.products = new ArrayList<>();
    }
}
