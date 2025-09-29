package menu_service.menu_service.Event.Consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckItemEvent {
    private String itemName;
    private boolean status;
}
