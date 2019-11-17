package pl.mateusz.Pecunia.utils;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.NoteInfoView;
import pl.mateusz.Pecunia.models.Order;
import pl.mateusz.Pecunia.models.dtos.CustomerDto;
import pl.mateusz.Pecunia.models.dtos.NoteInfoViewDto;
import pl.mateusz.Pecunia.models.dtos.OrderDto;
import pl.mateusz.Pecunia.models.dtos.OrderItemDto;
import pl.mateusz.Pecunia.models.forms.OrderItems;
import pl.mateusz.Pecunia.models.repositories.NoteInfoViewRepository;
import pl.mateusz.Pecunia.models.repositories.OrderRepository;
import pl.mateusz.Pecunia.services.customerService.CustomerService;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class OrderUtils {


    CustomerService customerService;
    OrderRepository orderRepository;
    NoteInfoViewRepository noteInfoViewRepository;

    @Autowired
    public OrderUtils(CustomerService customerService, OrderRepository orderRepository, NoteInfoViewRepository noteInfoViewRepository) {
        this.customerService = customerService;
        this.orderRepository = orderRepository;
        this.noteInfoViewRepository = noteInfoViewRepository;
    }

    CustomerDto customerDto;
    List<OrderDto> orderDtoList = new ArrayList<>();
    OrderItems orderItems = new OrderItems();

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public CustomerDto customerDto (String uniqueId) {
        customerDto = customerService.customerDtoDetails(uniqueId);
        return customerDto;
    }


    public List<OrderDto> getOrderDtoList(Long orderId) {
        Order order = orderRepository.order(orderId);
        List<Order> orderList = new ArrayList<>();
        orderDtoList.clear();
        orderList.add(order);
        for (Order order1 : orderList) {
            orderDtoList.add(new ModelMapper().map(order1, OrderDto.class));
        }
        return orderDtoList;
    }

    public OrderItems getOrderItems(Long noteId) {
        if (noteId != null) {
            try {
                NoteInfoView noteInfoView = noteInfoViewRepository.findByNoteId(noteId);
                NoteInfoViewDto noteInfoViewDto = new ModelMapper().map(noteInfoView, NoteInfoViewDto.class);
                orderItemDtoList.add(orderItemDto(noteInfoViewDto));
                orderItems.setOrderItemDtos(orderItemDtoList);

                return orderItems;
            }catch (Exception e) {
                return orderItems;
            }
        }
        return orderItems;
    }

    public void clearOrderDtoList() {
        orderItemDtoList.clear();
        orderItems.setOrderItemDtos(orderItemDtoList);
    }

    private OrderItemDto orderItemDto(NoteInfoViewDto noteInfoViewDto) {
        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setNoteId(noteInfoViewDto.getNoteId());
        orderItemDto.setCountryEn(noteInfoViewDto.getCountryEn());
        orderItemDto.setCod(noteInfoViewDto.getCod());
        orderItemDto.setDenomination(noteInfoViewDto.getDenomination());
        orderItemDto.setCurrencyName(noteInfoViewDto.getNameCurrency());
        orderItemDto.setNoteDate(noteInfoViewDto.getNoteDate());
        orderItemDto.setPriceBuy(noteInfoViewDto.getPriceBuy());
        orderItemDto.setPriceSell(noteInfoViewDto.getPriceSell());
        orderItemDto.setPriceSellFinal(noteInfoViewDto.getPriceSell());
        orderItemDto.setQuality(noteInfoViewDto.getQuality());
        orderItemDto.setMaking(noteInfoViewDto.getMaking());
        orderItemDto.setQuantity(1);
        orderItemDto.setDescription(noteInfoViewDto.getDescription());
        orderItemDto.setAversPath(noteInfoViewDto.getAversPath());
        orderItemDto.setReversePath(noteInfoViewDto.getReversePath());

        return orderItemDto;
    }

    public OrderItems orderItems(List<OrderItemDto> orderItemDtoList1) {
        System.out.println(orderItemDtoList1.size());
        orderItemDtoList.clear();
        for (OrderItemDto orderItemDto : orderItemDtoList1) {
            orderItemDtoList.add(orderItemDto);
        }
        return orderItems;
    }
}
