package pl.mateusz.Pecunia.services.customerService;

import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;
import pl.mateusz.Pecunia.utils.CustomerUtils;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto encodeCustomer(CustomerDto customerDto) {
        customerDto.setName(CustomerUtils.encodeData(customerDto.getName()));
        customerDto.setLastname(CustomerUtils.encodeData(customerDto.getLastname()));
        return customerDto;
    }
}
