package pl.mateusz.Pecunia.controllers.apiControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.Pecunia.countryService.CountryServiceImpl;
import pl.mateusz.Pecunia.models.forms.CountryViewList;

@RestController
@RequestMapping("/api/country")
public class ApiCountryControllers {

    private CountryServiceImpl countryService;

    @Autowired
    public ApiCountryControllers(CountryServiceImpl countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/")
    public ResponseEntity<CountryViewList> getCountry() {

        return ResponseEntity.ok().body(countryService.countryViewList());
    }
}
