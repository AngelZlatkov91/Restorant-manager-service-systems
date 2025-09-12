package User.management.user.management.Web;

import User.management.user.management.Models.DTO.UserRegisterDTO;
import User.management.user.management.Service.Interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping( "/api/user")
public class UserRegisterController {

    private final UserService userService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegisterDTO userRegistrationDTO ,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            String defaultMessage = allErrors.getFirst().getDefaultMessage();
            return ResponseEntity.badRequest().body(defaultMessage);
        }
            userService.registerUser(userRegistrationDTO);
            return ResponseEntity.ok().build();
    }
}
