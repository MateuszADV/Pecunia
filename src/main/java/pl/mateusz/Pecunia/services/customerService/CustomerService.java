package pl.mateusz.Pecunia.services.customerService;

import pl.mateusz.Pecunia.models.Customer;
import pl.mateusz.Pecunia.models.dtos.CustomerBasic;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto encodeCustomer(CustomerDto customerDto);

    List<CustomerDto> decodeCustomer(List<Customer> customerList);

    Customer decodeCustomer(Customer customer);

    List<CustomerBasic> customerBasicList(List<Customer> customerList);

    CustomerDto customerDtoDetails(String uniqueId);
}
