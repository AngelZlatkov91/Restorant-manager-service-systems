package order.services.order.services.Models.DTO.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseTable {
    private Long id;
    private String tableName;
    private boolean isEmpty;
}
