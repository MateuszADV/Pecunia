package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteInfoViewDto {
    private Long noteId;
    private String continent;
    private String countryEn;
    private String cod;
    private String currency;
    private Double denomination;
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
}
