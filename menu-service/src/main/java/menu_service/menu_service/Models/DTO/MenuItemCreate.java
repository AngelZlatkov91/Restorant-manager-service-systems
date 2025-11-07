package menu_service.menu_service.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import menu_service.menu_service.Models.TypeProduct;
import menu_service.menu_service.Validation.UniqueMenuName;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuItemCreate {
     @UniqueMenuName
     @NotBlank
    private String name;
     @Positive
     @NotNull(message = "The price must be positive!")
    private Double price;
     @NotBlank
    private String category;

    private TypeProduct typeProduct;

}
