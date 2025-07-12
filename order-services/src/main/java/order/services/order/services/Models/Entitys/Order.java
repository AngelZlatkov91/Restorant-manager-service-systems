package order.services.order.services.Models.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import order.services.order.services.Models.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@jakarta.persistence.Table
public class Order extends BaseEntity {
    @ManyToOne
    private Personal personal;
    @ManyToOne
    private Table table_name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;
    @Column(nullable = false)
    private boolean isActive;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private LocalDateTime created_at;

    public Order(){
        products = new ArrayList<>();
    }

}
