package pl.mateusz.Pecunia.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;

@Component
public class PecuniaUtils {

    private static CountryRepository countryRepository;

    @Autowired
    public PecuniaUtils(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Double stringToDouble(String value) {
        try {
            return Double.valueOf(value);
        }catch (Exception e) {
            return 0d;
        }
    }

    public Integer stringToInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return 0;
        }
    }

    /*
    Pobieranie Klasy Country po przez Id Lub nawę państwa
     */

    public static Country getCountry(Long countryId) {
        Country country = countryRepository.getCountry(countryId);
        return country;
    }
}
