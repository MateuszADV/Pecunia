package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mateusz.Pecunia.models.CountryCurrencyView;

@Repository
public interface CountryCurrencyViewRepository extends JpaRepository<CountryCurrencyView, Long> {
    CountryCurrencyView findByCurrencyId(Long currencyId);
}
