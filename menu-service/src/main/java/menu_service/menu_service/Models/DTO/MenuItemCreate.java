package menu_service.menu_service.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import menu_service.menu_service.Models.TypeProduct;
import menu_service.menu_service.Validation.UniqueMenuName;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemCreate {
    @UniqueMenuName
    private String name;
    private Double price;
    private Double costPrice;
    private String category;
    private TypeProduct typeProduct;
}

