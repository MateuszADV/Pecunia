package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.Pecunia.models.GoldRate;

public interface GoldRateRepository extends JpaRepository<GoldRate, Long> {
}
