package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.mateusz.Pecunia.models.OrderItem;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "SELECT items FROM OrderItem items" +
                   " WHERE items.order.id = ?1" +
                   " ORDER BY items.countryEn")
    List<OrderItem> orderItemsList(Long orderId);

    @Query(value = "DELETE FROM OrderItem items WHERE items.id = ?1")
    OrderItem deleteOrderItem(Long itemId);


    void deleteById(Long itemId);
}
