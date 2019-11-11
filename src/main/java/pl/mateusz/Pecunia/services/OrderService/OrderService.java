package pl.mateusz.Pecunia.services.OrderService;


import pl.mateusz.Pecunia.models.Order;
import pl.mateusz.Pecunia.models.dtos.OrderItemDto;

import java.util.List;

public interface OrderService {

    void seveOrderItems(Order order, List<OrderItemDto> orderItemDto);
}
