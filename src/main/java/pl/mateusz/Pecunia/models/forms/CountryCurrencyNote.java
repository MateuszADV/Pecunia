package pl.mateusz.Pecunia.models.forms;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mateusz.Pecunia.models.dtos.CurrencyDto;
import pl.mateusz.Pecunia.models.dtos.CurrencyNoteDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CountryCurrencyNote {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String country;
    private String alfa3;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CurrencyNoteDto> currencys;
}
