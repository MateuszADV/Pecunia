package pl.mateusz.Pecunia.services.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.CountryCurrencyView;
import pl.mateusz.Pecunia.models.repositories.CountryCurrencyViewRepository;

@Service
public class NoteServiceImpl implements NoteService {

    private CountryCurrencyViewRepository countryCurrencyViewRepository;

    @Autowired
    public NoteServiceImpl(CountryCurrencyViewRepository countryCurrencyViewRepository) {
        this.countryCurrencyViewRepository = countryCurrencyViewRepository;
    }

    @Override
    public CountryCurrencyView countryCurrencyView(Long currencyId) {
        CountryCurrencyView countryCurrencyView = countryCurrencyViewRepository.findByCurrencyId(currencyId);

        return countryCurrencyView;
    }
}
