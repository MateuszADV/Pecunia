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

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    public OrderServiceImpl() {
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

    @Override
    public String nextNumberOrder(String lastNumberOrder) {

        if (checkLastNumberOrder(lastNumberOrder)) {
            Map<String, String> elements = elementsOfNumberOrder(lastNumberOrder);
            return dateOrderAndNextNumberOrder(elements.get("year"), elements.get("month"), elements.get("orderYear"), elements.get("orderTotal"));
        } else {
            String rok = String.valueOf(LocalDate.now().getYear());
            String miesiac = String.valueOf(LocalDate.now().getMonthValue());
            if (miesiac.length() == 1) {
                miesiac = "0" + miesiac;
            }

            return rok + "/" + miesiac + "/00000/00000";
        }
    }

    @Override
    public String getLastNumberOrder() {
        String lastNumberOrder = orderRepository.lastNumberOrder();
        return lastNumberOrder;
    }

    public Boolean checkLastNumberOrder(String lastNumberOrder) {
        Pattern pattern = Pattern.compile("\\d{4}/\\d{2}/\\d{3,5}/\\d{3,5}");
        if (pattern.matcher(lastNumberOrder).matches()) {
            return true;
        }
        return false;
    }

    public Map<String, String> elementsOfNumberOrder(String lastNumberOrder) {
        Map<String, String> elementsOrderMap = new LinkedHashMap<>();
        String[] elementsOrder = lastNumberOrder.split("/");

        elementsOrderMap.put("year", elementsOrder[0]);
        elementsOrderMap.put("month", elementsOrder[1]);
        elementsOrderMap.put("orderYear", elementsOrder[2]);
        elementsOrderMap.put("orderTotal", elementsOrder[3]);

        return elementsOrderMap;
    }

    public String nextNumber(String lastNumber) {
        Integer lengthNumber = lastNumber.length();

        try {
            Integer numberOrder = Integer.valueOf(lastNumber);
            lastNumber = String.valueOf(++numberOrder);
            while (lengthNumber > lastNumber.length()) {
                lastNumber = "0"+lastNumber;
            }
            return lastNumber;
        }catch (Exception e) {
            return "00000";
        }
    }

    public String dateOrderAndNextNumberOrder(String year, String month, String orderYear, String orderTotal) {

        String rok = String.valueOf(LocalDate.now().getYear());
        String miesiac = String.valueOf(LocalDate.now().getMonthValue());
        if (miesiac.length() == 1) {
            miesiac = "0" + miesiac;
        }

        if (!year.equals(rok)) {
            year = rok;
            month = miesiac;
            orderYear = "001";
            orderTotal = nextNumber(orderTotal);
            return year + "/" + month + "/" + orderYear + "/" + orderTotal;
        }
        if (!month.equals(miesiac) || month.equals(miesiac)) {
            month = miesiac;
            orderYear = nextNumber(orderYear);
            orderTotal = nextNumber(orderTotal);
            return year + "/" + month + "/" + orderYear + "/" + orderTotal;
        }

        String dataOrder = String.format("%s/%s", year, month);

        return dataOrder;
    }
}
