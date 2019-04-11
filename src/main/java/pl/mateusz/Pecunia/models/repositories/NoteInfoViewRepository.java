package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.mateusz.Pecunia.models.NoteInfoView;

import java.util.List;

@Repository
public interface NoteInfoViewRepository extends JpaRepository<NoteInfoView, Long> {

    List<NoteInfoView> findAllByOrderByCountryEn();
    List<NoteInfoView> findAllByCurrencyId(Long currencyId);
    NoteInfoView findByNoteId(Long noteId);

    @Query(value = "SELECT note FROM NoteInfoView note WHERE note.countryEn = ?1 ORDER BY note.series, note.denomination ASC")
    List<NoteInfoView> findAllNoteCountry(String country);

    List<NoteInfoView> findAllByCountryId(Long countryId);

    @Query(value = "SELECT DISTINCT(note.countryEn) FROM  NoteInfoView note where note.status = 'FOR SELL'")
    List<String> countryNoteForSell();

    @Query(value = "SELECT note FROM NoteInfoView note WHERE note.countryEn = ?1 AND note.status = 'FOR SELL'")
    List<NoteInfoView> NoteForSell(String country);

}
