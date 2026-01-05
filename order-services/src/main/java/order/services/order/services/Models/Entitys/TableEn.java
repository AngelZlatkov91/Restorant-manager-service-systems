package order.services.order.services.Models.Entitys;

import jakarta.persistence.*;
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
   @Column
    private boolean isEmpty;
   @ManyToOne
    private Personal isOwner;


}
