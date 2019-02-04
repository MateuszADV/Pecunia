package pl.mateusz.Pecunia.services.NoteService;

import pl.mateusz.Pecunia.models.CountryCurrencyView;
import pl.mateusz.Pecunia.models.dtos.NoteDto;
import pl.mateusz.Pecunia.models.dtos.NoteInfoViewDto;

import java.util.List;

public interface NoteService {

    CountryCurrencyView countryCurrencyView(Long currencyId);

    Boolean saveNote(NoteDto noteDto, Long currencyId);

    List<NoteInfoViewDto> currencyNoteList(Long currencyId);

}
