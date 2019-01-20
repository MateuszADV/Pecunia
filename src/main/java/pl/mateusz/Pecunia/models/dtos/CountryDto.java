package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

    private Long id;
    private String continent;
    private String countryEn;
    private String countryPl;
    private String capitalCity;
    private String alfa2;
    private String alfa3;
    private String numericCode;
    private String isoCode;
}
