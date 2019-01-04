package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CurrencyDto {

    private Long id;
    private String cod;
    private String nameCurrency;
    private String change;
    private int active;
    private String dataExchange;
    private String currencyFrom;
    private String converter;
    private String description;
}
