package menu_service.menu_service.Models.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import menu_service.menu_service.Models.TypeProduct;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuItemRes {
    private String id;
    private String name;
    private Double price;
    private String category;
    private TypeProduct typeProduct;
    private boolean active;
}
