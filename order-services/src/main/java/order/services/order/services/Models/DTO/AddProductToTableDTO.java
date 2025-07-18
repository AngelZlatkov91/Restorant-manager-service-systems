package order.services.order.services.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddProductToTableDTO {
    private String name;
    private String category;
    private BigDecimal price;
    private boolean isCheck;
    private int quantity;



}
