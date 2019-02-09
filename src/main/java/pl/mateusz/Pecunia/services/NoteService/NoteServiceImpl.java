package pl.mateusz.Pecunia.services.NoteService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.*;
import pl.mateusz.Pecunia.models.dtos.*;
import pl.mateusz.Pecunia.models.forms.ContinentCountryCurrencyNote;
import pl.mateusz.Pecunia.models.forms.CountryCurrencyNote;
import pl.mateusz.Pecunia.models.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private CountryCurrencyViewRepository countryCurrencyViewRepository;
    private NoteRepository noteRepository;
    private CurrencyRepository currencyRepository;
    private NoteInfoViewRepository noteInfoViewRepository;
    private CountryRepository countryRepository;

    @Autowired
    public NoteServiceImpl(CountryCurrencyViewRepository countryCurrencyViewRepository, NoteRepository noteRepository,
                           CurrencyRepository currencyRepository, NoteInfoViewRepository noteInfoViewRepository, CountryRepository countryRepository) {
        this.countryCurrencyViewRepository = countryCurrencyViewRepository;
        this.noteRepository = noteRepository;
        this.currencyRepository = currencyRepository;
        this.noteInfoViewRepository = noteInfoViewRepository;
        this.countryRepository = countryRepository;
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
        List<NoteInfoViewDto> noteInfoViewDtoList = new ArrayList<>();

        for (NoteInfoView noteInfoView : noteInfoViewList) {
            noteInfoViewDtoList.add(new ModelMapper().map(noteInfoView, NoteInfoViewDto.class));
        }
        return noteInfoViewDtoList;
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

}
