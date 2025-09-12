package User.management.user.management.Models.DTO;

import User.management.user.management.Models.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserChangeRoleDTO {
    private String username;
    private Role role;

}
