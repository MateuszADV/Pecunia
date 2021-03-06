package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mateusz.Pecunia.models.CodeParam;

@Repository
public interface CodeParamRepository extends JpaRepository<CodeParam, Long> {

    CodeParam findByWebName(String webName);
}
