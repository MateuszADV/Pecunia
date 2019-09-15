package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mateusz.Pecunia.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
