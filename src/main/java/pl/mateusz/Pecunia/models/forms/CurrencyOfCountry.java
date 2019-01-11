package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mateusz.Pecunia.models.dtos.CurrencyDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CurrencyOfCountry {
    private String country;
    private List<CurrencyDto> currencys;
}
