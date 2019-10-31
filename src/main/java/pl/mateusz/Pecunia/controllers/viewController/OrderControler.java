package pl.mateusz.Pecunia.controllers.viewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;
import pl.mateusz.Pecunia.models.repositories.CustomerRepository;
import pl.mateusz.Pecunia.services.customerService.CustomerService;

@Controller
public class OrderControler {

    CustomerService customerService;

    @Autowired
    public OrderControler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping (value = {"/Pecunia/add_order/{uniqueId}", "/add_order/{uniqueId}"})
    public String getAddOrder(@PathVariable String  uniqueId, ModelMap modelMap) {
        CustomerDto customerDto = customerService.customerDtoDetails(uniqueId);
        modelMap.addAttribute("customerDetails", customerDto);
        System.out.println(customerDto);
        return "order";
    }

}
