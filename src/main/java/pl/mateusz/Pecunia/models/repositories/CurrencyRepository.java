package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.mateusz.Pecunia.models.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
  List<Currency> findByCountry_IdOrderByDataExchangeDesc(Long country_id);
  Optional<Currency> findById(Long currency_id);


////  use for test
//  @Query(value = "SELECT DISTINCT (curr.cod) FROM Currency curr" +
//          "        WHERE curr.active = 101" +
//          "         ORDER BY curr.cod")
//  List<String> activeCodeCurrency();
}
