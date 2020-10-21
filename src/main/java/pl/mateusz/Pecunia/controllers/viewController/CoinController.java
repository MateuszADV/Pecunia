package pl.mateusz.Pecunia.controllers.viewController;

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
import pl.mateusz.Pecunia.models.dtos.CoinDto;
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
    private PaternSet paternSet;

    @Autowired
    public CoinController(CountryService countryService, PaternSet paternSet) {
        this.countryService = countryService;
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
            System.out.println("!!!!!!!!!!POWINIEN BYC BLAD!!!!!!!!!!!!!!");
            coinDto1.setDateBuyNote(Date.valueOf(LocalDate.now()));
            modelMap.addAttribute("coinDto", coinDto1);
            modelMap.addAttribute("currencyId", currencyId);

            System.out.println(JsonUtils.gsonPretty(coinDto));
            return "coin";
        }

        System.out.println(coinDto);
        System.out.println(JsonUtils.gsonPretty(coinDto));
        System.out.println("Powinno byc ID waluty - " + currencyId);
        return "coin";
    }
}
