package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mateusz.Pecunia.models.NoteInfoView;

import java.util.List;

@Repository
public interface NoteInfoViewRepository extends JpaRepository<NoteInfoView, Long> {

    List<NoteInfoView> findAllByOrderByCountryEn();
    List<NoteInfoView> findAllByCountryEnOrderByNoteId(String countryEn);
    List<NoteInfoView> findAllByCurrencyId(Long currencyId);
}
