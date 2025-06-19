package User.management.user.management.Web;

import User.management.user.management.Models.DTO.UserChangeRoleDTO;
import User.management.user.management.Models.DTO.UserDetailsDTO;
import User.management.user.management.Service.Interfaces.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/change/personal")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getProfiles(Authentication authentication) {
        List<UserDetailsDTO> userDetailsDTOList = managerService.getAllUsers(authentication.getName());
        return ResponseEntity.ok(userDetailsDTOList);
    }

    @PostMapping
    public ResponseEntity<String> changeProfile(@RequestBody UserChangeRoleDTO userChangeRoleDTO) {
        managerService.changeProfileRole(userChangeRoleDTO);
        return ResponseEntity.ok("Profile changed");
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteProfile(@RequestBody Long id) {
        managerService.deleteProfile(id);
        return ResponseEntity.ok("Profile deleted");
    }
}
