package pl.mateusz.Pecunia.models.forms;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import pl.mateusz.Pecunia.models.CountryCurrencyView;

import java.util.List;

@Data
public class CountryViewList {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CountryCurrencyView> CountryList;
}
