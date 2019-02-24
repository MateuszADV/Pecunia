package pl.mateusz.Pecunia.controllers.viewController;

import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.controllers.Constans;
import pl.mateusz.Pecunia.models.Note;
import pl.mateusz.Pecunia.models.NoteCountryView;
import pl.mateusz.Pecunia.models.NoteInfoView;
import pl.mateusz.Pecunia.models.dtos.NoteCountryViewDto;
import pl.mateusz.Pecunia.models.dtos.NoteDto;
import pl.mateusz.Pecunia.models.dtos.NoteInfoViewDto;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.models.repositories.NoteCountryViewRepository;
import pl.mateusz.Pecunia.models.repositories.NoteInfoViewRepository;
import pl.mateusz.Pecunia.models.repositories.NoteRepository;
import pl.mateusz.Pecunia.services.NoteService.NoteService;
import pl.mateusz.Pecunia.services.countryService.CountryService;
import pl.mateusz.Pecunia.utils.JsonUtils;

import java.util.*;

@Controller
public class NoteController {


    private NoteService noteService;
    private NoteRepository noteRepository;
    private CurrencyRepository currencyRepository;
    private CountryService countryService;
    private NoteInfoViewRepository noteInfoViewRepository;
    private NoteCountryViewRepository noteCountryViewRepository;

    @Autowired
    public NoteController(NoteService noteService, NoteRepository noteRepository, CurrencyRepository currencyRepository, CountryService countryService,
                          NoteInfoViewRepository noteInfoViewRepository, NoteCountryViewRepository noteCountryViewRepository) {
        this.noteService = noteService;
        this.noteRepository = noteRepository;
        this.currencyRepository = currencyRepository;
        this.countryService = countryService;
        this.noteInfoViewRepository = noteInfoViewRepository;
        this.noteCountryViewRepository = noteCountryViewRepository;
    }


    @GetMapping(value = {"/Pecunia/note/{currencyId}","/note/{currencyId}"})
    public String getNote(@PathVariable Long currencyId, ModelMap modelMap) {

        modelMap.addAttribute("button", Constans.BUTTON_ADD_NOTE);
        NoteModelMap(currencyId, modelMap);

        return "/note";
    }

    @GetMapping(value = {"/Pecunia/selectCountry","/selectCountry"})
    public String getNotesSize(ModelMap modelMap) {
        modelMap.addAttribute("countrys", countryService.countryDtoList().getCountryDtoList());
        modelMap.addAttribute("title","Wybierz państwo");

        return "notes";
    }

    @GetMapping(value = {"/Pecunia/selectCurrency/{countryId}","/selectCurrency/{countryId}"})
    public String getNotesCountry(@PathVariable Long countryId, ModelMap modelMap) {
        modelMap.addAttribute("currencyList", countryService.currencyFromCountryId(countryId));
        modelMap.addAttribute("countryVisible",true);
        modelMap.addAttribute("title","Wybierz walute");

        return "notes";
    }

    @PostMapping(value = {"/Pecunia/addNote","/addNote"})
    public String postNote(@ModelAttribute("noteDto") NoteDto noteDto,
                           @RequestParam("currencyId") Long currencyId,
                           ModelMap modelMap) {

        if (BooleanUtils.isTrue(noteService.saveNote(noteDto,currencyId))) {
            modelMap.addAttribute("save", "Bnknot został dodany do bazy");
            modelMap.addAttribute("button", Constans.BUTTON_ADD_NOTE);
            modelMap.addAttribute("statusSave", true);
        }else {
            modelMap.addAttribute("save", "Coś poszło nie tak :(!!!");
            modelMap.addAttribute("statusSave", false);
        }

        NoteModelMap(currencyId, modelMap);
        return "note";
    }

    private void NoteModelMap(@RequestParam("currencyId") Long currencyId, ModelMap modelMap) {
        modelMap.addAttribute("currencyNoteList",noteService.currencyNoteList(currencyId));
        modelMap.addAttribute("countryCurrency", noteService.countryCurrencyView(currencyId));
        modelMap.addAttribute("currencyId", currencyId);
        modelMap.addAttribute("noteDto", new NoteDto());
    }

    @GetMapping(value = {"Pecunia/quality","/quality"})
    public String getQuality(){

        return "quality";
    }

    @GetMapping(value = {"/Pecunia/showNotes","/showNotes"})
    public String getShowNote(ModelMap modelMap) {

        List<NoteInfoView> noteInfoViewList = noteInfoViewRepository.findAllByOrderByCountryEn();
        modelMap.addAttribute("banknotes", noteInfoViewList);
        modelMap.addAttribute("countryEn", "Wszystkie banknoty");

        return "showNotes";
    }

    @GetMapping(value = {"/Pecunia/banknotesCollection","/banknotesCollection"})
    public String getBanknotesColletion(ModelMap modelMap) {
        List<NoteCountryView> noteCountryViewList = noteCountryViewRepository.findAll();
        List<NoteCountryViewDto> noteCountryViewDtoList = new ArrayList<>();

        for (NoteCountryView noteCountryView : noteCountryViewList) {
            noteCountryViewDtoList.add(new ModelMapper().map(noteCountryView, NoteCountryViewDto.class));
        }

        modelMap.addAttribute("noteCountry", noteCountryViewDtoList);

        return "banknotesCollection";
    }

    @GetMapping(value = {"/Pecunia/banknotes/{countryEn}","/banknotes/{countryEn}"})
    public String postbanknotes(@PathVariable String countryEn, ModelMap modelMap) {
        List<NoteInfoView> noteInfoViewList = noteInfoViewRepository.findAllByCountryEnOrderByNoteId(countryEn);
        List<NoteInfoViewDto> noteInfoViewDtoList = new ArrayList<>();
        for (NoteInfoView noteInfoView : noteInfoViewList) {
            noteInfoViewDtoList.add(new ModelMapper().map(noteInfoView, NoteInfoViewDto.class));
        }

        modelMap.addAttribute("countryEn", noteInfoViewDtoList.get(0).getCountryEn());
        modelMap.addAttribute("banknotes", noteInfoViewDtoList);

        return "showNotes";
    }

    @GetMapping(value = {"/Pecunia/noteEdit/{noteId}","/noteEdit/{noteId}"})
    public String getEditNote(@PathVariable Long noteId,
                              ModelMap modelMap) {
        noteEdit(noteId, modelMap);

        return "/note";
    }

    private void noteEdit(@PathVariable Long noteId, ModelMap modelMap) {
        NoteInfoView noteInfoView = noteInfoViewRepository.findByNoteId(noteId);
        Long currencyId = noteInfoView.getCurrencyId();
        Note note = noteRepository.getOne(noteId);
        NoteDto noteDto = new ModelMapper().map(note, NoteDto.class);

        modelMap.addAttribute("currencyNoteList",noteService.currencyNoteList(currencyId));
        modelMap.addAttribute("countryCurrency", noteService.countryCurrencyView(currencyId));
        modelMap.addAttribute("currencyId", currencyId);
        modelMap.addAttribute("noteDto", noteDto);
        modelMap.addAttribute("button", Constans.BUTTON_SAVE_CHANGE);

    }

    @PostMapping(value = {"/Pecunia/note/showJson","note/showJson"})
    public String getNoteJson(@RequestParam(value = "countryId") Long countryId, ModelMap modelMap) {

        modelMap.addAttribute("Gson",JsonUtils.gsonPretty(noteService.continentCountryCuttencyNote(countryId)));
        return "showJson";
    }
}
