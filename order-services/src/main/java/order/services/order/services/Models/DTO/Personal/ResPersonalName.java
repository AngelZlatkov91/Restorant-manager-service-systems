package order.services.order.services.Models.DTO.Personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.services.order.services.Models.Role;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResPersonalName {
    private String name;
    private Role role;
}
