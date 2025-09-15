package order.services.order.services.Services.OrderServ;

import order.services.order.services.Models.DTO.Order.DeleteProduct;
import order.services.order.services.Repositories.OrderRepositories;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductFormOrderImpl implements DeleteProductFormOrder {

    private final OrderRepositories orderRepositories;

    public DeleteProductFormOrderImpl(OrderRepositories orderRepositories) {
        this.orderRepositories = orderRepositories;
    }

    @Override
    public void deleteProduct(DeleteProduct deleteProduct) {
        orderRepositories.findById(deleteProduct.getOrderId()).ifPresent(order -> {
            order.getProducts().remove(deleteProduct.getIndexProduct());
        });
    }
}
