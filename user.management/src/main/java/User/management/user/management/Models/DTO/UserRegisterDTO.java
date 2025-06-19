package User.management.user.management.Models.DTO;

import User.management.user.management.Validation.PasswordMatch;
import User.management.user.management.Validation.UniqueUsername;
import jakarta.validation.constraints.*;

@PasswordMatch(first = "password",
second = "confirmPassword")
public class UserRegisterDTO {

    @NotBlank
    @UniqueUsername
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;


    public UserRegisterDTO() {
    }

    public UserRegisterDTO( String password,  String username, String confirmPassword) {

        this.password = password;

        this.username = username;
        this.confirmPassword = confirmPassword;
    }



    public String getPassword() {
        return password;
    }



    public String getUsername() {
        return username;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }



    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    public void setPassword(String password) {
        this.password = password;
    }



    public void setUsername(String username) {
        this.username = username;
    }
}
