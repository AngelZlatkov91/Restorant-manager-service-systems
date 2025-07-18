package order.services.order.services.Models.Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TableEn extends BaseEntity{
   @Column(nullable = false, unique = true)
    private String tableName;
    private boolean isEmpty;


}
