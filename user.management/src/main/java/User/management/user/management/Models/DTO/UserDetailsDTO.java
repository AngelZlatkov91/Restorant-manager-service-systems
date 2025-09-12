package User.management.user.management.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetailsDTO {
    private Long id;
    private  String username;
    private String role;
}
