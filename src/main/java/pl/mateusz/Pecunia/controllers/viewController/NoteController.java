package pl.mateusz.Pecunia.controllers.viewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.Note;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.models.repositories.NoteRepository;
import pl.mateusz.Pecunia.services.NoteService.NoteService;
import pl.mateusz.Pecunia.services.countryService.CountryService;

import java.util.List;
import java.util.Optional;

@Controller
public class NoteController {


    private NoteService noteService;
    private NoteRepository noteRepository;
    private CurrencyRepository currencyRepository;
    private CountryService countryService;

    @Autowired
    public NoteController(NoteService noteService, NoteRepository noteRepository, CurrencyRepository currencyRepository, CountryService countryService) {
        this.noteService = noteService;
        this.noteRepository = noteRepository;
        this.currencyRepository = currencyRepository;
        this.countryService = countryService;
    }

    @GetMapping("/note")
    public String getNotes(ModelMap modelMap) {
        modelMap.addAttribute("note", new Note());
        return "note";
    }

    @GetMapping("/note/{currencyId}")
    public String getNote(@PathVariable Long currencyId, ModelMap modelMap) {
        System.out.println("Powinno być id waluty: " + currencyId);

        modelMap.addAttribute("countryCurrency", noteService.countryCurrencyView(currencyId));
        modelMap.addAttribute("currencyId", currencyId);
        modelMap.addAttribute("note", new Note());

        return "/note";
    }

    @PostMapping("/note")
    public String postNote(@ModelAttribute("note") Note note,
                           @RequestParam("currencyId") Long currencyId,
                           ModelMap modelMap) {

        Optional<Currency> currency = currencyRepository.findById(currencyId);
        note.setCurrency(currency.get());
        noteRepository.save(note);
        System.out.println(note.getDenomination()*2);
        System.out.println("Id waluty: " + currencyId);
        modelMap.addAttribute("countryCurrency", noteService.countryCurrencyView(currencyId));
        modelMap.addAttribute("currencyId", currencyId);
        modelMap.addAttribute("note", new Note());
        return "note";
    }

    @GetMapping("/notes")
    public String getNotesSize(ModelMap modelMap) {

        modelMap.addAttribute("countrys", countryService.countryDtoList().getCountryDtoList());

        List<Note> noteList = noteRepository.findAll();
        modelMap.addAttribute("noteList", noteList);

        return "notes";
    }

    @GetMapping("/notes/{countryId}")
    public String getNotesCountry(@PathVariable Long countryId, ModelMap modelMap) {
        modelMap.addAttribute("currencyList", countryService.curencyFromCountryId(countryId));

        System.out.println("powinno byćid państwa: " + countryId);

        return "notes";
    }
}
