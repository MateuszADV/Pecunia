package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mateusz.Pecunia.models.dtos.OrderDto;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CustomerOrders {

    private Long id;
    private String uniqueId;
    private Boolean active;
    private String name;
    private String lastname;
    private String city;
    private String zipCode;
    private String street;
    private String number;
    private String email;
    private String nick;
    private String phone;
    private String descryption;

    private List<Orders> ordersList = new ArrayList<>();
}
