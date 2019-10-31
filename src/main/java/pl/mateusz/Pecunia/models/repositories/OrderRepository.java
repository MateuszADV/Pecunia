package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mateusz.Pecunia.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
