package pl.mateusz.Pecunia.controllers.viewController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.controllers.Constans;
import pl.mateusz.Pecunia.models.Customer;
import pl.mateusz.Pecunia.models.Order;
import pl.mateusz.Pecunia.models.dtos.CustomerBasicDto;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;
import pl.mateusz.Pecunia.models.dtos.OrderDto;
import pl.mateusz.Pecunia.models.repositories.CustomerRepository;
import pl.mateusz.Pecunia.models.repositories.OrderRepository;
import pl.mateusz.Pecunia.services.customerService.CustomerService;
import pl.mateusz.Pecunia.utils.Base64Utils;
import pl.mateusz.Pecunia.utils.OrderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class CustomerControler {

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private OrderUtils orderUtils;
    private OrderRepository orderRepository;

    public CustomerControler(CustomerService customerService, CustomerRepository customerRepository, OrderUtils orderUtils, OrderRepository orderRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.orderUtils = orderUtils;
        this.orderRepository = orderRepository;
    }

    @GetMapping(value = {"/Pecunia/customer", "/customer"})
    public String getCustomer(ModelMap modelMap) {
        newCustomer(modelMap);
        modelMap.addAttribute("button", Constans.BUTTON_ADD_CUSTOMER);
        modelMap.addAttribute("customer", Constans.NEW_CUSTOMER);
        return "customer";
    }

    private void newCustomer(ModelMap modelMap) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUniqueId(Base64Utils.uniqueCustomerId());
        customerDto.setActive(true);
        modelMap.addAttribute("customerDto", customerDto);
    }

    @PostMapping(value = {"/Pecunia/customer/add", "/customer/add"})
    public String postCustomerSave(@ModelAttribute("customerDto") CustomerDto customerDto, ModelMap modelMap) {
        Logger LOGGER = Logger.getLogger(CustomerDto.class.toString());
        Customer customer = customerService.saveCustomer(customerDto);
        modelMap.addAttribute("customerDetails", customerService.decodeCustomer(customer));
        LOGGER.log(Level.INFO, customerDto.toString());

//        newCustomer(modelMap);
        return "customer_details";
    }

    @GetMapping(value = {"/Pecunia/customerList", "/customerList"})
    public String getCustomerList(ModelMap modelMap) {
        Logger LOGGER = Logger.getLogger(CustomerBasicDto.class.toString());
        List<CustomerBasicDto> customerBasicDtoList = customerService.getCustomerBassicList();
        modelMap.addAttribute("customerBasic", customerBasicDtoList);
        LOGGER.log(Level.INFO, customerBasicDtoList.toString());
        return "customer_basic";
    }

    @GetMapping(value = {"/Pecunia/customerDetails/{uniqueId}", "/customerDetails/{uniqueId}"})
    public String getCustomerDetails(@PathVariable String uniqueId,
                                  ModelMap modelMap) {
        Logger LOGGER = Logger.getLogger(Exception.class.toString());
        List<Order> orderList = orderRepository.OrderList(uniqueId);
        List<OrderDto> orderDtoList = new ArrayList<>();
        try {

            CustomerDto customerDtoDetails = customerService.customerDtoDetails(uniqueId);
            modelMap.addAttribute("customerDetails", customerDtoDetails);

            for (Order order : orderList) {
                orderDtoList.add(new ModelMapper().map(order, OrderDto.class));
            }

            modelMap.addAttribute("orderList" ,orderDtoList);
        }
        catch (Exception e) {
            modelMap.addAttribute("Error", "Podano nieprawidłowy numer klienta");
            LOGGER.log(Level.WARNING, e.toString());
        }

        System.out.println(orderDtoList);

        return "customer_details";
    }

    @GetMapping(value = {"/Pecunia/customerEdit/{uniqueId}", "/customerEdit/{uniqueId}"})
    public String getCustomerEdit(@PathVariable String uniqueId, ModelMap modelMap) {

        Logger LOGGER = Logger.getLogger(Exception.class.toString());

        try {
            CustomerDto customerDtoDetails = customerService.customerDtoDetails(uniqueId);
            modelMap.addAttribute("customerDto", customerDtoDetails);
            modelMap.addAttribute("button", Constans.BUTTON_SAVE_CHANGE);
            modelMap.addAttribute("customer", Constans.EDIT_CUSTOMER_DATE);
        }
        catch (Exception e) {
            modelMap.addAttribute("Error", "cos poszło nie tak");
            LOGGER.log(Level.WARNING, e.toString());
        }
        return "customer";
    }

    @PostMapping(value = {"/Pecunia/searchCustomer", "/searchCustomer"})
    public String postSearchCustomer(@RequestParam(value = "nameCustomer") String nameCustomer, ModelMap modelMap) {
        Logger LOGGER = Logger.getLogger(String.class.toString());
        List<CustomerBasicDto> customerBasicList = customerService.findCustomer(nameCustomer);
        modelMap.addAttribute("customerBasic", customerBasicList);
        LOGGER.log(Level.INFO, customerBasicList.toString());
        return "customer_basic";
    }

}
