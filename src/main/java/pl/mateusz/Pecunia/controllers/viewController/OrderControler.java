package pl.mateusz.Pecunia.controllers.viewController;


import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.models.Customer;
import pl.mateusz.Pecunia.models.Order;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;
import pl.mateusz.Pecunia.models.dtos.OrderDto;
import pl.mateusz.Pecunia.models.dtos.OrderItemDto;
import pl.mateusz.Pecunia.models.forms.enums.DeliveryEnum;
import pl.mateusz.Pecunia.models.repositories.CustomerRepository;
import pl.mateusz.Pecunia.models.repositories.OrderRepository;
import pl.mateusz.Pecunia.services.OrderService.OrderService;
import pl.mateusz.Pecunia.services.customerService.CustomerService;
import pl.mateusz.Pecunia.utils.OrderUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class OrderControler {


    private OrderService orderService;
    private CustomerService customerService;
    private OrderUtils orderUtils;
    private OrderRepository orderRepository;

    @Autowired
    public OrderControler(OrderService orderService, CustomerService customerService, OrderUtils orderUtils, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.orderUtils = orderUtils;
        this.orderRepository = orderRepository;
    }



    @GetMapping (value = {"/Pecunia/add_order/{uniqueId}", "/add_order/{uniqueId}"})
    public String getAddOrder(@PathVariable String  uniqueId, ModelMap modelMap) {
        CustomerDto customerDto = orderUtils.customerDto(uniqueId);
        OrderDto orderDto = new OrderDto();
        orderDto.setDateOrder(LocalDate.now());
        orderDto.setDateSend(LocalDate.now());
        modelMap.addAttribute("customerDetails", customerDto);
        modelMap.addAttribute("orderDto", orderDto);
        modelMap.addAttribute("delivery", DeliveryEnum.values());

        return "order";
    }

    @PostMapping(value = {"/Pecunia/order", "/order"})
    public String postCustomer(@ModelAttribute("orderDto") OrderDto orderDto,
                               ModelMap modelMap) {
        //TODO Zrobić poprawki kodu zoptymalizować

        Logger LOGGER = Logger.getLogger(CustomerDto.class.toString());
        CustomerDto customerDto = orderUtils.getCustomerDto();
        modelMap.addAttribute("customerDetails",orderUtils.getCustomerDto());

        Order order = new ModelMapper().map(orderDto, Order.class);
        Customer customer = new ModelMapper().map(customerDto, Customer.class);
        order.setCustomer(customer);
        orderRepository.save(order);

        LOGGER.log(Level.INFO, orderDto.toString());

        List<OrderDto> orderDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.OrderList(customer.getId());
        for (Order order1 : orderList) {
            orderDtoList.add(new ModelMapper().map(order1, OrderDto.class));
        }
        modelMap.addAttribute("orderList" ,orderDtoList);

        return "order_items";
    }


    @GetMapping (value = {"/Pecunia/order_list/{uniqueId}", "/order_list/{uniqueId}"})
    public String getOderList(@PathVariable String  uniqueId, ModelMap modelMap) {
        CustomerDto customerDto = orderUtils.customerDto(uniqueId);
        modelMap.addAttribute("customerDetails",customerDto);
        List<Order> orderList = orderRepository.OrderList(uniqueId);
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order : orderList) {
            orderDtoList.add(new ModelMapper().map(order, OrderDto.class));
        }

        modelMap.addAttribute("orderList" ,orderDtoList);

        System.out.println("czyszczenie listy");
        orderUtils.clearOrderDtoList();
        return "customer_Details";
    }

    @GetMapping (value = {"/Pecunia/order/{orderId}", "/order/{orderId}"})
    public String getOrderEdit(@PathVariable Long orderId, ModelMap modelMap) {
        Order order = orderRepository.order(orderId);

        CustomerDto customerDto = orderUtils.customerDto(order.getCustomer().getUniqueId());
        modelMap.addAttribute("customerDetails",customerDto);

        OrderDto orderDto = new ModelMapper().map(order, OrderDto.class);
        modelMap.addAttribute("orderDto", orderDto);
        modelMap.addAttribute("delivery", DeliveryEnum.values());

        System.out.println("czyszczenie listy");
        orderUtils.clearOrderDtoList();
        return "order";

    }

    @GetMapping (value = {"/Pecunia/order_items_add/{orderId}", "/order_items_add/{orderId}"})
    public String getOrderItemAdd(@PathVariable Long orderId, ModelMap modelMap) {

        Order order = orderRepository.order(orderId);
        CustomerDto customerDto = orderUtils.customerDto(order.getCustomer().getUniqueId());

        modelMap.addAttribute("customerDetails",customerDto);
        modelMap.addAttribute("orderList" ,orderUtils.getOrderDtoList(orderId));
        modelMap.addAttribute("order_item_add", true);
        return "order_items";
    }

    @PostMapping (value = {"/Pecunia/order_item", "/order_item"})
    public String postOrderItem(@RequestParam(value = "noteId") Long noteId,
                                @RequestParam(value = "sellFinal") String sellFinal,
                                @RequestParam(value = "sellQuantity") String sellQuantity,
                                ModelMap modelMap) {

        //TODO ZOPTYMALIZOWAĆ, ZREDUKOWAĆ KOD DO BARDZIEJ ELEGANCKIEJ FORMY
        modelMap.addAttribute("customerDetails",orderUtils.getCustomerDto());
        modelMap.addAttribute("orderList" ,orderUtils.getOrderDtoList());
        modelMap.addAttribute("order_item_add", true);

        updatePriceSellFinal(noteId, sellFinal, sellQuantity);

        modelMap.addAttribute("orderItems", true);
        modelMap.addAttribute("orderItemList", orderUtils.getOrderItems().getOrderItemDtos());

        return "order_items";
    }

    private void updatePriceSellFinal(@RequestParam("noteId") Long noteId,
                                      @RequestParam("sellFinal") String sellFinal,
                                      @RequestParam("sellQuantity") String sellQuantity) {
        orderUtils.getOrderItems(noteId);

        System.out.println("CENA FINALNA: " + sellFinal);

        String[] priceSellFinal = sellFinal.split(",");
        String[] sellQuantityFinal = sellQuantity.split(",");

        System.out.println("ILOść elementów tablicy: " + priceSellFinal.length + "zawartość tablicy: " + priceSellFinal[0]);

        if (orderUtils.getOrderItems().getOrderItemDtos().size() == 1 && sellFinal == "0") {
            priceSellFinal[0] = orderUtils.getOrderItems().getOrderItemDtos().get(0).getPriceSell().toString();
            sellQuantityFinal[0] = orderUtils.getOrderItems().getOrderItemDtos().get(0).getQuantity().toString();
        }
        for (OrderItemDto orderItemDto : orderUtils.getOrderItems().getOrderItemDtos()) {
            if (orderUtils.getOrderItems().getOrderItemDtos().indexOf(orderItemDto) == priceSellFinal.length) {
                break;
            }
            orderItemDto.setPriceSellFinal(Double.valueOf(priceSellFinal[orderUtils.getOrderItems().getOrderItemDtos().indexOf(orderItemDto)]));
            orderItemDto.setQuantity(Integer.valueOf(sellQuantityFinal[orderUtils.getOrderItems().getOrderItemDtos().indexOf(orderItemDto)]));
        }
    }

    @GetMapping(value = {"/Pecunia/save_order/", "/save_order"})
    public String getSaveOrder(ModelMap modelMap) {
        //TODO ZOPTYMALIZOWAĆ, ZREDUKOWAĆ KOD DO BARDZIEJ ELEGANCKIEJ FORMY
        modelMap.addAttribute("customerDetails",orderUtils.getCustomerDto());
        modelMap.addAttribute("orderList" ,orderUtils.getOrderDtoList());
        modelMap.addAttribute("order_item_add", true);

        modelMap.addAttribute("orderItems", true);
        modelMap.addAttribute("orderItemList", orderUtils.getOrderItems().getOrderItemDtos());

        modelMap.addAttribute("saveOrder" ,true);

        for (OrderItemDto orderItemDto : orderUtils.getOrderItems().getOrderItemDtos()) {
            System.out.println(orderItemDto);
        }
        Order order = new ModelMapper().map(orderUtils.getOrderDtoList().get(0), Order.class);

        orderService.seveOrderItems(order, orderUtils.getOrderItemDtoList());
        System.out.println(order.getOrderNumber());


        return "order_items";
    }
}
