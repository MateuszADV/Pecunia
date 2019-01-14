package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CountryJsonDto {
    private Long id;
    private String continent;
    private String countryEn;
    private List<CurrencyDto> currencys;
}
