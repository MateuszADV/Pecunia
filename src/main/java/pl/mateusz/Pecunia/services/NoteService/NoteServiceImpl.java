package pl.mateusz.Pecunia.services.NoteService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import pl.mateusz.Pecunia.models.CountryCurrencyView;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.Note;
import pl.mateusz.Pecunia.models.NoteInfoView;
import pl.mateusz.Pecunia.models.dtos.NoteDto;
import pl.mateusz.Pecunia.models.dtos.NoteInfoViewDto;
import pl.mateusz.Pecunia.models.repositories.CountryCurrencyViewRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.models.repositories.NoteInfoViewRepository;
import pl.mateusz.Pecunia.models.repositories.NoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private CountryCurrencyViewRepository countryCurrencyViewRepository;
    private NoteRepository noteRepository;
    private CurrencyRepository currencyRepository;
    private NoteInfoViewRepository noteInfoViewRepository;

    @Autowired
    public NoteServiceImpl(CountryCurrencyViewRepository countryCurrencyViewRepository, NoteRepository noteRepository,
                           CurrencyRepository currencyRepository, NoteInfoViewRepository noteInfoViewRepository) {
        this.countryCurrencyViewRepository = countryCurrencyViewRepository;
        this.noteRepository = noteRepository;
        this.currencyRepository = currencyRepository;
        this.noteInfoViewRepository = noteInfoViewRepository;
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

}
