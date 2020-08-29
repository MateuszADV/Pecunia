package pl.mateusz.Pecunia.models.forms.goldApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MetalPrice extends MetalApi {



    private String symbol;

    private Double openPrice;
    private Double lowPrice;
    private Double highPrice;
    private String  openTime;


    private Double ask;
    private Double bid;
}
