package pl.mateusz.Pecunia.services.OrderService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.Order;
import pl.mateusz.Pecunia.models.OrderItem;
import pl.mateusz.Pecunia.models.dtos.OrderItemDto;
import pl.mateusz.Pecunia.models.repositories.OrderItemRepository;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void seveOrderItems(Order order, List<OrderItemDto> orderItemDto) {

        for (OrderItemDto itemDto : orderItemDto) {
            saveSaveOrderItem(order,itemDto);
        }

    }

    private void saveSaveOrderItem(Order order, OrderItemDto orderItemDto) {
        OrderItem orderItem = new ModelMapper().map(orderItemDto, OrderItem.class);
        orderItem.setOrder(order);
//        orderItemRepository.save(orderItem);
        System.out.println("ZAPIS DO BAZY");
    }
}
