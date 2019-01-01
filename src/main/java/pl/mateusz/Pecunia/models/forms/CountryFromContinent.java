package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mateusz.Pecunia.models.dtos.CountryViewDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CountryFromContinent {
    private String continent;
    private List<CountryViewDto> countryFromContinent;
}
