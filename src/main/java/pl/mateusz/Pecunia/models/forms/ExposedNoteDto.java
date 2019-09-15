package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExposedNoteDto {
    private Long noteId;
    private String countryEn;
    private String cod;
    private Double denomination;
    private String nameCurrency;
    private String noteDate;
    private Integer signatureCode;  // Kod opisujący czy banknot jest obiegowy, wymiwenny, nie obiegowy...
    private Double priceBuy;
    private Double priceSell;
    private Integer quantity;
    private String quality;
    private String description;
    private String aversPath;
    private String reversePath;

    private String series;
    private String statusSell;  //StatusSell odpowiada za to czy dany banknot został wystawiony na sprzedaż
}
