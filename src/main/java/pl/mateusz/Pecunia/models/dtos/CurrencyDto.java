package pl.mateusz.Pecunia.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<NoteJsonDto> noteList;
}
