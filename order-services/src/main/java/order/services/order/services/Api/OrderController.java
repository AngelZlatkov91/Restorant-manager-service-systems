package order.services.order.services.Api;
import jakarta.validation.Valid;
import order.services.order.services.Models.DTO.Order.*;
import order.services.order.services.Services.OrderServ.CompleteOrdersServ;
import order.services.order.services.Services.OrderServ.CreateAndUpdateOrderServ;
import order.services.order.services.Services.OrderServ.DeleteProductFormOrder;
import order.services.order.services.Services.OrderServ.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final CreateAndUpdateOrderServ createAndUpdateOrderServ;
    private final OrderService orderService;
    private final CompleteOrdersServ completeOrdersServ;
    private final DeleteProductFormOrder deleteProductFormOrder;


    public OrderController(CreateAndUpdateOrderServ orderService, OrderService orderService1, CompleteOrdersServ completeOrdersServ, DeleteProductFormOrder deleteProductFormOrder) {
        this.createAndUpdateOrderServ = orderService;
        this.orderService = orderService1;
        this.completeOrdersServ = completeOrdersServ;
        this.deleteProductFormOrder = deleteProductFormOrder;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderDTO orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            String defaultMessage = allErrors.getFirst().getDefaultMessage();
            return ResponseEntity.badRequest().body(defaultMessage);
        }
        createAndUpdateOrderServ.createOrder(orderDto);
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }

    @PostMapping("/getOrder")
    public ResponseEntity<OrderResp> getOrder(@RequestBody ReqOrder reqOrder) {
     return ResponseEntity.ok(orderService.getOrder(reqOrder));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateOrder(@RequestBody OrderResp orderDto)  {
        createAndUpdateOrderServ.updateOrder(orderDto);
        return new ResponseEntity<>("Order updated", HttpStatus.OK);
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeOrder(@RequestBody PaymentMethodDTO paymentMethodDTO) {
       return ResponseEntity.ok(completeOrdersServ.completeOrder(paymentMethodDTO));
    }

    @DeleteMapping("/remove/product")
    public ResponseEntity<String> removeProduct(@RequestBody DeleteProduct deleteProduct) {
       String result =  deleteProductFormOrder.deleteProduct(deleteProduct);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderResp>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }



}
