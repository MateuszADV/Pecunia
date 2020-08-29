package pl.mateusz.Pecunia.models.forms.goldApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class MetalApi {

    private String timestamp;
    private String metalCod;
    private String metal;
    private String currency;
    private String exchange;
    private Double prevClosePrice;
    private Double price;
    private Double ch;
    private Double chp;
}
