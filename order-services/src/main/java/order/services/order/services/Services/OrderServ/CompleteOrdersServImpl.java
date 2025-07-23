package order.services.order.services.Services.OrderServ;

import jakarta.transaction.Transactional;
import order.services.order.services.Event.DailyReportsDTO;
import order.services.order.services.Event.DailyReportsEvent;
import order.services.order.services.Event.InventoryDTO;
import order.services.order.services.Event.InventoryEvent;
import order.services.order.services.Models.DTO.AddProductToTableDTO;
import order.services.order.services.Models.DTO.CompleteOrderDTO;
import order.services.order.services.Models.DTO.PaymentMethodDTO;
import order.services.order.services.Models.Entitys.Order;
import order.services.order.services.Models.Entitys.Personal;
import order.services.order.services.Models.Entitys.Product;
import order.services.order.services.Models.OrderStatus;
import order.services.order.services.Repositories.OrderRepositories;
import order.services.order.services.Repositories.PersonalRepositories;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CompleteOrdersServImpl implements CompleteOrdersServ {
    private final OrderRepositories orderRepositories;
    private final PersonalRepositories personalRepositories;
    private final InventoryEvent inventoryEvent;
    private final DailyReportsEvent dailyReportsEvent;
    private Double totalPrice;


    public CompleteOrdersServImpl(OrderRepositories orderRepositories, PersonalRepositories personalRepositories, InventoryEvent inventoryEvent, DailyReportsEvent dailyReportsEvent) {
        this.orderRepositories = orderRepositories;
        this.personalRepositories = personalRepositories;
        this.inventoryEvent = inventoryEvent;
        this.dailyReportsEvent = dailyReportsEvent;
        totalPrice = 0.0;
    }

    @Override
    @Transactional
    public CompleteOrderDTO completeOrder(PaymentMethodDTO paymentMethodDTO) {
        Optional<Order> byId = orderRepositories.findById(paymentMethodDTO.getId());
        Optional<Personal> byName = personalRepositories.findByName(paymentMethodDTO.getPersonal());
        if (byName.isEmpty() || byId.isEmpty() || byId.get().getOrderStatus() == OrderStatus.COMPLETED) {
            throw new NullPointerException("Order or Personal not found");
        }

        CompleteOrderDTO completeOrderDTO = mapOrderToDTO(byId.get());
        completeOrderDTO.setTotalPrice(totalPrice);

        inventoryEvent.sendEvent(sendInventory(completeOrderDTO.getProducts()));

        DailyReportsDTO dailyReportsDTO = new DailyReportsDTO();
        dailyReportsDTO.setCompletedIn(LocalDateTime.now());
        dailyReportsDTO.setPersonalName(byName.get().getName());
        dailyReportsDTO.setPaymentMethod(paymentMethodDTO.getPaymentMethod());
        dailyReportsDTO.setTotalCost(totalPrice);
        dailyReportsEvent.sendEvent(dailyReportsDTO);

        byId.get().setOrderStatus(OrderStatus.COMPLETED);
        totalPrice = 0.0;
        orderRepositories.save(byId.get());

        return completeOrderDTO;
    }

    private InventoryDTO sendInventory(List<AddProductToTableDTO> products) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        List<AddProductToTableDTO> newProducts = new ArrayList<>();
        for (AddProductToTableDTO product : products) {
            if (product.getCategory().equals("BAR")){
                AddProductToTableDTO newProduct = new AddProductToTableDTO();
                newProduct.setName(product.getName());
                newProduct.setPrice(product.getPrice());
                newProduct.setQuantity(product.getQuantity());
                newProducts.add(newProduct);
            }

        }
        inventoryDTO.setProducts(newProducts);
        return inventoryDTO;
    }

    private CompleteOrderDTO mapOrderToDTO(Order order) {
        CompleteOrderDTO completeOrderDTO = new CompleteOrderDTO();
        completeOrderDTO.setPersonal(order.getPersonal().getName());
        completeOrderDTO.setTableName(order.getTable_name().getTableName());
        completeOrderDTO.setTotalPrice(totalPrice);
        completeOrderDTO.setProducts(mapProductToDTO(order.getProducts()));
        return completeOrderDTO;
    }



    private List<AddProductToTableDTO> mapProductToDTO(List<Product> products) {
        Map<String , AddProductToTableDTO> dtos = new HashMap<>();
        for (Product product : products) {
            AddProductToTableDTO dto = new AddProductToTableDTO();
            if (dtos.containsKey(product.getName())) {
                int quantity = dtos.get(product.getName()).getQuantity();
                dtos.get(product.getName()).setQuantity(quantity + product.getQuantity());
                totalPrice = totalPrice + ( product.getQuantity() * product.getPrice());
            } else {
                dto.setName(product.getName());
                int quantity = product.getQuantity();
                dto.setQuantity(quantity);
                Double price = product.getPrice();
                dto.setPrice(price);
                dto.setCategory(product.getCategory());
                dto.setCheck(true);
                totalPrice = totalPrice + (quantity * price);
                dtos.put(product.getName(), dto);
            }
        }

       return new ArrayList<>(dtos.values());
    }
}
