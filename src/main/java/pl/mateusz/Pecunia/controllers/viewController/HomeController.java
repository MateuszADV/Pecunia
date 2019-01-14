package pl.mateusz.Pecunia.controllers.viewController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.countryService.CountryService;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.dtos.CountryJsonDto;
import pl.mateusz.Pecunia.models.dtos.CurrencyDto;
import pl.mateusz.Pecunia.models.forms.ContinentCountryCurrencysResponse;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {


    private CountryService countryService;
    private CountryRepository countryRepository;
    private CurrencyRepository currencyRepository;

    @Autowired
    public HomeController(CountryService countryService, CountryRepository countryRepository, CurrencyRepository currencyRepository) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
    }

    @GetMapping("/login")
    public String getlogin(){

        return "login";
    }

    @ResponseBody
    @GetMapping("/error")
    public String getError(){

        return "<h1>BRAK DOSTEPU</h1";
    }

    @PostMapping("/showJson")
    public String getShowJson(@RequestParam(value = "countryId") Long countryId, ModelMap modelMap) {
        Country country = countryRepository.findById(countryId).get();
        List<Currency> currencyList = currencyRepository.findByCountry_IdOrderByDataExchangeDesc(countryId);
        CountryJsonDto countryJsonDto = new ModelMapper().map(country, CountryJsonDto.class);

        List<CurrencyDto> currencyDtoList = new ArrayList<>();

        for (Currency currency : currencyList) {
            currencyDtoList.add(new ModelMapper().map(currency, CurrencyDto.class));
        }

       countryJsonDto.setCurrencys(currencyDtoList);

//        System.out.println(JsonUtils.gsonPretty(countryJsonDto));
//         System.out.println(json);
//       modelMap.addAttribute("Gson", json);
        modelMap.addAttribute("Gson", JsonUtils.gsonPretty(countryJsonDto));

        return "showJson";
    }
}
