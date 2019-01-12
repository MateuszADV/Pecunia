package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContinentCountryCurrencysResponse {
    List<ContinentCountrys> continents;
}
