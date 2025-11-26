package menu_service.menu_service.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import menu_service.menu_service.Validation.UniqueCategoryItem;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateCategory {
//    @UniqueCategoryItem
    @NotBlank
    @Size(min = 3, max = 50)
    private String categoryName;
}
