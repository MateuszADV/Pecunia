package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mateusz.Pecunia.models.Many;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoinDto extends Many {

    private Long id;
    private Double denomination;     //nominał ++
    @NotEmpty(message = "pole nie może byćpuste")
    private String coinDate;        //rok monety ++
    private String quality;         // stan monety ++
    private String series;          // lata bicia monetu ++
    private String composition;    // skład monety ++
    @NotEmpty(message = "polenie może być puste")
    @Size(min = 3, message = "Wyraz jest za krutki")
    private String bought;          // miejsce zakupu  ++

//
//    @NotEmpty(message = "Pole nie może być puste")
//    @Pattern(regexp = "\\d{1,4}.\\d{1,2}", message = "Popraw wartość")
    private Double diameter;         //średnica monety w mm ++
//    @Pattern(regexp = "\\d{1,4}.\\d{1,2}", message = "Podana wartość jest nipoporawna")
    private Double thickness;       // grubość monety w mm ++
    private Double weight;          //waga monety w gramach


}
