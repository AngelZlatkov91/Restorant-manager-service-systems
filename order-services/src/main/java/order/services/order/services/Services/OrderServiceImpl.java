package order.services.order.services.Services;

import jakarta.transaction.Transactional;
import order.services.order.services.Event.KitchenDTO;
import order.services.order.services.Event.KitchenEvent;
import order.services.order.services.Models.DTO.*;
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
import java.util.concurrent.ExecutionException;

@Service
public class OrderServiceImpl implements OrderService {

    private final TableRepositories tableRepositories;
    private final ProductRepositories productRepositories;
    private final OrderRepositories orderRepositories;
    private final PersonalRepositories personalRepositories;
    private final KitchenEvent kitchenEvent;



    public OrderServiceImpl(TableRepositories tableRepositories, ProductRepositories productRepositories, OrderRepositories orderRepositories, PersonalRepositories personalRepositories, KitchenEvent kitchenEvent) {
        this.tableRepositories = tableRepositories;
        this.productRepositories = productRepositories;
        this.orderRepositories = orderRepositories;
        this.personalRepositories = personalRepositories;

        this.kitchenEvent = kitchenEvent;
    }

    @Override
    @Transactional
    public void createOrder(OrderDTO order) throws ExecutionException, InterruptedException {
        Optional<Personal> personal = personalRepositories.findByName(order.getPersonal_name());
        Optional<TableEn> byTableName = tableRepositories.findByTableName(order.getTable_name());
        if (personal.isEmpty() || byTableName.isEmpty()) {
            throw new NullPointerException("Personal or Table not found");
        }
        byTableName.get().setEmpty(false);
        tableRepositories.save(byTableName.get());

        Order orderEntity = mapToCreate(order, byTableName.get(), personal.get());

        KitchenDTO kitchenDTO = sendToKitchen(orderEntity.getProducts());
        kitchenDTO.setPersonal(personal.get().getName());
        kitchenDTO.setTableName(byTableName.get().getTableName());
        kitchenEvent.sendEvent(kitchenDTO);

        orderRepositories.save(orderEntity);
    }


    private KitchenDTO sendToKitchen(List<Product> products) {
        KitchenDTO kitchenDTO = new KitchenDTO();
        for (Product product : products) {
            if (product.getCategory().equals("KITCHEN")) {
                AddProductToTableDTO addProductToTableDTO = new AddProductToTableDTO();
                addProductToTableDTO.setName(product.getName());
                addProductToTableDTO.setPrice(product.getPrice());
                addProductToTableDTO.setQuantity(product.getQuantity());
                addProductToTableDTO.setCategory(product.getCategory());
                addProductToTableDTO.setCheck(true);
                kitchenDTO.getProducts().add(addProductToTableDTO);
            }
        }
        return kitchenDTO;
    }

    @Override
    @Transactional
    public void updateOrder(OrderResp order) throws ExecutionException, InterruptedException {
        Optional<Order> byId = orderRepositories.findById(order.getId());
        if (byId.isEmpty()) {
           throw new NullPointerException("Order not found");
        }
        List<Product> products = mapProducts(order.getProducts());
        byId.get().getProducts().addAll(products);
        KitchenDTO kitchenDTO = sendToKitchen(products);
        kitchenDTO.setPersonal(byId.get().getPersonal().getName());
        kitchenDTO.setTableName(byId.get().getTable_name().getTableName());
        kitchenEvent.sendEvent(kitchenDTO);
        orderRepositories.save(byId.get());

    }

    @Override
    public OrderResp getOrder(Long orderId) {
        return orderRepositories.findById(orderId).map(this::mapToResponse).orElse(null);
    }

    @Override
    public List<CheckOrders> checkOrders(CheckOrders checkOrders) {
        Optional<Personal> byName = personalRepositories.findByName(checkOrders.getName());
      return  orderRepositories
              .findAllByPersonalAndOrderStatus(byName.get(), checkOrders.getStatus())
              .stream()
              .map(this::mapCheckOrders)
              .toList();

    }

    private CheckOrders mapCheckOrders(Order order) {
        CheckOrders checkOrders = new CheckOrders();
        checkOrders.setId(order.getId());
        checkOrders.setName(order.getPersonal().getName());
        checkOrders.setStatus(order.getOrderStatus());
        return checkOrders;
    }


    private OrderResp mapToResponse(Order order) {
        OrderResp orderResp = new OrderResp();
        orderResp.setId(order.getId());
        orderResp.setTable_name(order.getTable_name().getTableName());
        orderResp.setStatus(order.getOrderStatus());
        orderResp.setPersonal_name(order.getPersonal().getName());
        orderResp.setProducts(mapToResponse(order.getProducts()));
        return orderResp;
    }

    private List<AddProductToTableDTO> mapToResponse(List<Product> products) {
        List<AddProductToTableDTO> addProducts = new ArrayList<>();
        for (Product product : products) {
            AddProductToTableDTO addProductToTableDTO = new AddProductToTableDTO();
            addProductToTableDTO.setName(product.getName());
            addProductToTableDTO.setPrice(product.getPrice());
            addProductToTableDTO.setQuantity(product.getQuantity());
            addProductToTableDTO.setCheck(product.isCheck());
            addProducts.add(addProductToTableDTO);
        }
        return addProducts;
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
                productRepositories.save(productEntity);
                productEntities.add(productEntity);
            }

        }
         return productEntities;
    }
}
