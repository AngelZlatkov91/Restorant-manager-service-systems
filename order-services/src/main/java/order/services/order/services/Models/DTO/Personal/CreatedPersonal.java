package order.services.order.services.Models.DTO.Personal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.services.order.services.Models.Role;
import order.services.order.services.Validation.UniquePersonalPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedPersonal {
    @NotBlank
    @Size(min = 3)
    private String name;

    @NotBlank
    @Size(min = 4, max = 6)
    @UniquePersonalPassword
    private String password;

    private Role role;
}
