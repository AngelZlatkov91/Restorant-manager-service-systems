package order.services.order.services.Models.DTO.Personal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.services.order.services.Models.Role;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonalResponse {
    private Long id;
    private String name;
    private String password;
    private Role role;
    private boolean isActive;
}
