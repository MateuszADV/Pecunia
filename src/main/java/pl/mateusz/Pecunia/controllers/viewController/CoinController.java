package pl.mateusz.Pecunia.controllers.viewController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.controllers.Constans;
import pl.mateusz.Pecunia.models.Coin;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.dtos.CoinDto;
import pl.mateusz.Pecunia.models.dtos.CountryDto;
import pl.mateusz.Pecunia.models.forms.enums.ImgTypeEnum;
import pl.mateusz.Pecunia.models.forms.enums.MakingEnum;
import pl.mateusz.Pecunia.models.forms.enums.StatusEnum;
import pl.mateusz.Pecunia.models.forms.enums.StatusSellEnum;
import pl.mateusz.Pecunia.models.repositories.CoinRepository;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.services.NoteService.NoteService;
import pl.mateusz.Pecunia.services.coinService.CoinService;
import pl.mateusz.Pecunia.services.countryService.CountryService;
import pl.mateusz.Pecunia.utils.JsonUtils;
import pl.mateusz.Pecunia.utils.PaternSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
public class CoinController {

    private CoinRepository coinRepository;
    private CountryRepository countryRepository;
    private CountryService countryService;

    private CoinService coinService;
    private NoteService noteService;

    private PaternSet paternSet;

    @Autowired
    public CoinController(CoinRepository coinRepository, CountryRepository countryRepository, CountryService countryService, CoinService coinService, NoteService noteService, PaternSet paternSet) {
        this.coinRepository = coinRepository;
        this.countryRepository = countryRepository;
        this.countryService = countryService;
        this.coinService = coinService;
        this.noteService = noteService;
        this.paternSet = paternSet;
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
            modelMap.addAttribute("countryCurrency", noteService.countryCurrencyView(currencyId));
            modelMap.addAttribute("coinList", coinService.currencyAndListCoin(currencyId).getCoinDtosList());
            System.out.println("!!!!!!!!!!POWINIEN BYC BLAD!!!!!!!!!!!!!!");
            coinDto.setDateBuyNote(Date.valueOf(LocalDate.now()));
            coinDtoError(coinDto);
            modelMap.addAttribute("coinDto", coinDto);
            modelMap.addAttribute("currencyId", currencyId);

            System.out.println(JsonUtils.gsonPretty(coinDto));

            modelMap.addAttribute("save", "Coś poszło nie tak :(!!!)");
            modelMap.addAttribute("statusSave", false);
            EnumForm(modelMap);
            return "coin";
        }

        try {
            coinService.saveCoin(coinDto, currencyId);

            EnumForm(modelMap);
            System.out.println(coinDto);
            System.out.println(JsonUtils.gsonPretty(coinDto));
            System.out.println("Powinno byc ID waluty - " + currencyId);

            modelMap.addAttribute("save", "Moneta została dodana do bazy");
            modelMap.addAttribute("button", Constans.BUTTON_ADD_NOTE);
            modelMap.addAttribute("statusSave", true);
            modelMap.addAttribute("countryCurrency", noteService.countryCurrencyView(currencyId));
            modelMap.addAttribute("coinList", coinService.currencyAndListCoin(currencyId).getCoinDtosList());
            Logger LOGGER = Logger.getLogger(NoteService.class.toString());
            LOGGER.log(Level.INFO, noteService.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modelMap.addAttribute("error_message", e.getMessage());
            return "index";
        }
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

//    private Coin coinMaper(Coin coin, CoinDto coinDto) {
//
//        coin.setDateBuyNote(coinDto.getDateBuyNote());
//        coin.setNameCurrency(coinDto.getNameCurrency());
//        coin.setSignatureCode(coinDto.getSignatureCode());
//        coin.setPriceBuy(coinDto.getPriceBuy());
//        coin.setPriceSell(coinDto.getPriceSell());
//        coin.setQuality(coinDto.getQuality());
//        coin.setStatus(coinDto.getStatus());
//        coin.setStatusSell(coinDto.getStatusSell());
//        coin.setDescription(coinDto.getDescription());
//        coin.setImgType(coinDto.getImgType());
//        coin.setAversPath(coinDto.getAversPath());
//        coin.setReversePath(coinDto.getReversePath());
//        return coin;
//    }

    @GetMapping(value = {"/Pecunia/coinEdit/{coinId}","/coinEdit/{coinId}"})
    public String getEditNote(@PathVariable Long coinId,
                              ModelMap modelMap) {

        try {
            EnumForm(modelMap);
            Coin coin = coinRepository.getOne(coinId);
            CoinDto coinDto = new ModelMapper().map(coin, CoinDto.class);
            modelMap.addAttribute("currencyId", coin.getCurrency().getId());
            System.out.println(JsonUtils.gsonPretty(coinDto));
            modelMap.addAttribute("coinDto", coinDto);
            modelMap.addAttribute("coinList", coinService.currencyAndListCoin(coin.getCurrency().getId()).getCoinDtosList());
            modelMap.addAttribute("countryCurrency", noteService.countryCurrencyView(coin.getCurrency().getId()));
            modelMap.addAttribute("button", Constans.BUTTON_SAVE_CHANGE);
            EnumForm(modelMap);
        }catch (Exception e){
            modelMap.addAttribute("errorId", coinId);
            return "error404";
        }
        return "/coin";
    }

    @GetMapping(value = {"/Pecunia/select_country_coin", "/select_country_coin"})
    public String getCountryCoinColection(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("countryTrue", true);

        List<Country> countryListCoin = countryRepository.countryCoin();
        List<CountryDto> countryDtoListCoin = new ArrayList<>();
        for (Country country : countryListCoin) {
            countryDtoListCoin.add(new ModelMapper().map(country, CountryDto.class));
        }
        modelMap.addAttribute("countrys", countryDtoListCoin);
        modelMap.addAttribute("countryFromContinent", "Wszystkie kontynenty");

        if (request.getRequestURI().contains("coin")) {
            paternSet.patternSet("Coin");
        }
        System.out.println("Powinien byc ustawiony patern COIN - " + paternSet.getPatternSet());

        System.out.println("powinna byćilośc elementów - " + countryListCoin.get(0).getCurrencies().get(0).getCoins().size());

        return "view_note";
//        return "index";
    }

}
