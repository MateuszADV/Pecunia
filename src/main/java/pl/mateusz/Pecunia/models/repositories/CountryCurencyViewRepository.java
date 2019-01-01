package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.Pecunia.models.CountryCurrencyView;

import java.util.List;

public interface CountryCurencyViewRepository extends JpaRepository<CountryCurrencyView, Long> {

    List<CountryCurrencyView> findByContinent(String continent);
}
