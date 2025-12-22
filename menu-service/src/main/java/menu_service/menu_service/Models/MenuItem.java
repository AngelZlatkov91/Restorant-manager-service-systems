package menu_service.menu_service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "menu_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    private String id;
    private String name;
    private Double price;
    private Double costPrice;
    private Double markupPercentage;
    private Category category;
    private TypeProduct typeProduct;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
