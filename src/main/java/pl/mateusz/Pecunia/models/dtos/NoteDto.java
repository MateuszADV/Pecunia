package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String  reversePath;
}
