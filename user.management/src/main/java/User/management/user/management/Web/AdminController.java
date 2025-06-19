package User.management.user.management.Web;

import User.management.user.management.Models.DTO.UserChangeRoleDTO;
import User.management.user.management.Models.DTO.UserDetailsDTO;
import User.management.user.management.Service.Interfaces.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/change/manager")
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getProfiles(Authentication authentication) {
         List<UserDetailsDTO> userDetailsDTOList = adminService.getAllUsers(authentication.getName());
         return ResponseEntity.ok(userDetailsDTOList);
    }

    @PostMapping
    public ResponseEntity<String> changeProfile(@RequestBody UserChangeRoleDTO userChangeRoleDTO) {
        adminService.changeProfileRole(userChangeRoleDTO);
        return ResponseEntity.ok("Profile changed");
    }



    @DeleteMapping("/id")
    public ResponseEntity<String> deleteProfile(@RequestBody Long id) {
        adminService.deleteProfile(id);
        return ResponseEntity.ok("Profile deleted");
    }
}
