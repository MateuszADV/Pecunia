package pl.mateusz.Pecunia.controllers.viewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.countryService.CountryServiceImpl;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;

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

        return "country";
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

    @GetMapping("/currency/{countryEn}")
    public String getCurrency(@PathVariable String countryEn, ModelMap modelMap) {
        modelMap.addAttribute("currency", new Currency());
        Country country = countryRepository.findByCountryEn(countryEn);
        modelMap.addAttribute("countryEn", country.getCountryEn());
        modelMap.addAttribute("countryPl", country.getCountryPl().replace(" ", "_"));

        return "currency";
    }

    @PostMapping("/currency")
    public String postCurrency(@ModelAttribute("currency") Currency currency,
                               @RequestParam(value = "countryEn") String countryEn,
                               ModelMap modelMap) {
        Country country = countryRepository.findByCountryEn(countryEn);
        currency.setCountry(country);
        currencyRepository.save(currency);
        modelMap.addAttribute("countryEn", countryEn);

        modelMap.addAttribute("countryPl", country.getCountryPl().replace(" ", "_"));
        return "currency";
    }
}
