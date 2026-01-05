package order.services.order.services.Models.Entitys;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import order.services.order.services.Models.Role;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private boolean isActive;

}
