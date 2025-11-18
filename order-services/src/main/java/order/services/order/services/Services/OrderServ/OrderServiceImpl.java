package order.services.order.services.Services.OrderServ;

import order.services.order.services.Models.DTO.Order.AddProductToTableDTO;
import order.services.order.services.Models.DTO.Order.CheckOrders;
import order.services.order.services.Models.DTO.Order.OrderResp;
import order.services.order.services.Models.DTO.Order.ReqOrder;
import order.services.order.services.Models.Entitys.Order;
import order.services.order.services.Models.Entitys.Personal;
import order.services.order.services.Models.Entitys.Product;
import order.services.order.services.Models.Entitys.TableEn;
import order.services.order.services.Models.OrderStatus;
import order.services.order.services.Repositories.OrderRepositories;
import order.services.order.services.Repositories.PersonalRepositories;
import order.services.order.services.Repositories.TableRepositories;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepositories orderRepositories;
    private final PersonalRepositories personalRepositories;
    private final TableRepositories tableRepositories;
    private Double totalAmount;

    public OrderServiceImpl(OrderRepositories orderRepositories, PersonalRepositories personalRepositories, TableRepositories tableRepositories) {
        this.orderRepositories = orderRepositories;
        this.personalRepositories = personalRepositories;
        this.tableRepositories = tableRepositories;
        this.totalAmount = 0.0;
    }


    @Override
    public OrderResp getOrder(ReqOrder reqOrder) {
        Optional<TableEn> byId = tableRepositories.findById(reqOrder.getId());
        Optional<Personal> byName = personalRepositories.findByName(reqOrder.getName());
        if (byId.isEmpty() || byName.isEmpty()) {
            throw  new NullPointerException("Table or Personal don't exist");
        }
        Optional<Order> order = orderRepositories.getByPersonalAndOrderStatusAndTableEn(byName.get(),OrderStatus.PENDING,byId.get());
       if (order.isEmpty()) {
           return new OrderResp(byId.get().getTableName(),byName.get().getName());
       }
        return mapToResponse(order.get());

    }

    @Override
    public List<CheckOrders> checkOrders(CheckOrders checkOrders) {
        Optional<Personal> byName = personalRepositories.findByName(checkOrders.getName());
      return  orderRepositories
              .findAllByPersonalAndOrderStatus(byName.get(), OrderStatus.PENDING)
              .stream()
              .map(this::mapCheckOrders)
              .toList();

    }


    private CheckOrders mapCheckOrders(Order order) {
        CheckOrders checkOrders = new CheckOrders();
        checkOrders.setOrderId(order.getId());
        checkOrders.setName(order.getPersonal().getName());
        checkOrders.setTableId(order.getTableEn().getId());
        return checkOrders;
    }


    private OrderResp mapToResponse(Order order) {
        OrderResp orderResp = new OrderResp();
        orderResp.setId(order.getId());
        orderResp.setTableName(order.getTableEn().getTableName());
        orderResp.setStatus(order.getOrderStatus());
        orderResp.setPersonalName(order.getPersonal().getName());
        orderResp.setCreatedAd(order.getCreated_at());
        orderResp.setProducts(mapToResponseProduct(order.getProducts()));
        orderResp.setTotalPrice(totalAmount);
        totalAmount = 0.0;
        return orderResp;
    }

    private List<AddProductToTableDTO> mapToResponseProduct(List<Product> products) {
        List<AddProductToTableDTO> addProducts = new ArrayList<>();
        for (Product product : products) {
            AddProductToTableDTO addProductToTableDTO = new AddProductToTableDTO();
            addProductToTableDTO.setName(product.getName());
            addProductToTableDTO.setPrice(product.getPrice());
            addProductToTableDTO.setQuantity(product.getQuantity());
            addProductToTableDTO.setAddedAt(product.getAddedAt());
            if (product.getDescription() != null) {
                addProductToTableDTO.setDescription(product.getDescription());
            }
            totalAmount = totalAmount + (product.getQuantity() * product.getPrice());
            addProductToTableDTO.setCheck(product.isCheck());
            addProducts.add(addProductToTableDTO);
        }
        return addProducts;
    }

}
