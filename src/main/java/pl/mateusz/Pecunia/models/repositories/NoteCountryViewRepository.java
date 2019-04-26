package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.mateusz.Pecunia.models.NoteCountryView;

import java.util.List;
import java.util.Set;

public interface NoteCountryViewRepository extends JpaRepository<NoteCountryView, Long> {

    List<NoteCountryView> findAllByContinent(String continent);

    @Query(value = "SELECT country FROM NoteCountryView country, NoteInfoView note " +
            "        WHERE note.continent = ?1 " +
            "          AND note.status = 'KOLEKCJA' " +
            "          AND note.countryEn = country.countryEn ORDER BY note.countryEn")
   Set<NoteCountryView> countryListColection (String continent);

    @Query(value = "SELECT cou FROM NoteCountryView cou ORDER BY cou.countryEn")
    List<NoteCountryView> countryInMyColection();

}
