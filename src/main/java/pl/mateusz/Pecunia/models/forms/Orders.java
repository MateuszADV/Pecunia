package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mateusz.Pecunia.models.dtos.OrderItemDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Orders {

    private Long id;
    private String orderNumber;
    private LocalDate dateOrder;
    private Boolean active;
    private LocalDate dateSend;
    private String trackingNumber;
    private String shipmentType;
    private Double shippingCosts;

    List<Items> itemsList = new ArrayList<>();
}
