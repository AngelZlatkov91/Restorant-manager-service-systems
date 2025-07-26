package order.services.order.services.Services.OrderServ;

import jakarta.transaction.Transactional;
import order.services.order.services.Event.Display.OrderProductsDTO;
import order.services.order.services.Event.Display.ProductEventSentDTO;
import order.services.order.services.Event.Display.DisplayEvent;
import order.services.order.services.Models.DTO.AddProductToTableDTO;
import order.services.order.services.Models.DTO.OrderDTO;
import order.services.order.services.Models.DTO.OrderResp;
import order.services.order.services.Models.Entitys.Order;
import order.services.order.services.Models.Entitys.Personal;
import order.services.order.services.Models.Entitys.Product;
import order.services.order.services.Models.Entitys.TableEn;
import order.services.order.services.Repositories.OrderRepositories;
import order.services.order.services.Repositories.PersonalRepositories;
import order.services.order.services.Repositories.ProductRepositories;
import order.services.order.services.Repositories.TableRepositories;
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
        Optional<Personal> personal = personalRepositories.findByName(order.getPersonal_name());
        Optional<TableEn> byTableName = tableRepositories.findByTableName(order.getTable_name());

        if (personal.isEmpty() || byTableName.isEmpty()) {
            throw new NullPointerException("Personal or Table not found");
        }

        byTableName.get().setEmpty(false);
        tableRepositories.save(byTableName.get());

        Order orderEntity = mapToCreate(order, byTableName.get(), personal.get());

        sendEvents(orderEntity.getProducts(),order.getPersonal_name(),order.getTable_name());

        orderRepositories.save(orderEntity);
    }

    private void sendEvents(List<Product> products, String personalName, String tableName) {
        ProductEventSentDTO kitchen = new ProductEventSentDTO();
        ProductEventSentDTO bar = new ProductEventSentDTO();

        products.forEach(product -> {
            OrderProductsDTO orderProductsDTO = new OrderProductsDTO();
            orderProductsDTO.setProductDescription("");
            orderProductsDTO.setProductName(product.getName());
            orderProductsDTO.setQuantity(product.getQuantity());
            if (!product.getDescription().isBlank()) {
                orderProductsDTO.setProductDescription(product.getDescription());
            }
            if (product.getCategory().equals("BAR")) {
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
        }
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
        
        sendEvents(products,order.getPersonal_name(),order.getTable_name());

        orderRepositories.save(byId.get());
    }

    private Order mapToCreate(OrderDTO order, TableEn tableEn, Personal personal) {
        Order orderEntity = new Order();
        orderEntity.setPersonal(personal);
        orderEntity.setOrderStatus(order.getStatus());
        orderEntity.setCreated_at(LocalDateTime.now());
        orderEntity.setActive(true);
        orderEntity.setTable_name(tableEn);
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
                productEntity.setQuantity(product.getQuantity());
                productEntity.setName(product.getName());
                productEntity.setCategory(product.getCategory());
                if (!product.getDescription().isBlank()) {
                    productEntity.setDescription(product.getDescription());
                }
                productRepositories.save(productEntity);
                productEntities.add(productEntity);
            }
        }
        return productEntities;
    }
}
