package pl.mateusz.Pecunia.countryService;

import pl.mateusz.Pecunia.models.dtos.CountryDtoList;
import pl.mateusz.Pecunia.models.dtos.CurrencyDto;
import pl.mateusz.Pecunia.models.forms.*;

import java.util.List;

public interface CountryService {

    CountryViewList countryViewList();

    CountryDtoList countryDtoList();

    ContinentResponse continentResponse();

    ContinentResponse continentResponse(ContinentRequest request);

    List<CurrencyDto> curencyFromCountryId(Long currencyId);

    ContinentCountryCurrencysResponse continentCountryCurrencysResponse();

    ContinentCountryCurrencysResponse continentCountryCurrencysResponse(ContinentRequest request);

}
