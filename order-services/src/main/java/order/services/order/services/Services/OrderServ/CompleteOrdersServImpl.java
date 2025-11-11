package order.services.order.services.Services.OrderServ;
import jakarta.transaction.Transactional;
import order.services.order.services.Event.CashEvent.CashReceiptDTO;
import order.services.order.services.Event.CashEvent.CashReceiptEvent;
import order.services.order.services.Event.CashEvent.ProductsToReceipt;
import order.services.order.services.Event.Display.OrderProductsDTO;
import order.services.order.services.Event.Reports.DailyReportsDTO;
import order.services.order.services.Event.Reports.DailyReportsEvent;
import order.services.order.services.Event.Inventory.InventoryProductsDTO;
import order.services.order.services.Event.Inventory.InventoryEvent;
import order.services.order.services.Models.DTO.Order.AddProductToTableDTO;
import order.services.order.services.Models.DTO.Order.CompleteOrderDTO;
import order.services.order.services.Models.DTO.Order.PaymentMethodDTO;
import order.services.order.services.Models.Entitys.Order;
import order.services.order.services.Models.Entitys.Personal;
import order.services.order.services.Models.Entitys.Product;
import order.services.order.services.Models.Entitys.TableEn;
import order.services.order.services.Models.OrderStatus;
import order.services.order.services.Repositories.OrderRepositories;
import order.services.order.services.Repositories.PersonalRepositories;
import order.services.order.services.Repositories.TableRepositories;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CompleteOrdersServImpl implements CompleteOrdersServ {
    private final OrderRepositories orderRepositories;
    private final PersonalRepositories personalRepositories;
    private final TableRepositories tableRepositories;
    private final InventoryEvent inventoryEvent;
    private final DailyReportsEvent dailyReportsEvent;
    private final CashReceiptEvent cashReceiptEvent;
    private Double totalPrice;


    public CompleteOrdersServImpl(OrderRepositories orderRepositories, PersonalRepositories personalRepositories, TableRepositories tableRepositories, InventoryEvent inventoryEvent, DailyReportsEvent dailyReportsEvent, CashReceiptEvent cashReceiptEvent) {
        this.orderRepositories = orderRepositories;
        this.personalRepositories = personalRepositories;
        this.tableRepositories = tableRepositories;
        this.inventoryEvent = inventoryEvent;
        this.dailyReportsEvent = dailyReportsEvent;
        this.cashReceiptEvent = cashReceiptEvent;
        totalPrice = 0.0;
    }

    @Override
    @Transactional
    public void completeOrder(PaymentMethodDTO paymentMethodDTO) {
        Optional<Order> byId = orderRepositories.findById(paymentMethodDTO.getId());
        Optional<Personal> byName = personalRepositories.findByName(paymentMethodDTO.getPersonal());

        if (byName.isEmpty() || byId.isEmpty() || byId.get().getOrderStatus() == OrderStatus.COMPLETED) {
            throw new NullPointerException("Order or Personal not found");
        }
        TableEn tableName = byId.get().getTableEn();
        tableName.setEmpty(true);
        tableRepositories.save(tableName);

        CompleteOrderDTO completeOrderDTO = mapOrderToDTO(byId.get());


        if (paymentMethodDTO.getPaymentMethod().equals("CASH")) {
            cashReceiptEvent.printReceipt(printTheReceipt(byId.get().getId(),completeOrderDTO));
        }
        cashReceiptEvent.printTable(printTheReceipt(byId.get().getId(),completeOrderDTO));

        inventoryEvent.sendEvent(sendInventory(completeOrderDTO.getProducts()));


        dailyReportsEvent.sendEvent(sendReports(byId.get().getId(),byName.get().getName(),paymentMethodDTO.getPaymentMethod(),totalPrice));



        byId.get().setOrderStatus(OrderStatus.COMPLETED);
        byId.get().setTotalPrice(totalPrice);
        byId.get().setActive(false);
        totalPrice = 0.0;
        orderRepositories.save(byId.get());

    }

    private CashReceiptDTO printTheReceipt(Long id, CompleteOrderDTO completeOrderDTO) {
        CashReceiptDTO cashReceiptDTO = new CashReceiptDTO();
        cashReceiptDTO.setOrderId(id);
        cashReceiptDTO.setPaymentMethod("CASH");
        cashReceiptDTO.setPersonal(completeOrderDTO.getPersonal());
        cashReceiptDTO.setTableName(completeOrderDTO.getTableName());
        cashReceiptDTO.setAmount(completeOrderDTO.getTotalPrice());

        completeOrderDTO.getProducts().forEach(product -> {
            ProductsToReceipt products = new ProductsToReceipt(product.getName(), product.getQuantity(), product.getPrice());
            cashReceiptDTO.getProducts().add(products);
        });

        return cashReceiptDTO;
    }


    private DailyReportsDTO sendReports(Long id,String name, String paymentMethod, Double totalPrice) {
        DailyReportsDTO dailyReportsDTO = new DailyReportsDTO();
        dailyReportsDTO.setOrderId(id);
        dailyReportsDTO.setCompletedIn(LocalDateTime.now());
        dailyReportsDTO.setPersonalName(name);
        dailyReportsDTO.setPaymentMethod(paymentMethod);
        dailyReportsDTO.setTotalCost(totalPrice);
        return dailyReportsDTO;
    }

    private InventoryProductsDTO sendInventory(List<AddProductToTableDTO> products) {
        InventoryProductsDTO inventoryDTO = new InventoryProductsDTO();
        List<OrderProductsDTO> newProducts = new ArrayList<>();

        products.stream().filter(p-> p.getCategory().equals("BAR")).forEach(p->{
            OrderProductsDTO newProduct = new OrderProductsDTO();
            newProduct.setProductName(p.getName());
            newProduct.setDescription(p.getDescription());
            newProduct.setQuantity(p.getQuantity());
            newProducts.add(newProduct);
        });
        inventoryDTO.setProducts(newProducts);
        return inventoryDTO;
    }

    private CompleteOrderDTO mapOrderToDTO(Order order) {
        CompleteOrderDTO completeOrderDTO = new CompleteOrderDTO();
        completeOrderDTO.setPersonal(order.getPersonal().getName());
        completeOrderDTO.setTableName(order.getTableEn().getTableName());
        completeOrderDTO.setProducts(mapProductToDTO(order.getProducts()));
        completeOrderDTO.setTotalPrice(totalPrice);

        return completeOrderDTO;
    }



    private List<AddProductToTableDTO> mapProductToDTO(List<Product> products) {
        Map<String, AddProductToTableDTO> dtos = new HashMap<>();

        for (Product product : products) {
            String name = product.getName();
            int quantity = product.getQuantity();
            double price = product.getPrice();

            if (dtos.containsKey(name)) {
                AddProductToTableDTO existingDto = dtos.get(name);
                existingDto.setQuantity(existingDto.getQuantity() + quantity);
            } else {
                AddProductToTableDTO dto = new AddProductToTableDTO();
                dto.setName(name);
                dto.setQuantity(quantity);
                dto.setPrice(price);
                dto.setCategory(product.getCategory());
                dto.setCheck(true);
                dtos.put(name, dto);
            }

            totalPrice += quantity * price;
        }

       return new ArrayList<>(dtos.values());
    }
}
