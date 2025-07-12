package order.services.order.services.Api;
import order.services.order.services.Models.DTO.CheckOrders;
import order.services.order.services.Models.DTO.OrderDTO;
import order.services.order.services.Models.DTO.OrderResp;
import order.services.order.services.Services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService ;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDto) {
        orderService.createOrder(orderDto);
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<OrderResp> getOrder(@RequestParam Long orderId) {
     return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateOrder(@RequestBody OrderResp orderDto) {
        orderService.updateOrder(orderDto);
        return new ResponseEntity<>("Order updated", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CheckOrders>> getAllOrders(@RequestBody CheckOrders checkOrders) {
        return ResponseEntity.ok(orderService.checkOrders(checkOrders));
    }




}
