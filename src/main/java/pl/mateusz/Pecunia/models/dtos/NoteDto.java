package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteDto {
    private Long id;
    private Double denomination;
    private String nameCurrency;
    private String noteDate;
    private Integer signatureCode;
    private Double priceBuy;
    private Double priceSell;
    private Integer quantity;
    private String quality;
    private String status;
    private String description;
    private String imgType;
    private String aversPath;
    private String reversePath;
    private String series;
    private String making;
//    @NotEmpty(message = "Pole nie może byc puste")
//    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "2000-01-01")
    private Date dateBuyNote;
    private  String bought;
    private String statusSell;  //StatusSell odpowiada za to czy dany banknot został wystawiony na sprzedaż
}
