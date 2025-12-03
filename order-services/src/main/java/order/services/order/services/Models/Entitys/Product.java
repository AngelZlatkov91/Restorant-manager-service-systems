package order.services.order.services.Models.Entitys;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column
    private String idItem;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private boolean isCheck;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private String typeProduct;
    @Column
    private LocalDateTime addedAt;
    @Column
    private String description;

}
