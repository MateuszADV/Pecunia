package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.Pecunia.models.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
