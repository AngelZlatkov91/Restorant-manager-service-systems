package menu_service.menu_service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "mune_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Category category;
    private boolean active;
}
