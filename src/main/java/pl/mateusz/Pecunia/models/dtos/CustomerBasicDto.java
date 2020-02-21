package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CustomerBasicDto {

    private String uniqueId;
    private Boolean active;
    private String name;
    private String lastname;
    private String city;

    private String phone;
}
