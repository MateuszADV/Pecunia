package pl.mateusz.Pecunia.models.forms;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONPropertyName;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoldRate {
    String dataRate;
    Double priceForGram;
    Double priceForOunce;
    Double change;
}
