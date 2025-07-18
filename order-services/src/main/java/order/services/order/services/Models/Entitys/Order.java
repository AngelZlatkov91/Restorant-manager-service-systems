package order.services.order.services.Models.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import order.services.order.services.Models.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;




@Entity
@Table(name = "orders")
@AllArgsConstructor
@Data
public class Order  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Personal personal;
    @ManyToOne
    private TableEn table_name;
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
