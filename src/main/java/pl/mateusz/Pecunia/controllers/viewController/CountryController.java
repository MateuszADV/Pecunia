package pl.mateusz.Pecunia.controllers.viewController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.services.countryService.CountryServiceImpl;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.dtos.CountryDto;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;

import javax.validation.Valid;
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

    @GetMapping(value = {"/Pecunia/country","/country"})
    public String getCountry(ModelMap modelMap) {
        modelMap.addAttribute("countryDto", new CountryDto());
        modelMap.addAttribute("edit", false);
        modelMap.addAttribute("buton", "Dodaj Państwo");
        return countryList(modelMap);
    }

    @PostMapping(value = {"/Pecunia/country","/country"})
    public String postCountry(@ModelAttribute("countryDto")@Valid CountryDto countryDto, BindingResult result,
                              @RequestParam Boolean edit,
                              ModelMap modelMap) {

        Country countryFind = countryRepository.findByCountryEn(countryDto.getCountryEn());

        if (result.hasErrors()) {
            modelMap.addAttribute("error", "Wypełnij poprawnie pole");
            modelMap.addAttribute("buton", "Dodaj Państwo");
            return countryList(modelMap);
        }

        if (countryFind != null && edit != true) {
            modelMap.addAttribute("countryExist", true);
            modelMap.addAttribute("countryInfo", "Państwo które chcez dodać już jest w bazie");
            modelMap.addAttribute("buton", "Dodaj Państwo");
            modelMap.addAttribute("country", new Country());
            return countryList(modelMap);
        }

        Country country = (new ModelMapper().map(countryDto, Country.class));

        countryRepository.save(country);
//        System.out.println("Zapis do bazy");
        return "redirect:/country";
    }

    @GetMapping(value = {"/Pecunia/country/{countryEn}","/country/{countryEn}"})
    public String getCountryEn(@PathVariable String countryEn, ModelMap modelMap) {
        Country country = countryRepository.findByCountryEn(countryEn);
        CountryDto countryDto = (new ModelMapper().map(country, CountryDto.class));
        modelMap.addAttribute("countryDto", countryDto);
        modelMap.addAttribute("edit", true);
        modelMap.addAttribute("buton", "Zapisz Zmiany");
        return countryList(modelMap);
    }

    private String countryList(ModelMap modelMap) {
        List<Country> countries = countryRepository.findAllByOrderById();
        List<CountryDto> countryDtoList = new ArrayList<>();

        for (Country country : countries) {
            country.setCountryPl(country.getCountryPl());
            countryDtoList.add(new ModelMapper().map(country, CountryDto.class));
        }

        modelMap.addAttribute("countryList", countryDtoList);
        return "country";
    }

    @GetMapping(value = {"/Pecunia/currency","/currency"})
    public String getCurrency1(ModelMap modelMap) {
        modelMap.addAttribute("countrys", countryService.countryDtoList().getCountryDtoList());
        modelMap.addAttribute("currencyTrue", false);
        return "currency";
    }

    @GetMapping(value = {"/Pecunia/currency/{countryEn}","/currency/{countryEn}"})
    public String getCurrency(@PathVariable String countryEn, ModelMap modelMap) {
        modelMap.addAttribute("currency", new Currency());
        Country country = countryRepository.findByCountryEn(countryEn);

        return currencyDate(modelMap, country);
    }

    @PostMapping(value = {"/Pecunia/currency","/currency"})
    public String postCurrency(@ModelAttribute("currency") Currency currency,
                               @RequestParam(value = "countryEn") String countryEn,
                               @RequestParam(value = "edit") Integer edit,
                               ModelMap modelMap) {
        Country country = countryRepository.findByCountryEn(countryEn);
        currency.setCountry(country);

         currencyRepository.save(currency);

        modelMap.addAttribute("countryEn", country.getCountryEn());
        modelMap.addAttribute("countryPl", country.getCountryPl());
        modelMap.addAttribute("currencyList", countryService.currencyFromCountryId(country.getId()));
        modelMap.addAttribute("countryId", currency.getCountry().getId()); //Wysyła Id państwa potrzebnego do wyswietlenia Jsona

        if (edit == null) {
            modelMap.addAttribute("currency", new Currency());
        }
        return "currency";
    }

    @GetMapping(value = {"/Pecunia/currencyEdit/{currencyId}","/currencyEdit/{currencyId}"})
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
        modelMap.addAttribute("countryPl", country.getCountryPl());
        modelMap.addAttribute("currencyList", countryService.currencyFromCountryId(country.getId()));
        modelMap.addAttribute("countryId", country.getId());
        return "currency";
    }
}
