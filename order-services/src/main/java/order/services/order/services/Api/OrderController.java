package order.services.order.services.Api;
import order.services.order.services.Models.DTO.CheckOrders;
import order.services.order.services.Models.DTO.OrderDTO;
import order.services.order.services.Models.DTO.OrderResp;
import order.services.order.services.Services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService ;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDto) throws ExecutionException, InterruptedException {
        orderService.createOrder(orderDto);
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResp> getOrder(@PathVariable Long id) {
     return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateOrder(@RequestBody OrderResp orderDto) throws ExecutionException, InterruptedException {
        orderService.updateOrder(orderDto);
        return new ResponseEntity<>("Order updated", HttpStatus.OK);
    }

    @PostMapping("/getAll")
    public ResponseEntity<List<CheckOrders>> getAllOrders(@RequestBody CheckOrders checkOrders) {
        return ResponseEntity.ok(orderService.checkOrders(checkOrders));
    }




}
