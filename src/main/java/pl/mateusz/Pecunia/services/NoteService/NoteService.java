package pl.mateusz.Pecunia.services.NoteService;

import pl.mateusz.Pecunia.models.CountryCurrencyView;

public interface NoteService {

    CountryCurrencyView countryCurrencyView(Long currencyId);
}
