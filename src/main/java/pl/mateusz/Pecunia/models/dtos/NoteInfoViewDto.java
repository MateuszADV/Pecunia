package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteInfoViewDto {
    private Long noteId;
    private String continent;
    private String countryEn;
    private Long countryId;
    private Long currencyId;
    private String cod;
    private String currency;
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
    private String aversPath;
    private String reversePath;

    private String series;
    private String making;
    private Date dateBuyNote;
    private  String bought;
    private String statusSell;  //StatusSell odpowiada za to czy dany banknot został wystawiony na sprzedaż
}
