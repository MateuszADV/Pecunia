package pl.mateusz.Pecunia.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.Order;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;
import pl.mateusz.Pecunia.services.customerService.CustomerService;

@Service
public class OrderUtils {

    @Autowired
    CustomerService customerService;

    CustomerDto customerDto;
    Order order;

    public CustomerDto customerDto (String uniqueId) {
        customerDto = customerService.customerDtoDetails(uniqueId);
        return customerDto;
    }

    public CustomerDto customerDto() {
        return customerDto;
    }


}
