package order.services.order.services.Models.Entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import order.services.order.services.Models.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor

public class Order {
    private String personal;
    private Table table_name;
    private List<Product> products;
    private boolean isActive;
    private OrderStatus orderStatus;
    private LocalDateTime created_at;

    public Order(){
        products = new ArrayList<>();
    }

}
