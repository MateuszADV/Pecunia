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
public class CurrencyNoteDto {

    private Long id;
    private String cod;
    private String nameCurrency;
    private String change;
    private int active;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<NoteJsonDto> banknotes;
}
