package order.services.order.services.Models.Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@jakarta.persistence.Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Table extends BaseEntity{
   @Column(nullable = false, unique = true)
    private String tableName;
    private boolean isEmpty;


}
