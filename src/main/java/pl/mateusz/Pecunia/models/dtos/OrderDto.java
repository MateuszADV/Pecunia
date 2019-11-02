package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDto {

    private Long id;
    private String orderNumber;
    private LocalDate dateOrder;
    private Boolean active;
    private LocalDate dateSend;
    private String trackingNumber;
    private String shipmentType;
    private Double shippingCosts;
}
