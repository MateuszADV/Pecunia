package pl.mateusz.Pecunia.controllers.apiControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.models.forms.ContinentCountryCurrencyNote;
import pl.mateusz.Pecunia.services.NoteService.NoteService;

@RestController
@RequestMapping("/api/note")
public class ApiNoteController {

    private NoteService noteService;

    @Autowired
    public ApiNoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/noteListFromCountry")
    public ResponseEntity<ContinentCountryCurrencyNote> getNoteFromCountry(@RequestParam Long countryId) {

        return ResponseEntity.ok().body(noteService.continentCountryCuttencyNote(countryId));
    }
}
