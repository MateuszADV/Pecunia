package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mateusz.Pecunia.models.Many;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coins extends Many {

    private Long id;
    private Double denomination;     //nominał ++
    private String coinDate;        //rok monety ++
    private String quality;         // stan monety ++
    private String series;          // lata bicia monetu ++
    private String composition;    // skład monety ++
    private String bought;          // miejsce zakupu  ++

//
//    @NotEmpty(message = "Pole nie może być puste")
//    @Pattern(regexp = "\\d{1,4}.\\d{1,2}", message = "Popraw wartość")
    private Double diameter;         //średnica monety w mm ++
//    @Pattern(regexp = "\\d{1,4}.\\d{1,2}", message = "Podana wartość jest nipoporawna")
    private Double thickness;       // grubość monety w mm ++
    private Double weight;          //waga monety w gramach


}
