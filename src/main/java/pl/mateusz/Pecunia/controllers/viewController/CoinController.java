package pl.mateusz.Pecunia.controllers.viewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mateusz.Pecunia.services.coinService.CoinService;
import pl.mateusz.Pecunia.services.countryService.CountryService;
import pl.mateusz.Pecunia.utils.PaternSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
