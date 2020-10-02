package pl.mateusz.Pecunia.models.forms.goldApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MetalPrice {

    private String date;
    private String timestamp;
    private String metalCod;
    private String metal;
    private String currency;
    private String exchange;
    private String symbol;
    private Double prevClosePrice;
    private Double openPrice;
    private Double price;
    private Double lowPrice;
    private Double highPrice;
    private String openTime;
    private Double ch;
    private Double chp;
    private Double ask;
    private Double bid;
}
