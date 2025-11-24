package User.management.user.management.Models.DTO;

import User.management.user.management.Models.Enum.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserChangeRoleDTO {
    @NotBlank
    private String username;

    private Role role;

}
