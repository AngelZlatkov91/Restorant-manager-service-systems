package order.services.order.services.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddProductToTableDTO {
    private String name;
    private String category;
    private Double price;
    private boolean isCheck;
    private int quantity;

}
