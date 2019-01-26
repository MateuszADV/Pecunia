package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.Pecunia.models.CurrencyCountryActiveView;

import java.util.List;

public interface CurrencyCountryActiveViewRepository extends JpaRepository<CurrencyCountryActiveView, Long> {

    List<CurrencyCountryActiveView> findByContinent(String continent);
}
