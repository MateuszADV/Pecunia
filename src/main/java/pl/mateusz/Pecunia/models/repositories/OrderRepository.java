package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.mateusz.Pecunia.models.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT ord FROM Order ord " +
                   " WHERE ord.customer.id = ?1" +
                   "   AND ord.active = true")
    List<Order> OrderList(Long customerId);

    @Query(value = "SELECT ord FROM Order ord " +
                   " WHERE ord.customer.uniqueId = ?1" +
                   " ORDER BY ord.orderNumber")
    List<Order> OrderList(String  uniqueId);

    @Query(value = "SELECT ord FROM Order ord" +
                   " WHERE ord.id = ?1")
    Order order(Long orderId);

    @Query(value = "SELECT ord.orderNumber FROM Order ord " +
                   "WHERE ord.id=(SELECT max(ord.id) FROM ord) ")
    String lastNumberOrder();


}
