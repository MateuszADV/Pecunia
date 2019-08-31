package pl.mateusz.Pecunia.services.NoteService;

import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.controllers.Constans;
import pl.mateusz.Pecunia.models.*;
import pl.mateusz.Pecunia.models.dtos.*;
import pl.mateusz.Pecunia.models.forms.ContinentCountryCurrencyNote;
import pl.mateusz.Pecunia.models.forms.CountryCurrencyNote;
import pl.mateusz.Pecunia.models.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NoteServiceImpl implements NoteService {

    private CountryCurrencyViewRepository countryCurrencyViewRepository;
    private NoteRepository noteRepository;
    private CurrencyRepository currencyRepository;
    private NoteInfoViewRepository noteInfoViewRepository;
    private CountryRepository countryRepository;
    private NoteCountryViewRepository noteCountryViewRepository;

    public NoteServiceImpl(CountryCurrencyViewRepository countryCurrencyViewRepository, NoteRepository noteRepository,
                           CurrencyRepository currencyRepository, NoteInfoViewRepository noteInfoViewRepository,
                           CountryRepository countryRepository, NoteCountryViewRepository noteCountryViewRepository) {
        this.countryCurrencyViewRepository = countryCurrencyViewRepository;
        this.noteRepository = noteRepository;
        this.currencyRepository = currencyRepository;
        this.noteInfoViewRepository = noteInfoViewRepository;
        this.countryRepository = countryRepository;
        this.noteCountryViewRepository = noteCountryViewRepository;
    }

    @Override
    public CountryCurrencyView countryCurrencyView(Long currencyId) {
        CountryCurrencyView countryCurrencyView = countryCurrencyViewRepository.findByCurrencyId(currencyId);

        return countryCurrencyView;
    }

    @Override
    public Boolean saveNote(NoteDto noteDto, Long currencyId) {
        try {
            Optional<Currency> currency = currencyRepository.findById(currencyId);
            Note note = (new ModelMapper().map(noteDto, Note.class));
            note.setCurrency(currency.get());
            noteRepository.save(note);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<NoteInfoViewDto> currencyNoteList(Long currencyId) {
        List<NoteInfoView> noteInfoViewList = noteInfoViewRepository.findAllByCurrencyId(currencyId);
        return getNoteInfoViewDtos(noteInfoViewList);
    }

    @Override
    public  ContinentCountryCurrencyNote continentCountryCuttencyNote(Long countryId) {
        ContinentCountryCurrencyNote continentCountryCurrencyNote = new ContinentCountryCurrencyNote();
        List<CountryCurrencyNote> countryCurrencyNoteList = new ArrayList<>();
        List<CurrencyNoteDto> currencyNoteDtoList = new ArrayList<>();

        Country country = countryRepository.findById(countryId).get();

        List<Currency> currencyList = currencyRepository.findByCountry_IdOrderByDataExchangeDesc(countryId);
        for (Currency currency : currencyList) {
            CurrencyNoteDto currencyNoteDto = new ModelMapper().map(currency, CurrencyNoteDto.class);
            currencyNoteDto.setBanknotes(noteJsonDtoList(currency.getId()));

            currencyNoteDtoList.add(currencyNoteDto);
        }
        CountryCurrencyNote countryCurrencyNote = new CountryCurrencyNote();
        countryCurrencyNote.setCountry(country.getCountryEn());
        countryCurrencyNote.setAlfa3(country.getAlfa3());
        countryCurrencyNote.setCurrencys(currencyNoteDtoList);

        countryCurrencyNoteList.add(countryCurrencyNote);

        continentCountryCurrencyNote.setContinent(country.getContinent());
        continentCountryCurrencyNote.setCountryList(countryCurrencyNoteList);
        return continentCountryCurrencyNote;
    }

    private List<NoteJsonDto> noteJsonDtoList(Long currencyId) {
        List<Note> noteList = noteRepository.findByCurrencyId(currencyId);
        List<NoteJsonDto> noteJsonDtoList = new ArrayList<>();
        for (Note note : noteList) {
            noteJsonDtoList.add(new ModelMapper().map(note, NoteJsonDto.class));
        }
        return noteJsonDtoList;
    }

    @Override
    public List<NoteCountryViewDto> CountryFromContinent(String continent) {
        List<NoteCountryView> noteCountryViewList = noteCountryViewRepository.findAllByContinent(continent);
        List<NoteCountryViewDto> noteCountryViewDtoList = new ArrayList<>();

        for (NoteCountryView noteCountryView : noteCountryViewList) {
            noteCountryViewDtoList.add(new ModelMapper().map(noteCountryView, NoteCountryViewDto.class));
        }

        return noteCountryViewDtoList;
    }

    @Override
    public List<NoteCountryViewDto> countryInColection(String continent) {
        Set<NoteCountryView> noteCountryViewList = noteCountryViewRepository.countryListColection(continent);
        return countryFromContinentSet(noteCountryViewList);
    }

    private List<NoteCountryViewDto> countryFromContinentSet(Set<NoteCountryView> noteCountryViewList) {
        List<NoteCountryViewDto> noteCountryViewDtoList = new ArrayList<>();

        for (NoteCountryView noteCountryView : noteCountryViewList) {
            noteCountryViewDtoList.add(new ModelMapper().map(noteCountryView, NoteCountryViewDto.class));
            System.out.println(noteCountryView);
        }
        return noteCountryViewDtoList;
    }


    @Override
    public List<NoteInfoViewDto> noteFromCountry(String countryEn) {
        List<NoteInfoView> noteInfoViewList = noteInfoViewRepository.findAllNoteCountry(countryEn);
//        List<NoteInfoViewDto> noteInfoViewDtoList = new ArrayList<>();
//        for (NoteInfoView noteInfoView : noteInfoViewList) {
//            noteInfoViewDtoList.add(new ModelMapper().map(noteInfoView, NoteInfoViewDto.class));
//        }
//
//        return noteInfoViewDtoList;
        return getNoteInfoViewDtos(noteInfoViewList);
    }

    //Banknoty na sprzedaz

    @Override
    public List<String> countryNoteForSell(String status) {
        List<String> countyNoteForSell = noteInfoViewRepository.countryNoteForSell(status);

        return countyNoteForSell;
    }

    @Override
    public List<NoteInfoViewDto> noteForSell(String countryEn, String status) {
        List<NoteInfoView> noteInfoViewList = noteInfoViewRepository.NoteForSell(countryEn, status);
        return getNoteInfoViewDtos(noteInfoViewList);
    }

    private List<NoteInfoViewDto> getNoteInfoViewDtos(List<NoteInfoView> noteInfoViewList) {
        List<NoteInfoViewDto> noteInfoViewDtoList = new ArrayList<>();

        for (NoteInfoView noteInfoView : noteInfoViewList) {
            noteInfoViewDtoList.add(new ModelMapper().map(noteInfoView, NoteInfoViewDto.class));
        }
        return noteInfoViewDtoList;
    }
}
