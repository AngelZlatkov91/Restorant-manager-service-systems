package order.services.order.services.Repositories;

import order.services.order.services.Models.Entitys.Order;
import order.services.order.services.Models.Entitys.Personal;
import order.services.order.services.Models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositories extends JpaRepository<Order, Long> {


    List<Order> findAllByPersonalAndOrderStatus(Personal personal, OrderStatus orderStatus);


}
