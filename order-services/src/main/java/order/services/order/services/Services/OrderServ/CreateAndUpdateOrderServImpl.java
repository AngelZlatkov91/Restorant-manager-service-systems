package order.services.order.services.Services.OrderServ;
import jakarta.transaction.Transactional;


import order.services.order.services.Event.Display.DisplayEvent;
import order.services.order.services.Models.DTO.Order.AddProductToTableDTO;
import order.services.order.services.Models.DTO.Order.OrderDTO;
import order.services.order.services.Models.DTO.Order.OrderResp;
import order.services.order.services.Models.Entitys.Order;
import order.services.order.services.Models.Entitys.Personal;
import order.services.order.services.Models.Entitys.Product;
import order.services.order.services.Models.Entitys.TableEn;
import order.services.order.services.Models.OrderStatus;
import order.services.order.services.Repositories.OrderRepositories;
import order.services.order.services.Repositories.PersonalRepositories;
import order.services.order.services.Repositories.ProductRepositories;
import order.services.order.services.Repositories.TableRepositories;
import printOrder.ProductEventSentDTO;
import printOrder.OrderProductsDTO;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CreateAndUpdateOrderServImpl implements CreateAndUpdateOrderServ {

    private final TableRepositories tableRepositories;
    private final ProductRepositories productRepositories;
    private final OrderRepositories orderRepositories;
    private final PersonalRepositories personalRepositories;
    private final DisplayEvent displayEvent;


    public CreateAndUpdateOrderServImpl(TableRepositories tableRepositories, ProductRepositories productRepositories, OrderRepositories orderRepositories, PersonalRepositories personalRepositories, DisplayEvent displayEvent) {
        this.tableRepositories = tableRepositories;
        this.productRepositories = productRepositories;
        this.orderRepositories = orderRepositories;
        this.personalRepositories = personalRepositories;
        this.displayEvent = displayEvent;

    }


    @Override
    public void createOrder(OrderDTO order) {
        Optional<Personal> personal = personalRepositories.findByName(order.getPersonalName());
        Optional<TableEn> byTableName = tableRepositories.findById(order.getId());

        if (personal.isEmpty() || byTableName.isEmpty()) {
            throw new NullPointerException("Personal or Table not found");
        }

        Order orderEntity = mapToCreate(order, byTableName.get(), personal.get());
        orderRepositories.save(orderEntity);
        byTableName.get().setEmpty(false);
        tableRepositories.save(byTableName.get());
        sendEvents(orderEntity.getProducts(),order.getPersonalName(),byTableName.get().getTableName());
    }

    private void sendEvents(List<Product> products, String personalName, String tableName) {
        ProductEventSentDTO kitchen = new ProductEventSentDTO();
        ProductEventSentDTO bar = new ProductEventSentDTO();

        products.forEach(product -> {
            OrderProductsDTO orderProductsDTO = new OrderProductsDTO();
            orderProductsDTO.setProductName(product.getName());
            orderProductsDTO.setQuantity(product.getQuantity());
            if (product.getDescription() != null) {
                orderProductsDTO.setDescription(product.getDescription());
            }
            if (product.getTypeProduct().equals("BAR")) {
                bar.getProducts().add(orderProductsDTO);
            } else {
                kitchen.getProducts().add(orderProductsDTO);
            }
        });

        if (!bar.getProducts().isEmpty()) {
            bar.setPersonal(personalName);
            bar.setTableName(tableName);
            displayEvent.barSendEvent(bar);
        }

        if (!kitchen.getProducts().isEmpty()) {
            kitchen.setPersonal(personalName);
            kitchen.setTableName(tableName);
            displayEvent.kitchenSendEvent(kitchen);
            bar.getProducts().addAll(kitchen.getProducts());
        }
        displayEvent.checkDisplay(bar);
    }


    @Override
    @Transactional
    public void updateOrder(OrderResp order) {
        Optional<Order> byId = orderRepositories.findById(order.getId());
        if (byId.isEmpty()) {
            throw new NullPointerException("Order not found");
        }
        List<Product> products = mapProducts(order.getProducts());
        byId.get().getProducts().addAll(products);
        
        sendEvents(products,order.getPersonalName(),order.getTableName());

        orderRepositories.save(byId.get());
    }

    private Order mapToCreate(OrderDTO order, TableEn tableEn, Personal personal) {
        Order orderEntity = new Order();
        orderEntity.setPersonal(personal);
        orderEntity.setOrderStatus(OrderStatus.PENDING);
        orderEntity.setCreated_at(LocalDateTime.now());
        orderEntity.setActive(true);
        orderEntity.setTableEn(tableEn);
        orderEntity.setProducts(mapProducts(order.getProducts()));
        return orderEntity;
    }

    private List<Product> mapProducts(List<AddProductToTableDTO> products) {
        List<Product> productEntities = new ArrayList<>();
        for (AddProductToTableDTO product : products) {
            if (!product.isCheck()) {
                Product productEntity = new Product();
                productEntity.setCheck(true);
                productEntity.setPrice(product.getPrice());
                productEntity.setIdItem(product.getId());
                productEntity.setQuantity(product.getQuantity());
                productEntity.setName(product.getName());
                productEntity.setCategory(product.getCategory());
                productEntity.setTypeProduct(product.getTypeProduct());
                if (product.getDescription() != null) {
                    productEntity.setDescription(product.getDescription());
                } else {
                    productEntity.setDescription("");
                }
                productEntity.setAddedAt(LocalDateTime.now());
                productRepositories.save(productEntity);
                productEntities.add(productEntity);
            }
        }
        return productEntities;
    }
}
