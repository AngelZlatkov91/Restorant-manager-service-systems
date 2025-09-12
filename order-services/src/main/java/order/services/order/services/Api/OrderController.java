package order.services.order.services.Api;
import order.services.order.services.Models.DTO.Order.*;
import order.services.order.services.Services.OrderServ.CompleteOrdersServ;
import order.services.order.services.Services.OrderServ.CreateAndUpdateOrderServ;
import order.services.order.services.Services.OrderServ.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final CreateAndUpdateOrderServ createAndUpdateOrderServ;
    private final OrderService orderService;
    private final CompleteOrdersServ completeOrdersServ;


    public OrderController(CreateAndUpdateOrderServ orderService, OrderService orderService1, CompleteOrdersServ completeOrdersServ) {
        this.createAndUpdateOrderServ = orderService;
        this.orderService = orderService1;
        this.completeOrdersServ = completeOrdersServ;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDto) {
        createAndUpdateOrderServ.createOrder(orderDto);
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResp> getOrder(@PathVariable Long id) {
     return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateOrder(@RequestBody OrderResp orderDto)  {
        createAndUpdateOrderServ.updateOrder(orderDto);
        return new ResponseEntity<>("Order updated", HttpStatus.OK);
    }

    @PostMapping("/getAll")
    public ResponseEntity<List<CheckOrders>> getAllOrders(@RequestBody CheckOrders checkOrders) {
        return ResponseEntity.ok(orderService.checkOrders(checkOrders));
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeOrder(@RequestBody PaymentMethodDTO paymentMethodDTO) {
        completeOrdersServ.completeOrder(paymentMethodDTO);
       return ResponseEntity.ok("The order is pay!");
    }



}
