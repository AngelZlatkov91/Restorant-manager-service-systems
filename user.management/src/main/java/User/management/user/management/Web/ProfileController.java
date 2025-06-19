package User.management.user.management.Web;
import User.management.user.management.Models.DTO.UserDetailsDTO;
import User.management.user.management.Service.Interfaces.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;

    }


    @GetMapping
    public ResponseEntity<UserDetailsDTO> getProfile(Authentication authentication) {

        UserDetailsDTO userDetails = profileService.getUserDetails(authentication.getName());

        return ResponseEntity.ok(userDetails);

    }

    @PutMapping
    public ResponseEntity<String> updateProfile () {

        return ResponseEntity.ok("ok");
    }
}
