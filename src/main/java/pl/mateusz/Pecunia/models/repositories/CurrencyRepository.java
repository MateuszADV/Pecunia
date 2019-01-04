package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.Pecunia.models.Currency;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
  List<Currency> findByCountry_Id(Long country_id);
}
