package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.Pecunia.models.Country;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByCountryEn(String countryEn);
    Country findById (long countryId);
    List<Country> findAllByOrderByCountryEn();
    List<Country> findByContinent(String continent);
}
