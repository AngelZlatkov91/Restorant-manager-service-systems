package order.services.order.services.Models.Entitys;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class Personal extends BaseEntity{
    @Column( nullable = false)
    private String name;
    @Column(unique=true, nullable = false)
    private String password;

}
