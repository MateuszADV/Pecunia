package pl.mateusz.Pecunia.countryService;

import pl.mateusz.Pecunia.models.dtos.CountryDtoList;
import pl.mateusz.Pecunia.models.forms.CountryViewList;

public interface CountryService {

    CountryViewList countryViewList();

    CountryDtoList countryDtoList();

}
