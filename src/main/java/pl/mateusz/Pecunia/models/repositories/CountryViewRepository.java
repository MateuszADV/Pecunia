package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.Pecunia.models.CountryCountryView;

public interface CountryViewRepository extends JpaRepository<CountryCountryView, Long> {
}
