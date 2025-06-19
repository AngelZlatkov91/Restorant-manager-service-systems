package menu_service.menu_service.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuItemRes {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private boolean active;
}
