package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteJsonDto {

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
}
