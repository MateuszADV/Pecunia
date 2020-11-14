package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CurrencyCoinDto {

    private Long id;
    private String cod;
    private String nameCurrency;
    private String change;
    private int active;
    private String dataExchange;
    private String currencyFrom;
    private String converter;
    private String description;

    private String pattern;     //Okrerśla rodzaj pieniądza NOTE/COIN

    private List<Coins> coinDtosList;
}
