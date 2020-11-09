package pl.mateusz.Pecunia.controllers.viewController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mateusz.Pecunia.controllers.Constans;
import pl.mateusz.Pecunia.models.Coin;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.dtos.CoinDto;
import pl.mateusz.Pecunia.models.forms.enums.ImgTypeEnum;
import pl.mateusz.Pecunia.models.forms.enums.MakingEnum;
import pl.mateusz.Pecunia.models.forms.enums.StatusEnum;
import pl.mateusz.Pecunia.models.forms.enums.StatusSellEnum;
import pl.mateusz.Pecunia.models.repositories.CoinRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.services.coinService.CoinService;
import pl.mateusz.Pecunia.services.countryService.CountryService;
import pl.mateusz.Pecunia.utils.JsonUtils;
import pl.mateusz.Pecunia.utils.PaternSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class CoinController {

    private CountryService countryService;
    private PaternSet paternSet;
    private CurrencyRepository currencyRepository;
    private CoinRepository coinRepository;

    @Autowired
    public CoinController(CountryService countryService, PaternSet paternSet, CurrencyRepository currencyRepository, CoinRepository coinRepository) {
        this.countryService = countryService;
        this.paternSet = paternSet;
        this.currencyRepository = currencyRepository;
        this.coinRepository = coinRepository;
    }

    @GetMapping(value = {"/Pecunia/addCoin", "/addCoin"})
    public String getAddCoinSelectCountry(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        modelMap.addAttribute("countrys", countryService.countryDtoList().getCountryDtoList());
        modelMap.addAttribute("title","Wybierz państwo");

        if (request.getRequestURI().contains("Coin")) {
            paternSet.patternSet("Coin");
        }

        System.out.println(paternSet.getPatternSet());

        return "banknotes";
    }

    @PostMapping(value = {"/Pecunia/addNewCoin", "/addNewCoin"})
    public String postAddNewCoin(@ModelAttribute("coinDto")@Valid CoinDto coinDto,
                                 BindingResult result,
                                 @RequestParam("currencyId") Long currencyId,
                                 ModelMap modelMap) {
        modelMap.addAttribute("button", Constans.BUTTON_ADD_COIN);
        CoinDto coinDto1 = new CoinDto();

        coinDto1.setDateBuyNote(Date.valueOf(LocalDate.now()));
        modelMap.addAttribute("coinDto", coinDto1);
        modelMap.addAttribute("currencyId", currencyId);

        if (result.hasErrors()) {
            modelMap.addAttribute("error", "Wypełnij poprawnie pole");
            modelMap.addAttribute("button", Constans.BUTTON_ADD_COIN);
            System.out.println("!!!!!!!!!!POWINIEN BYC BLAD!!!!!!!!!!!!!!");
            coinDto.setDateBuyNote(Date.valueOf(LocalDate.now()));
            coinDtoError(coinDto);
            modelMap.addAttribute("coinDto", coinDto);
            modelMap.addAttribute("currencyId", currencyId);

            System.out.println(JsonUtils.gsonPretty(coinDto));

            EnumForm(modelMap);
            return "coin";
        }

        Optional<Currency> currency = currencyRepository.findById(currencyId);
        Coin coin = new ModelMapper().map(coinDto1, Coin.class);
        coin.setCurrency(currency.get());
        System.out.println(JsonUtils.gsonPretty(coin.getDateBuyNote()));
        coinRepository.save(coin);

        EnumForm(modelMap);
        System.out.println(coinDto);
        System.out.println(JsonUtils.gsonPretty(coinDto));
        System.out.println("Powinno byc ID waluty - " + currencyId);
        return "coin";
    }

    private void EnumForm(ModelMap modelMap) {
        modelMap.addAttribute("status", StatusEnum.values());
        modelMap.addAttribute("statusSell", StatusSellEnum.values());
        modelMap.addAttribute("making", MakingEnum.values());
        modelMap.addAttribute("imgType", ImgTypeEnum.values());
    }

    private CoinDto coinDtoError(CoinDto coinDto) {
        if (coinDto.getDiameter() == null) {
            coinDto.setDiameter(0.00);
        } if (coinDto.getThickness() == null) {
            coinDto.setThickness(0.00);
        } if (coinDto.getPriceBuy() == null) {
            coinDto.setPriceBuy(0.00);
        } if (coinDto.getPriceSell() == null) {
            coinDto.setPriceSell(0.00);
        }
        return coinDto;
    }
}
