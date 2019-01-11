package pl.mateusz.Pecunia.controllers.viewController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.countryService.CountryServiceImpl;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.dtos.CountryDto;
import pl.mateusz.Pecunia.models.dtos.CurrencyDto;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CountryController {

    private CountryRepository countryRepository;
    private CurrencyRepository currencyRepository;
    private CountryServiceImpl countryService;

    @Autowired
    public CountryController(CountryRepository countryRepository, CurrencyRepository currencyRepository, CountryServiceImpl countryService) {
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.countryService = countryService;
    }

    @GetMapping("/country")
    public String getCountry(ModelMap modelMap) {
        modelMap.addAttribute("country", new Country());
        return countryList(modelMap);
    }

    @PostMapping("/country")
    public String postCountry(@ModelAttribute("country") Country country) {
        countryRepository.save(country);
        return "redirect:/country";
    }

    @GetMapping("/editCountry")
    public String getCountryView(ModelMap modelMap) {

        modelMap.addAttribute("countryList", countryService.countryDtoList().getCountryDtoList());

        return "editCountry";
    }

    @GetMapping("/country/{countryEn}")
    public String getCountryEn(@PathVariable String countryEn, ModelMap modelMap) {
        Country country = countryRepository.findByCountryEn(countryEn);
        modelMap.addAttribute("country", country);

        return countryList(modelMap);
    }

    private String countryList(ModelMap modelMap) {
        List<Country> countries = countryRepository.findAllByOrderById();
        List<CountryDto> countryDtoList = new ArrayList<>();

        for (Country country : countries) {
            country.setCountryPl(country.getCountryPl().replace(" ", "_"));
            countryDtoList.add(new ModelMapper().map(country, CountryDto.class));
        }

        modelMap.addAttribute("countryList", countryDtoList);
        return "country";
    }

    @GetMapping("/currency/{countryEn}")
    public String getCurrency(@PathVariable String countryEn, ModelMap modelMap) {
        modelMap.addAttribute("currency", new Currency());
        Country country = countryRepository.findByCountryEn(countryEn);

        return currencyDate(modelMap, country);
    }

    @PostMapping("/currency")
    public String postCurrency(@ModelAttribute("currency") Currency currency,
                               @RequestParam(value = "countryEn") String countryEn,
                               @RequestParam(value = "edit") Integer edit,
                               ModelMap modelMap) {
        Country country = countryRepository.findByCountryEn(countryEn);
        currency.setCountry(country);

         currencyRepository.save(currency);

        modelMap.addAttribute("countryEn", country.getCountryEn());
        modelMap.addAttribute("countryPl", country.getCountryPl().replace(" ", "_"));
        modelMap.addAttribute("currencyList", countryService.curencyFromCountryId(country.getId()));

        if (edit == null) {
            modelMap.addAttribute("currency", new Currency());
        }
        System.out.println("---Powinna byćiczba: " + edit);
        System.out.println("Powinno bić id waluty: "  + currency.getId());

        return "currency";
    }

    @GetMapping("/currencyEdit/{currencyId}")
    public String postCurrency(@PathVariable Long currencyId, ModelMap modelMap) {
        Optional<Currency> currency = currencyRepository.findById(currencyId);
        Country country = currency.get().getCountry();
        modelMap.addAttribute("currency", currency);

        modelMap.addAttribute("edit", 1);

//        if (currency.isPresent()) {
//            CurrencyDto currencyDto = (new ModelMapper().map(currency.get(), CurrencyDto.class));
//        }

        return currencyDate(modelMap, country);
    }

    private String currencyDate(ModelMap modelMap, Country country) {
        modelMap.addAttribute("countryEn", country.getCountryEn());
        modelMap.addAttribute("countryPl", country.getCountryPl().replace(" ", "_"));
        modelMap.addAttribute("currencyList", countryService.curencyFromCountryId(country.getId()));
        return "currency";
    }
}
