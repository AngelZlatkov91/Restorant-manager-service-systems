package order.services.order.services.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.services.order.services.Validation.UniquePersonalName;
import order.services.order.services.Validation.UniquePersonalPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedPersonal {
    @NotBlank
    @UniquePersonalName
    private String name;

    @NotBlank
    @UniquePersonalPassword
    private String password;
}
