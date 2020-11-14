package pl.mateusz.Pecunia.controllers.viewController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.controllers.Constans;
import pl.mateusz.Pecunia.models.Coin;
import pl.mateusz.Pecunia.models.dtos.CoinDto;
import pl.mateusz.Pecunia.models.dtos.NoteDto;
import pl.mateusz.Pecunia.models.forms.enums.ImgTypeEnum;
import pl.mateusz.Pecunia.models.forms.enums.MakingEnum;
import pl.mateusz.Pecunia.models.forms.enums.StatusEnum;
import pl.mateusz.Pecunia.models.forms.enums.StatusSellEnum;
import pl.mateusz.Pecunia.models.repositories.CoinRepository;
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


@Controller
public class CoinController {

    private CountryService countryService;
    private CoinService coinService;
    private PaternSet paternSet;
    private CoinRepository coinRepository;
    private NoteService noteService;


    @Autowired
    public CoinController(CountryService countryService, CoinService coinService, PaternSet paternSet, CoinRepository coinRepository, NoteService noteService) {
        this.countryService = countryService;
        this.coinService = coinService;
        this.paternSet = paternSet;
        this.coinRepository = coinRepository;
        this.noteService = noteService;
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
}
