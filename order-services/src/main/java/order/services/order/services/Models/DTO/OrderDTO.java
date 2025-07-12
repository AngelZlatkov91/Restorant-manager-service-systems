package order.services.order.services.Models.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import order.services.order.services.Models.OrderStatus;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderDTO {

    private String table_name;
    private String personal_name;
    private OrderStatus status;
    private List<AddProductToTableDTO> products;

    public OrderDTO() {
        this.products = new ArrayList<>();
    }

}
