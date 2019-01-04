package pl.mateusz.Pecunia.countryService;

import pl.mateusz.Pecunia.models.dtos.CountryDtoList;
import pl.mateusz.Pecunia.models.dtos.CurrencyDto;
import pl.mateusz.Pecunia.models.forms.ContinentRequest;
import pl.mateusz.Pecunia.models.forms.ContinentResponse;
import pl.mateusz.Pecunia.models.forms.CountryViewList;

import java.util.List;

public interface CountryService {

    CountryViewList countryViewList();

    CountryDtoList countryDtoList();

    ContinentResponse continentResponse();

    ContinentResponse continentResponse(ContinentRequest request);

    List<CurrencyDto> curencyFromCountryId(Long currencyId);

}
