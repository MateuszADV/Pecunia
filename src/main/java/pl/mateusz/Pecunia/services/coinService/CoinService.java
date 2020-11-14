package pl.mateusz.Pecunia.services.coinService;

import pl.mateusz.Pecunia.models.dtos.CoinDto;
import pl.mateusz.Pecunia.models.forms.CurrencyCoinDto;

public interface CoinService {

    Boolean saveCoin(CoinDto coinDto, Long currencyId);
    CurrencyCoinDto currencyAndListCoin(Long currencyId);
}
