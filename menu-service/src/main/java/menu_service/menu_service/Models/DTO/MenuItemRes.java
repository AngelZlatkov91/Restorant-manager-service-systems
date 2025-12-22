package menu_service.menu_service.Models.DTO;

import menu_service.menu_service.Models.TypeProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemRes {
    private String id;
    private String name;
    private Double price;           
    private Double costPrice;       
    private Double markupPercentage; 
    private String category;        
    private TypeProduct typeProduct;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
