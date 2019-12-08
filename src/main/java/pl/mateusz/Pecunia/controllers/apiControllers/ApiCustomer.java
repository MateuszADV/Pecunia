package pl.mateusz.Pecunia.controllers.apiControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.Pecunia.models.dtos.CustomerBasicDto;
import pl.mateusz.Pecunia.models.forms.CustomerOrders;
import pl.mateusz.Pecunia.models.forms.Orders;
import pl.mateusz.Pecunia.services.OrderService.OrderService;
import pl.mateusz.Pecunia.services.customerService.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiCustomer {

    private CustomerService customerService;
    private OrderService orderService;

    @Autowired
    public ApiCustomer(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerBasicDto>> getCustomer() {
        return ResponseEntity.ok().body(customerService.getCustomerBassicList());
    }

    @GetMapping("/customer/orders")
    public ResponseEntity<CustomerOrders> getCustomerOrders(@RequestParam String uniqueId) {
        return ResponseEntity.ok().body(customerService.customerOrders(uniqueId));
    }

    @GetMapping("/order/items")
    public ResponseEntity<Orders> getOrderItems(@RequestParam Long orderId) {
        return ResponseEntity.ok().body(orderService.orderItems(orderId));
    }

    @GetMapping("/customer/find")
    public ResponseEntity<List<CustomerBasicDto>> getFindCustomer(@RequestParam String nameCustomer) {
        return ResponseEntity.ok().body(customerService.findCustomer(nameCustomer));
    }
}
