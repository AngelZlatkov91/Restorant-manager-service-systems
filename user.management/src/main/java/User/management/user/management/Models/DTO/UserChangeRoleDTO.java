package User.management.user.management.Models.DTO;

import User.management.user.management.Models.Enum.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserChangeRoleDTO {
    @NotBlank
    private String username;
    @NotBlank
    private Role role;

}
