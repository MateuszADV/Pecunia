package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Items {
    private Long id;
    private Long noteId;
    private String countryEn;
    private String cod;
    private Double denomination;
    private String currencyName;
    private String noteDate;
    private Double priceBuy;
    private Double priceSell;
    private Double priceSellFinal;
    private Integer quantity;
    private String quality;
    private String making;
    private String description;
//    private String aversPath;
//    private String reversePath;
}
