package Restaurant.service.managment.Inventory.Service.Event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class InventoryProductsDTO {
    List<ProductsUpdateQuantity> products;

    public InventoryProductsDTO(){
        this.products = new ArrayList<>();
    }
}
