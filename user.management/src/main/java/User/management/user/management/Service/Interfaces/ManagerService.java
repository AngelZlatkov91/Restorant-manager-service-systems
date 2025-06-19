package User.management.user.management.Service.Interfaces;

import User.management.user.management.Models.DTO.UserChangeRoleDTO;
import User.management.user.management.Models.DTO.UserDetailsDTO;

import java.util.List;

public interface ManagerService {
    List<UserDetailsDTO> getAllUsers(String email);

    void changeProfileRole(UserChangeRoleDTO userChangeRoleDTO);

    void deleteProfile(Long id);
}
