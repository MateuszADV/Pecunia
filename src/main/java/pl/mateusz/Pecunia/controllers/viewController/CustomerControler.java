package pl.mateusz.Pecunia.controllers.viewController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.controllers.Constans;
import pl.mateusz.Pecunia.models.Customer;
import pl.mateusz.Pecunia.models.dtos.CustomerBasic;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;
import pl.mateusz.Pecunia.models.repositories.CustomerRepository;
import pl.mateusz.Pecunia.services.customerService.CustomerService;
import pl.mateusz.Pecunia.utils.Base64Utils;
import pl.mateusz.Pecunia.utils.OrderUtils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class CustomerControler {

    private CustomerService customerService;
    private CustomerRepository customerRepository;



    @Autowired
    public CustomerControler(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
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

    @PostMapping(value = {"/Pecunia/customer", "/customer"})
    public String postCustomer(@ModelAttribute("customerDto") CustomerDto customerDto, ModelMap modelMap) {
        Logger LOGGER = Logger.getLogger(CustomerDto.class.toString());

        customerDto = customerService.encodeCustomer(customerDto);
        Customer customer = new ModelMapper().map(customerDto, Customer.class);
        customerRepository.save(customer);

        modelMap.addAttribute("customerDetails",customerService.decodeCustomer(customer) );

        LOGGER.log(Level.INFO, customerDto.toString());

//        newCustomer(modelMap);
        return "customer_details";
    }

    @GetMapping(value = {"/Pecunia/customerList", "/customerList"})
    public String getCustomerList(ModelMap modelMap) {
        Logger LOGGER = Logger.getLogger(CustomerDto.class.toString());

        List<Customer> customerList = customerRepository.customerList();
        List<CustomerBasic> customerBasicList = customerService.customerBasicList(customerList);
        customerBasicList = customerBasicList.stream()
//                .filter(s -> s.getActive().equals(true))
//                .filter(s -> s.getLastname().toLowerCase().contains("ła".toLowerCase()))
                .sorted((o1, o2) -> o1.getLastname().compareToIgnoreCase(o2.getLastname()))
                .collect(Collectors.toList());
        modelMap.addAttribute("customerBasic", customerBasicList);

//        customerBasicList.forEach(System.out::println);
        LOGGER.log(Level.INFO, customerBasicList.toString());

//        Collections.sort(customerBasicList, new Comparator<CustomerBasic>() {
//            @Override
//            public int compare(CustomerBasic o1, CustomerBasic o2) {
//                return o1.getLastname().compareToIgnoreCase(o2.getLastname());
//            }
//        });//
//        System.out.println("\n\n Powinno byćposortowane według nazwiska");
//        customerBasicList.forEach(System.out::println);

        return "customer_basic";
    }

    @GetMapping(value = {"/Pecunia/customerDetails/{uniqueId}", "/customerDetails/{uniqueId}"})
    public String getCustomerDetails(@PathVariable String uniqueId,
                                  ModelMap modelMap) {
        Logger LOGGER = Logger.getLogger(Exception.class.toString());

        try {
            CustomerDto customerDtoDetails = customerService.customerDtoDetails(uniqueId);
            modelMap.addAttribute("customerDetails", customerDtoDetails);
        }
        catch (Exception e) {
            modelMap.addAttribute("Error", "Podano nieprawidłowy numer klienta");
            LOGGER.log(Level.WARNING, e.toString());
        }

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
    public String postSearchCustomer(@RequestParam(value = "searchCustomer") String searchCustomer, ModelMap modelMap) {

        Logger LOGGER = Logger.getLogger(String.class.toString());

        List<Customer> customerList = customerRepository.customerList();
        List<CustomerBasic> customerBasicList = customerService.customerBasicList(customerList);
        customerBasicList = customerBasicList.stream()
//                .filter(s -> s.getActive().equals(true))
                .filter(s -> s.getLastname().toLowerCase().contains(searchCustomer.toLowerCase())
                        || s.getName().toLowerCase().contains(searchCustomer.toLowerCase()))
                .sorted((o1, o2) -> o1.getLastname().compareToIgnoreCase(o2.getLastname()))
                .collect(Collectors.toList());
        modelMap.addAttribute("customerBasic", customerBasicList);

        customerBasicList.forEach(System.out::println);
        LOGGER.log(Level.INFO, customerBasicList.toString());
        System.out.println(searchCustomer);

        return "customer_basic";
    }

}
