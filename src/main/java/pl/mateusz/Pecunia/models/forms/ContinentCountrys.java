package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ContinentCountrys {
    private String continent;
    List<CountryOfCurrency> countrys;
}
