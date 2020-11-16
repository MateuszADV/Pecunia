package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.mateusz.Pecunia.models.Country;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByCountryEn(String countryEn);
//    Country findById (long countryId);
    List<Country> findAllByOrderByCountryEn();
    List<Country> findByContinent(String continent);


    @Query(value = "SELECT cou FROM Country cou WHERE cou.id = ?1")
    Country getCountry(Long countryId);

    @Query(value = "SELECT DISTINCT (cou) FROM Country cou, Currency cur " +
                   "WHERE cou.id = cur.country " +
                   "  AND cur.pattern = 'COIN'")
    List<Country> countryCoin();

}
