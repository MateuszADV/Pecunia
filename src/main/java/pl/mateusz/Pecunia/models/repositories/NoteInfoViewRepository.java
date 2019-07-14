package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.mateusz.Pecunia.models.NoteInfoView;

import java.util.List;

@Repository
public interface NoteInfoViewRepository extends JpaRepository<NoteInfoView, Long> {

    @Query(value = "SELECT note FROM NoteInfoView  note " +
                   " WHERE note.status = 'KOLEKCJA' " +
                   " ORDER BY note.countryEn")
    List<NoteInfoView> noteInColection();

    @Query(value = "SELECT note FROM NoteInfoView  note" +
                   " WHERE note.currencyId = ?1" +
                   " ORDER BY note.series, note.denomination")
    List<NoteInfoView> findAllByCurrencyId(Long currencyId);

    NoteInfoView findByNoteId(Long noteId);

    @Query(value = "SELECT note FROM NoteInfoView note " +
                   " WHERE note.countryEn = ?1 " +
                   "   AND note.status = 'KOLEKCJA' " +
                   " ORDER BY note.series, note.denomination ASC")
    List<NoteInfoView> findAllNoteCountry(String country);

    List<NoteInfoView> findAllByCountryId(Long countryId);

    @Query(value = "SELECT DISTINCT(note.countryEn) FROM  NoteInfoView note " +
                   " WHERE note.status = ?1 " +
                   " ORDER BY note.countryEn")
    List<String> countryNoteForSell(String status);

    @Query(value = "SELECT note FROM NoteInfoView note " +
                   " WHERE note.countryEn = ?1 " +
                   "   AND note.status = ?2")
    List<NoteInfoView> NoteForSell(String country, String status);

    @Query(value = "SELECT note FROM NoteInfoView  note " +
                   " WHERE note.status = ?1 " +
                   " ORDER BY note.countryEn")
    List<NoteInfoView> noteForSell_OrderByCountry(String status);

}
