package User.management.user.management.Models.DTO;

import User.management.user.management.Validation.PasswordMatch;
import User.management.user.management.Validation.UniqueUsername;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@PasswordMatch(first = "password",
second = "confirmPassword")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegisterDTO {

    @NotBlank
    @UniqueUsername
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;

}
