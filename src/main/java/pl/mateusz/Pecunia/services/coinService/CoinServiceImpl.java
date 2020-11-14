package pl.mateusz.Pecunia.services.coinService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.Coin;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.dtos.CoinDto;
import pl.mateusz.Pecunia.models.forms.Coins;
import pl.mateusz.Pecunia.models.forms.CurrencyCoinDto;
import pl.mateusz.Pecunia.models.repositories.CoinRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService {

    private CurrencyRepository currencyRepository;
    private CoinRepository coinRepository;

    @Autowired
    public CoinServiceImpl(CurrencyRepository currencyRepository, CoinRepository coinRepository) {
        this.currencyRepository = currencyRepository;
        this.coinRepository = coinRepository;
    }

    @Override
    public Boolean saveCoin(CoinDto coinDto, Long currencyId) {
        try {
            Optional<Currency> currency = currencyRepository.findById(currencyId);
            Coin coin = (new ModelMapper().map(coinDto, Coin.class));
            coin.setCurrency(currency.get());
            coinRepository.save(coin);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CurrencyCoinDto currencyAndListCoin(Long currencyId) {
        Optional<Currency> currency = currencyRepository.findById(currencyId);
        CurrencyCoinDto currencyCoinDto = (new ModelMapper().map(currency.get(), CurrencyCoinDto.class));
        List<Coins> coins = new ArrayList<>();
        for (Coin coin : currency.get().getCoins()) {
            coins.add(new ModelMapper().map(coin, Coins.class));
        }
        currencyCoinDto.setCoinDtosList(coins);

//        System.out.println(JsonUtils.gsonPretty(currencyCoinDto));
        return currencyCoinDto;
    }
}
