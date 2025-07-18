package menu_service.menu_service.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import menu_service.menu_service.Models.TypeProduct;
import menu_service.menu_service.Validation.UniqueMenuName;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuItemCreate {
     @UniqueMenuName
     @NotBlank
    private String name;
     @NotBlank
    private String description;
     @Positive
    private BigDecimal price;
     @NotBlank
    private String category;
     @NotBlank
    private TypeProduct typeProduct;

}
