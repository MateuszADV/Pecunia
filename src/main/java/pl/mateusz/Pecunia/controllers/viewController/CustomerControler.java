package pl.mateusz.Pecunia.controllers.viewController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.mateusz.Pecunia.models.Customer;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;
import pl.mateusz.Pecunia.models.repositories.CustomerRepository;
import pl.mateusz.Pecunia.services.customerService.CustomerService;
import pl.mateusz.Pecunia.utils.CustomerUtils;

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
        return "customer";
    }

    private void newCustomer(ModelMap modelMap) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUniqueId(CustomerUtils.uniqueCustomerId());
        modelMap.addAttribute("customerDto", customerDto);
    }

    @PostMapping(value = {"/Pecunia/customer", "/customer"})
    public String postCustomer(@ModelAttribute("customerDto") CustomerDto customerDto, ModelMap modelMap) {

        System.out.println("Dane klienta przed zakodowaniem" + customerDto);

        System.out.println(customerService.encodeCustomer(customerDto));

        Customer customer = new ModelMapper().map(customerDto, Customer.class);
        customerRepository.save(customer);
        newCustomer(modelMap);
        return "customer";
    }
}
