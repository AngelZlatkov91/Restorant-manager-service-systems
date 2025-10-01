package order.services.order.services.Event.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import order.services.order.services.Event.Display.OrderProductsDTO;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class InventoryProductsDTO {
   private List<OrderProductsDTO> products;

   public InventoryProductsDTO() {
       this.products = new ArrayList<>();
   }
}
