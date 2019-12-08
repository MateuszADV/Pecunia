package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import pl.mateusz.Pecunia.models.Customer;
import pl.mateusz.Pecunia.models.dtos.CustomerBasicDto;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT cust FROM Customer cust" +
                   " ORDER BY cust.lastname")
    List<Customer> customerList();

    @Query(value = "SELECT cust FROM Customer cust" +
                   " WHERE cust.uniqueId = ?1")
    Customer customer(String uniqueId);
}
