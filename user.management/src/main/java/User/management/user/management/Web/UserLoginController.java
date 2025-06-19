package User.management.user.management.Web;

import User.management.user.management.Models.DTO.LoginResponseDTO;
import User.management.user.management.Models.DTO.UserLoginDTO;
import User.management.user.management.Service.Interfaces.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserLoginController {


    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UserLoginController( TokenService tokenService, AuthenticationManager authenticationManager) {

        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginDTO loginUserDTO) {

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()));
            String token = tokenService.generateToken(authenticate);
            return ResponseEntity.ok
                    (new LoginResponseDTO("Login successful",  token));


    }
}
