package order.services.order.services.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.services.order.services.Validation.UniqueTableName;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTable {
    @NotBlank
    @UniqueTableName
    private String tableName;

}
