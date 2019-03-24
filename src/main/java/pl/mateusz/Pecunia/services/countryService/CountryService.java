package pl.mateusz.Pecunia.services.countryService;

import pl.mateusz.Pecunia.models.dtos.CountryDtoList;
import pl.mateusz.Pecunia.models.dtos.CountryJsonDto;
import pl.mateusz.Pecunia.models.dtos.CurrencyDto;
import pl.mateusz.Pecunia.models.dtos.NoteCountryViewDto;
import pl.mateusz.Pecunia.models.forms.*;

import java.util.List;

public interface CountryService {

    CountryViewList countryViewList();

    CountryDtoList countryDtoList();

    ContinentResponse continentResponse();

    ContinentResponse continentResponse(ContinentRequest request);

    List<CurrencyDto> currencyFromCountryId(Long currencyId);

    ContinentCountryCurrencysResponse continentCountryCurrencysResponse();

    ContinentCountryCurrencysResponse continentCountryCurrencysResponse(ContinentRequest request);

    CountryJsonDto countryJson(Long country_id);



}
