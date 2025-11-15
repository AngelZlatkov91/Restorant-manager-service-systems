package order.services.order.services.Models.DTO.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import order.services.order.services.Models.OrderStatus;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderDTO {
    @Positive
    private Long id;
    @NotBlank
    private String personalName;
    private List<AddProductToTableDTO> products;

    public OrderDTO() {
        this.products = new ArrayList<>();
    }

}
