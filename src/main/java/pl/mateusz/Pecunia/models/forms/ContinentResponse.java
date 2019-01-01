package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContinentResponse {
    List<CountryFromContinent> continents;
}
