package pl.mateusz.Pecunia.services.customerService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.Customer;
import pl.mateusz.Pecunia.models.dtos.CustomerBasic;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;
import pl.mateusz.Pecunia.models.repositories.CustomerRepository;
import pl.mateusz.Pecunia.utils.Base64Utils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto encodeCustomer(CustomerDto customerDto) {
        customerDto.setName(Base64Utils.encodeString(customerDto.getName()));
        customerDto.setLastname(Base64Utils.encodeString(customerDto.getLastname()));
        customerDto.setCity(Base64Utils.encodeString(customerDto.getCity()));
        customerDto.setZipCode(Base64Utils.encodeString(customerDto.getZipCode()));
        customerDto.setStreet(Base64Utils.encodeString(customerDto.getStreet()));
        customerDto.setNumber(Base64Utils.encodeString(customerDto.getNumber()));
        customerDto.setEmail(Base64Utils.encodeString(customerDto.getEmail()));
        customerDto.setPhone(Base64Utils.encodeString(customerDto.getPhone()));
        return customerDto;
    }

    @Override
    public List<CustomerDto> decodeCustomer(List<Customer> customerList) {
        List<CustomerDto> customerDtoList = new ArrayList<>();

        for (Customer customer : customerList) {
            customerDtoList.add(new ModelMapper().map(decodeCustomer(customer), CustomerDto.class));
        }
        return customerDtoList;
    }

    @Override
    public Customer decodeCustomer(Customer customer) {
        customer.setName(Base64Utils.decodeData(customer.getName()));
        customer.setLastname(Base64Utils.decodeData(customer.getLastname()));
        customer.setCity(Base64Utils.decodeData(customer.getCity()));
        customer.setZipCode(Base64Utils.decodeData(customer.getZipCode()));
        customer.setStreet(Base64Utils.decodeData(customer.getStreet()));
        customer.setNumber(Base64Utils.decodeData(customer.getNumber()));
        customer.setEmail(Base64Utils.decodeData(customer.getEmail()));
        customer.setPhone(Base64Utils.decodeData(customer.getPhone()));
        return customer;
    }

    @Override
    public List<CustomerBasic> customerBasicList(List<Customer> customerList) {
        List<CustomerBasic> customerBasicList = new ArrayList<>();

        for (Customer customer : customerList) {
            customerBasicList.add(new ModelMapper().map(decodeCustomer(customer), CustomerBasic.class));
        }

        return customerBasicList;
    }

    @Override
    public CustomerDto customerDtoDetails(String uniqueId) {
        Customer customer = customerRepository.customer(uniqueId);

        CustomerDto customerDto = new ModelMapper().map(decodeCustomer(customer), CustomerDto.class);
        return customerDto;
    }
}
