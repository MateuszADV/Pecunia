package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mateusz.Pecunia.models.dtos.OrderItemDto;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItems {
    List<OrderItemDto> orderItemDtos = new ArrayList<>();
}
