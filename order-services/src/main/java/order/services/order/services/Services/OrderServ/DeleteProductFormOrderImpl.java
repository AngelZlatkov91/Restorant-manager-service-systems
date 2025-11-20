package order.services.order.services.Services.OrderServ;

import jakarta.transaction.Transactional;
import order.services.order.services.Models.DTO.Order.DeleteProduct;
import order.services.order.services.Models.Entitys.Order;
import order.services.order.services.Models.Entitys.Personal;
import order.services.order.services.Repositories.OrderRepositories;
import order.services.order.services.Repositories.PersonalRepositories;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteProductFormOrderImpl implements DeleteProductFormOrder {

    private final OrderRepositories orderRepositories;
    private final PersonalRepositories personalRepositories;

    public DeleteProductFormOrderImpl(OrderRepositories orderRepositories, PersonalRepositories personalRepositories) {
        this.orderRepositories = orderRepositories;
        this.personalRepositories = personalRepositories;
    }

    @Override
    @Transactional
    public String deleteProduct(DeleteProduct deleteProduct) {
        Optional<Personal> byPassword = personalRepositories.findByPassword(deleteProduct.getPassword());
        if (byPassword.isEmpty() || !deleteProduct.getPassword().contains("0000")) {
            throw new NullPointerException("is not Admin");
        }
        Optional<Order> byId = orderRepositories.findById(deleteProduct.getOrderId());
        byId.get().getProducts().remove(deleteProduct.getIndexProduct());
        orderRepositories.save(byId.get());

        return  "is Deleted";
    }
}
