package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.Pecunia.models.NoteCountryView;

public interface NoteCountryViewRepository extends JpaRepository<NoteCountryView, Long> {

}
