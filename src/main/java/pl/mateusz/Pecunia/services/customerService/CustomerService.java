package pl.mateusz.Pecunia.services.customerService;

import pl.mateusz.Pecunia.models.Customer;
import pl.mateusz.Pecunia.models.dtos.CustomerBasicDto;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;
import pl.mateusz.Pecunia.models.forms.CustomerOrders;
import pl.mateusz.Pecunia.models.forms.Orders;

import java.util.List;

public interface CustomerService {
    CustomerDto encodeCustomer(CustomerDto customerDto);

    List<CustomerDto> decodeCustomer(List<Customer> customerList);

    Customer decodeCustomer(Customer customer);

    Customer saveCustomer(CustomerDto customerDto);

    List<CustomerBasicDto> customerBasicList(List<Customer> customerList);

    CustomerDto customerDtoDetails(String uniqueId);

    List<CustomerBasicDto> getCustomerBassicList();

    CustomerOrders customerOrders(String uniqueId);

    List<CustomerBasicDto> findCustomer(String name);

}
