package pl.mateusz.Pecunia.services.customerService;

import pl.mateusz.Pecunia.models.dtos.CustomerDto;

public interface CustomerService {
    CustomerDto encodeCustomer(CustomerDto customerDto);
}
