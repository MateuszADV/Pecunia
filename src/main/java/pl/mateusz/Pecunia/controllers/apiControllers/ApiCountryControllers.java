package pl.mateusz.Pecunia.controllers.apiControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.models.forms.ContinentCountryCurrencysResponse;
import pl.mateusz.Pecunia.models.forms.ContinentRequest;
import pl.mateusz.Pecunia.models.forms.ContinentResponse;
import pl.mateusz.Pecunia.models.forms.CountryViewList;
import pl.mateusz.Pecunia.services.countryService.CountryServiceImpl;


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

    @GetMapping("/continent")
    public ResponseEntity<ContinentResponse> getContinent() {

        return ResponseEntity.ok().body(countryService.continentResponse());
    }

    @PostMapping("/continent")
    public ResponseEntity<ContinentResponse> postContinent(@RequestBody ContinentRequest request) {

        return ResponseEntity.ok().body(countryService.continentResponse(request));
    }

    @GetMapping("/currencysOfWorld")
    public ResponseEntity<ContinentCountryCurrencysResponse> getCurrencysOfWorld() {

        return ResponseEntity.ok().body(countryService.continentCountryCurrencysResponse());
    }

    @PostMapping("/currencysOfWorld")
    public ResponseEntity<ContinentCountryCurrencysResponse> postCurrencysOfWorld(@RequestBody ContinentRequest request) {

        return ResponseEntity.ok().body(countryService.continentCountryCurrencysResponse(request));
    }

}
