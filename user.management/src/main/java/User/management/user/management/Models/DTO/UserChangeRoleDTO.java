package User.management.user.management.Models.DTO;

import User.management.user.management.Models.Enum.Role;

public class UserChangeRoleDTO {
    private String username;
    private Role role;
    public UserChangeRoleDTO() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
