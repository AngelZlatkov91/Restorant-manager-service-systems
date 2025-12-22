package menu_service.menu_service.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import menu_service.menu_service.Models.TypeProduct;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemCreate {
    private String name;
    private Double price;
    private Double costPrice;
    private String category;
    private TypeProduct typeProduct;
}

