package order.services.order.services.Models.DTO.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddProductToTableDTO {
    private String name;
    private String category;
    private Double price;
    private boolean isCheck;
    private LocalDateTime addedAt;
    private int quantity;
    private String description;

}
