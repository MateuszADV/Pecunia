package pl.mateusz.Pecunia.controllers.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mateusz.Pecunia.models.repositories.CodeParamRepository;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.services.HomeService.HomeService;
import pl.mateusz.Pecunia.services.countryService.CountryService;
import pl.mateusz.Pecunia.services.exchangeService.ExchangeService;
import pl.mateusz.Pecunia.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HomeController {


    private CountryService countryService;
    private CountryRepository countryRepository;
    private CurrencyRepository currencyRepository;
    private ExchangeService exchangeService;
    private CodeParamRepository codeParamRepository;
    private HomeService homeService;

    public HomeController(CountryService countryService, CountryRepository countryRepository,
                          CurrencyRepository currencyRepository, ExchangeService exchangeService,
                          CodeParamRepository codeParamRepository, HomeService homeService) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.exchangeService = exchangeService;
        this.codeParamRepository = codeParamRepository;
        this.homeService = homeService;
    }

    @GetMapping("/login")
    public String getlogin( ){

        return "login";
    }

    @GetMapping(value = {"Pecunia","/"})
    public String getIndex(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

        try {
            List<String > codeList = homeService.currencyCode();
            modelMap.addAttribute("exchange", true);
            modelMap.addAttribute("exchangeRate",exchangeService.exchange(codeList));
        }catch (Exception e) {
            modelMap.addAttribute("exchange", false);
            modelMap.addAttribute("error", e.getMessage());
        }

        return "index";
    }

    @ResponseBody
    @GetMapping("/error")
    public String getError() {

        return "<h1>BRAK STRONY BŁĄD 404</h1>";
    }

    @PostMapping(value = {"/Pecunia/currency/showJson","/currency/showJson"})
    public String getShowJson(@RequestParam(value = "countryId") Long countryId, ModelMap modelMap) {

        modelMap.addAttribute("Gson", JsonUtils.gsonPretty(countryService.countryJson(countryId)));

        return "showJson";
    }

//    @GetMapping("/test")
//    @ResponseBody
//    public String getTest() {
//        List<Country> countCountries = countryRepository.CountryOn();
//        System.out.println(countCountries);
//        return "test";
//    }
}
