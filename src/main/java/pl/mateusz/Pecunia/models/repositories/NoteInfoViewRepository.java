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
    List<String> countryNoteList(String status);

    @Query(value = "SELECT DISTINCT(note.countryEn) FROM  NoteInfoView note " +
            " WHERE note.status = ?1 " +
            "   AND note.statusSell = ?2" +
            " ORDER BY note.countryEn")
    List<String> countryNoteList(String status, String statusSell);

    @Query(value = "SELECT note FROM NoteInfoView note " +
                   " WHERE note.countryEn = ?1 " +
                   "   AND note.status = ?2")
    List<NoteInfoView> NoteForSell(String country, String status);

    @Query(value = "SELECT note FROM NoteInfoView note " +
            " WHERE note.countryEn = ?1 " +
            "   AND note.status = ?2" +
            "   AND note.statusSell = ?3")
    List<NoteInfoView> NoteForSell(String country, String status, String statusSell);

    @Query(value = "SELECT note FROM NoteInfoView  note " +
                   " WHERE note.status = ?1 " +
                   " ORDER BY note.countryEn")
    List<NoteInfoView> noteForSell_OrderByCountry(String status);


    @Query(value = "SELECT note FROM NoteInfoView note" +
                   " WHERE note.statusSell = ?1 " +
                   "   AND note.status <> 'SOLD'" +
                   " ORDER BY note.countryEn")
    List<NoteInfoView> exposedNote(String statusSell);

    @Query(value = "SELECT note.series FROM NoteInfoView note" +
                   " WHERE note.countryEn = ?1 " +
                   " GROUP BY note.series")
    List<String> noteInfoSeries(String country_en);

}
