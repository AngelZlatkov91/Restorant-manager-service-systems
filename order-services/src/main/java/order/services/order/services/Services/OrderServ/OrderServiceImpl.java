package order.services.order.services.Services.OrderServ;

import order.services.order.services.Models.DTO.Order.AddProductToTableDTO;
import order.services.order.services.Models.DTO.Order.CheckOrders;
import order.services.order.services.Models.DTO.Order.OrderResp;
import order.services.order.services.Models.Entitys.Order;
import order.services.order.services.Models.Entitys.Personal;
import order.services.order.services.Models.Entitys.Product;
import order.services.order.services.Repositories.OrderRepositories;
import order.services.order.services.Repositories.PersonalRepositories;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepositories orderRepositories;
    private final PersonalRepositories personalRepositories;

    public OrderServiceImpl( OrderRepositories orderRepositories, PersonalRepositories personalRepositories) {
        this.orderRepositories = orderRepositories;
        this.personalRepositories = personalRepositories;
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
            if (!product.getDescription().isBlank()) {
                addProductToTableDTO.setDescription(product.getDescription());
            }
            addProductToTableDTO.setCheck(product.isCheck());
            addProducts.add(addProductToTableDTO);
        }
        return addProducts;
    }

}
