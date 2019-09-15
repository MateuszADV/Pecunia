package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {

    private Long id;
    private String uniqueId;
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
}
