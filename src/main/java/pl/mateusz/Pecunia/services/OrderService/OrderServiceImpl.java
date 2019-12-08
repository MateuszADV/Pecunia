package pl.mateusz.Pecunia.services.OrderService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.Order;
import pl.mateusz.Pecunia.models.OrderItem;
import pl.mateusz.Pecunia.models.dtos.OrderItemDto;
import pl.mateusz.Pecunia.models.forms.Orders;
import pl.mateusz.Pecunia.models.repositories.OrderItemRepository;
import pl.mateusz.Pecunia.models.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void seveOrderItems(Order order, List<OrderItemDto> orderItemDto) {

        for (OrderItemDto itemDto : orderItemDto) {
            saveSaveOrderItem(order,itemDto);
        }

        System.out.println("ZAPIS DO BAZY \n" +
                "ILOść ZAPISANYCH ELEMENTów: " + orderItemDto.size());

    }

    @Override
    public List<OrderItemDto> getOrderDetails(Long orderId) {
        List<OrderItem> orderItemList = orderItemRepository.orderItemsList(orderId);
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();

        for (OrderItem orderItem : orderItemList) {
            orderItemDtoList.add(new ModelMapper().map(orderItem, OrderItemDto.class));
        }
        return orderItemDtoList;
    }

    private void saveSaveOrderItem(Order order, OrderItemDto orderItemDto) {
        OrderItem orderItem = new ModelMapper().map(orderItemDto, OrderItem.class);
        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);

    }

    @Override
    public Orders orderItems(long orderId) {
        Order order = orderRepository.order(orderId);
        Orders orders = new ModelMapper().map(order, Orders.class);

        return orders;
    }
}
