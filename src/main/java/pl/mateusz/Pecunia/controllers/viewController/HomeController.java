package pl.mateusz.Pecunia.controllers.viewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mateusz.Pecunia.services.countryService.CountryService;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.services.exchangeService.ExchangeService;
import pl.mateusz.Pecunia.utils.JsonUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class HomeController {


    private CountryService countryService;
    private CountryRepository countryRepository;
    private CurrencyRepository currencyRepository;
    private ExchangeService exchangeService;

    @Autowired
    public HomeController(CountryService countryService, CountryRepository countryRepository, CurrencyRepository currencyRepository, ExchangeService exchangeService) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.exchangeService = exchangeService;
    }

    @GetMapping("/login")
    public String getlogin( ){

        return "login";
    }

    @GetMapping("/")
    public String getIndex(ModelMap modelMap) {

        List<String > codeList = new ArrayList<>();
        codeList.add("USD");
        codeList.add("EUR");
        codeList.add("CHF");
        codeList.add("GBP");
        modelMap.addAttribute("exchangeRate",exchangeService.exchange(codeList));
        return "index";
    }

    @ResponseBody
    @GetMapping("/error")
    public String getError() {

        return "<h1>BRAK DOSTEPU</h1";
    }

    @PostMapping("/showJson")
    public String getShowJson(@RequestParam(value = "countryId") Long countryId, ModelMap modelMap) {

        modelMap.addAttribute("Gson", JsonUtils.gsonPretty(countryService.countryJson(countryId)));

        return "showJson";
    }
}
