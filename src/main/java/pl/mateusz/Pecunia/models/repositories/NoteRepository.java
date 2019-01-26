package pl.mateusz.Pecunia.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.Pecunia.models.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
