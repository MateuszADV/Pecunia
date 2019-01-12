package pl.mateusz.Pecunia.models.forms;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class CountryOfCurrency {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String country;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CurrencyDto> currencys;
}
