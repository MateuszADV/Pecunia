package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountryViewDto {

    private Long id;
    private String country_en;
    private String cod;
    private String currency;
    private String change;
    private int active;
}
