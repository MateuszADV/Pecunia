package pl.mateusz.Pecunia.controllers.viewController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.controllers.Constans;
import pl.mateusz.Pecunia.models.dtos.CurrencyDto;
import pl.mateusz.Pecunia.models.forms.enums.ContinentEnum;
import pl.mateusz.Pecunia.services.countryService.CountryServiceImpl;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.dtos.CountryDto;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.utils.PaternSet;

import javax.servlet.http.HttpServletRequest;
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
    private PaternSet paternSet;

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
        modelMap.addAttribute("button", Constans.BUTTON_ADD_COUNTRY);
        modelMap.addAttribute("continents", ContinentEnum.values());
        return countryList(modelMap);
    }

    @PostMapping(value = {"/Pecunia/country","/country"})
    public String postCountry(@ModelAttribute("countryDto")@Valid CountryDto countryDto, BindingResult result,
                              @RequestParam Boolean edit,
                              ModelMap modelMap) {

        Country countryFind = countryRepository.findByCountryEn(countryDto.getCountryEn());
        modelMap.addAttribute("continents", ContinentEnum.values());
        System.out.println("powinien BYc Zestaw banknotów: " +countryDto.getContinent());
        System.out.println(countryDto.getContinent().equals(ContinentEnum.SET_NOTES.getNamePl()));

        if (!countryDto.getContinent().equals(ContinentEnum.SET_NOTES.getNamePl())) {
            if (result.hasErrors()) {
                modelMap.addAttribute("error", "Wypełnij poprawnie pole");
                modelMap.addAttribute("button", Constans.BUTTON_ADD_COUNTRY);
                return countryList(modelMap);
            }
            if (countryFind != null && edit != true) {
                modelMap.addAttribute("countryExist", true);
                modelMap.addAttribute("countryInfo", "Państwo które chcez dodać już jest w bazie");
                modelMap.addAttribute("button", Constans.BUTTON_ADD_COUNTRY);
                modelMap.addAttribute("country", new Country());
                return countryList(modelMap);
            }
        }

        Country country = (new ModelMapper().map(countryDto, Country.class));

        countryRepository.save(country);
        System.out.println(countryDto);
        return "redirect:/country";
    }

    @GetMapping(value = {"/Pecunia/country/{countryEn}","/country/{countryEn}"})
    public String getCountryEn(@PathVariable String countryEn, ModelMap modelMap) {
        Country country = countryRepository.findByCountryEn(countryEn);
        CountryDto countryDto = (new ModelMapper().map(country, CountryDto.class));
        modelMap.addAttribute("continents", ContinentEnum.values());
        modelMap.addAttribute("countryDto", countryDto);
        modelMap.addAttribute("edit", true);
        modelMap.addAttribute("button", Constans.BUTTON_SAVE_CHANGE);
        return countryList(modelMap);
    }

    private String countryList(ModelMap modelMap) {
        List<Country> countries = countryRepository.findAllByOrderByCountryEn();
        List<CountryDto> countryDtoList = new ArrayList<>();

        for (Country country : countries) {
            country.setCountryPl(country.getCountryPl());
//            country.setCountryEn(country.getCountryEn().replace("_"," "));
            countryDtoList.add(new ModelMapper().map(country, CountryDto.class));
        }

        modelMap.addAttribute("countryList", countryDtoList);
        return "country";
    }

    /*
        Wybieranie państwa do dodanie waluty dla banknotów
     */
    @GetMapping(value = {"/Pecunia/currencyNote","/currencyNote"})
    public String getCurrencyNote(ModelMap modelMap, HttpServletRequest request) {
        modelMap.addAttribute("countrys", countryService.countryDtoList().getCountryDtoList());
        modelMap.addAttribute("currencyTrue", false);
        modelMap.addAttribute("chooseCountry", "Wyberz państwo");

        System.out.println("DODAWANIE waluty do państwa!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (request.getRequestURI().contains("Note")) {
            paternSet.patternSet("Note");
        }
        System.out.println(paternSet.getPatternSet());

        return "currency";
    }

    @GetMapping(value = {"/Pecunia/currency/{countryEn}","/currency/{countryEn}"})
    public String getCurrency(@PathVariable String countryEn, ModelMap modelMap) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setPattern(paternSet.getPatternSet().toUpperCase());
        modelMap.addAttribute("currencyDto", currencyDto);
        Country country = countryRepository.findByCountryEn(countryEn);
        modelMap.addAttribute("chooseCountry", "Dodaj walutę");

        modelMap.addAttribute("button", Constans.BUTTON_ADD_CURRENCY);

        //TODO poprawić dodawanie banknotów i monet do walut
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  " + paternSet.getPatternSet());

        return currencyDate(modelMap, country, paternSet.getPatternSet());
    }

    @PostMapping(value = {"/Pecunia/currency","/currency"})
    public String postCurrency(@ModelAttribute("currencyDto") CurrencyDto currencyDto,
                               @RequestParam(value = "countryEn") String countryEn,
                               @RequestParam(value = "edit") Integer edit,
                               ModelMap modelMap) {
        Country country = countryRepository.findByCountryEn(countryEn);
//        currency.setCountry(country);

//        currencyRepository.save(currency);
          countryService.saveCurrency(currencyDto, countryEn);
//        modelMap.addAttribute("countryEn", country.getCountryEn());
//        modelMap.addAttribute("countryPl", country.getCountryPl());
//        modelMap.addAttribute("currencyList", countryService.currencyFromCountryId(country, paternSet.getPatternSet()));
//        modelMap.addAttribute("countryId", currency.getCountry().getId()); //Wysyła Id państwa potrzebnego do wyswietlenia Jsona

//        if (edit == null) {
//            modelMap.addAttribute("button", Constans.BUTTON_ADD_CURRENCY);
//        }
        modelMap.addAttribute("button", Constans.BUTTON_ADD_CURRENCY);
        CurrencyDto currencyDto1 = new CurrencyDto();
        currencyDto1.setPattern(paternSet.getPatternSet().toUpperCase());
        modelMap.addAttribute("currencyDto", currencyDto1);
        return currencyDate(modelMap, country, paternSet.getPatternSet());
//        return "currency";
    }

    @GetMapping(value = {"/Pecunia/currencyEdit/{currencyId}","/currencyEdit/{currencyId}"})
    public String postCurrency(@PathVariable Long currencyId, ModelMap modelMap) {
        Optional<Currency> currency = currencyRepository.findById(currencyId);
        Country country = currency.get().getCountry();
        CurrencyDto currencyDto = new ModelMapper().map(currency.get(), CurrencyDto.class);
        modelMap.addAttribute("currencyDto", currencyDto);

        System.out.println("Powinna byćwaluta do edycji - " + currencyDto);

        modelMap.addAttribute("edit", 1);
        modelMap.addAttribute("button", Constans.BUTTON_SAVE_CHANGE);

        //TODO dodać poprawne wyświelanie walit powiązanych z patternem
        return currencyDate(modelMap, country);
    }

    private String currencyDate(ModelMap modelMap, Country country) {
        modelMap.addAttribute("countryEn", country.getCountryEn());
        modelMap.addAttribute("countryPl", country.getCountryPl());
        modelMap.addAttribute("currencyList", countryService.currencyFromCountryId(country.getId()));
        modelMap.addAttribute("countryId", country.getId());
        return "currency";
    }

    private String currencyDate(ModelMap modelMap, Country country, String pattern) {
        modelMap.addAttribute("countryEn", country.getCountryEn());
        modelMap.addAttribute("countryPl", country.getCountryPl());
        modelMap.addAttribute("currencyList", countryService.currencyFromCountryId(country, pattern.toUpperCase()));

        modelMap.addAttribute("countryId", country.getId());
        return "currency";
    }

    @GetMapping(value = {"/Pecunia/all_countrys", "/all_countrys"})
    public String getAllCountryOfWorld(ModelMap modelMap) {

        countryList(modelMap);
        return "country_of_world";
    }


    @GetMapping(value = {"/Pecunia/currencyCoin","/currencyCoin"})
    public String getCurrencyCoin(ModelMap modelMap, HttpServletRequest request) {
        modelMap.addAttribute("countrys", countryService.countryDtoList().getCountryDtoList());
        modelMap.addAttribute("currencyTrue", false);
        modelMap.addAttribute("chooseCountry", "Wyberz państwo");

        System.out.println("DODAWANIE waluty do państwa!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (request.getRequestURI().contains("Coin")) {
            paternSet.patternSet("Coin");
        }

        System.out.println(paternSet.getPatternSet());

        return "currency";
    }

}
