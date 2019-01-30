package pl.mateusz.Pecunia.controllers.viewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.Note;
import pl.mateusz.Pecunia.models.NoteInfoView;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.models.repositories.NoteInfoViewRepository;
import pl.mateusz.Pecunia.models.repositories.NoteRepository;
import pl.mateusz.Pecunia.services.NoteService.NoteService;
import pl.mateusz.Pecunia.services.countryService.CountryService;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/Pecunia")
public class NoteController {


    private NoteService noteService;
    private NoteRepository noteRepository;
    private CurrencyRepository currencyRepository;
    private CountryService countryService;
    private NoteInfoViewRepository noteInfoViewRepository;

    @Autowired
    public NoteController(NoteService noteService, NoteRepository noteRepository, CurrencyRepository currencyRepository
                         ,CountryService countryService, NoteInfoViewRepository noteInfoViewRepository) {
        this.noteService = noteService;
        this.noteRepository = noteRepository;
        this.currencyRepository = currencyRepository;
        this.countryService = countryService;
        this.noteInfoViewRepository = noteInfoViewRepository;
    }

    @GetMapping("/note")
    public String getNotes(ModelMap modelMap) {
        modelMap.addAttribute("note", new Note());
        return "note";
    }

    @GetMapping("/note/{currencyId}")
    public String getNote(@PathVariable Long currencyId, ModelMap modelMap) {

        modelMap.addAttribute("countryCurrency", noteService.countryCurrencyView(currencyId));
        modelMap.addAttribute("currencyId", currencyId);
        modelMap.addAttribute("note", new Note());

        return "/note";
    }

    @GetMapping("/notes")
    public String getNotesSize(ModelMap modelMap) {
        modelMap.addAttribute("countrys", countryService.countryDtoList().getCountryDtoList());

        return "notes";
    }

    @GetMapping("/notes/{countryId}")
    public String getNotesCountry(@PathVariable Long countryId, ModelMap modelMap) {
        modelMap.addAttribute("currencyList", countryService.curencyFromCountryId(countryId));
        modelMap.addAttribute("countryVisible",true);
        System.out.println("powinno byćid państwa: " + countryId);

        return "notes";
    }

    @PostMapping("/note")
    public String postNote(@ModelAttribute("note") Note note,
                           @RequestParam("currencyId") Long currencyId,
                           ModelMap modelMap) {

        Optional<Currency> currency = currencyRepository.findById(currencyId);
        note.setCurrency(currency.get());
        noteRepository.save(note);
        modelMap.addAttribute("countryCurrency", noteService.countryCurrencyView(currencyId));
        modelMap.addAttribute("currencyId", currencyId);
        modelMap.addAttribute("note", new Note());
        return "note";
    }

    @GetMapping("/quality")
    public String getQuality(){

        return "quality";
    }

    @GetMapping("/showNotes")
    public String getShoowNote(ModelMap modelMap) {

        List<NoteInfoView> noteInfoViewList = noteInfoViewRepository.findAllByOrderByCountryEn();
        modelMap.addAttribute("noteInfo", noteInfoViewList);

        return "showNotes";
    }
}
