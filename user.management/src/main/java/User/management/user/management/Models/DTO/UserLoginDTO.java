package User.management.user.management.Models.DTO;

public class UserLoginDTO {

    private String username;
    private String password;
    public UserLoginDTO() {}

    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
