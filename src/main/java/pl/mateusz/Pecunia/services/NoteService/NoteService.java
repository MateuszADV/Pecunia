package pl.mateusz.Pecunia.services.NoteService;

import pl.mateusz.Pecunia.models.CountryCurrencyView;
import pl.mateusz.Pecunia.models.dtos.NoteCountryViewDto;
import pl.mateusz.Pecunia.models.dtos.NoteDto;
import pl.mateusz.Pecunia.models.dtos.NoteInfoViewDto;
import pl.mateusz.Pecunia.models.forms.ContinentCountryCurrencyNote;

import java.util.List;
import java.util.Set;

public interface NoteService {

    CountryCurrencyView countryCurrencyView(Long currencyId);

    Boolean saveNote(NoteDto noteDto, Long currencyId);

    List<NoteInfoViewDto> currencyNoteList(Long currencyId);

    ContinentCountryCurrencyNote continentCountryCuttencyNote(Long countryId);

    List<NoteCountryViewDto> CountryFromContinent(String continent);

    List<NoteCountryViewDto> countryInColection(String continent);

    List<NoteInfoViewDto> noteFromCountry(String countryEn);

    List<String> countryNoteForSell();
    List<NoteInfoViewDto> noteForSell(String countryEn);

}
