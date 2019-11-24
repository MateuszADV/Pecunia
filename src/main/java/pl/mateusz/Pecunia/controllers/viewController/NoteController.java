package pl.mateusz.Pecunia.controllers.viewController;

import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.controllers.Constans;
import pl.mateusz.Pecunia.models.Note;
import pl.mateusz.Pecunia.models.NoteCountryView;
import pl.mateusz.Pecunia.models.NoteInfoView;
import pl.mateusz.Pecunia.models.dtos.NoteCountryViewDto;
import pl.mateusz.Pecunia.models.dtos.NoteDto;
import pl.mateusz.Pecunia.models.dtos.NoteInfoViewDto;
import pl.mateusz.Pecunia.models.forms.ExposedNoteDto;
import pl.mateusz.Pecunia.models.forms.enums.*;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.models.repositories.NoteCountryViewRepository;
import pl.mateusz.Pecunia.models.repositories.NoteInfoViewRepository;
import pl.mateusz.Pecunia.models.repositories.NoteRepository;
import pl.mateusz.Pecunia.services.NoteService.NoteService;
import pl.mateusz.Pecunia.services.countryService.CountryService;
import pl.mateusz.Pecunia.utils.JsonUtils;

import javax.validation.Valid;
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

//        for (CountryDto countryDto : countryService.countryDtoList().getCountryDtoList()) {
//            System.out.println(countryDto);
//        }

        return "banknotes";
    }

    @GetMapping(value = {"/Pecunia/selectCurrency/{countryId}","/selectCurrency/{countryId}"})
    public String getNotesCountry(@PathVariable Long countryId, ModelMap modelMap) {
        modelMap.addAttribute("currencyList", countryService.currencyFromCountryId(countryId));
        modelMap.addAttribute("countryVisible",true);
        modelMap.addAttribute("title","Wybierz walute");
        modelMap.addAttribute("country", countryService.countryFromId(countryId));

        return "banknotes";
    }

    @PostMapping(value = {"/Pecunia/addNote","/addNote"})
    public String postNote(@ModelAttribute("noteDto") @Valid NoteDto noteDto, BindingResult result,
                           @RequestParam("currencyId") Long currencyId,
                           ModelMap modelMap) {
        /**
         * TYMCZASOWE ROZWIĄZANIE DO SPRAWDANIA POPRAWNOŚCI DATY ZAKUPU BANKNOTU
         */
        if (result.hasErrors()) {
            System.out.println("+++Powinien być wynik RESULT " + result.hasErrors());
            System.out.println(noteDto.getDateBuyNote());
            modelMap.addAttribute("data_erorr", "Został podany niepopraw format daty 2000-01-01");
            modelMap.addAttribute("save", "Zmiany nie zostały zapisane :(");

            EnumForm(modelMap);
            noteEdit(noteDto.getId(), modelMap);
            return "note";
        }

        if (BooleanUtils.isTrue(noteService.saveNote(noteDto,currencyId))) {
            modelMap.addAttribute("save", "Bnknot został dodany do bazy");
            modelMap.addAttribute("button", Constans.BUTTON_ADD_NOTE);
            modelMap.addAttribute("statusSave", true);
        }else {
            modelMap.addAttribute("save", "Coś poszło nie tak :(!!!");
            modelMap.addAttribute("statusSave", false);
        }

        EnumForm(modelMap);
        NoteModelMap(currencyId, modelMap);
        return "note";
    }

    private void EnumForm(ModelMap modelMap) {
        modelMap.addAttribute("status", StatusEnum.values());
        modelMap.addAttribute("statusSell", StatusSellEnum.values());
        modelMap.addAttribute("making", MakingEnum.values());
        modelMap.addAttribute("imgType", ImgTypeEnum.values());
    }

    private void NoteModelMap(@RequestParam("currencyId") Long currencyId, ModelMap modelMap) {
        modelMap.addAttribute("currencyNoteList",noteService.currencyNoteList(currencyId));
        modelMap.addAttribute("countryCurrency", noteService.countryCurrencyView(currencyId));
        modelMap.addAttribute("currencyId", currencyId);
        modelMap.addAttribute("noteDto", new NoteDto());

        EnumForm(modelMap);
    }

    @GetMapping(value = {"Pecunia/quality","/quality"})
    public String getQuality(){

        return "quality";
    }

    @GetMapping(value = {"/Pecunia/showNotes","/showNotes"})
    public String getShowNote(ModelMap modelMap) {

        List<NoteInfoView> noteInfoViewList = noteInfoViewRepository.noteInColection();
        modelMap.addAttribute("banknotes", noteInfoViewList);
        modelMap.addAttribute("status", "Banknoty w kolekcji");

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
        modelMap.addAttribute("countryEn", countryEn);
        modelMap.addAttribute("countryEn", countryEn);
        modelMap.addAttribute("banknotes", noteService.noteFromCountry(countryEn));

        return "showNotes";
    }

    @GetMapping(value = {"/Pecunia/noteEdit/{noteId}","/noteEdit/{noteId}"})
    public String getEditNote(@PathVariable Long noteId,
                              ModelMap modelMap) {

        try {
            noteEdit(noteId, modelMap);
        }catch (Exception e){
            modelMap.addAttribute("errorId", noteId);
            return "error404";
        }

        EnumForm(modelMap);
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

    @GetMapping(value = {"/Pecunia/continent","/continent"})
    public String getContinent(ModelMap modelMap) {

//        for (ContinentEnum value : ContinentEnum.values()) {
//            System.out.println(value);
//        }
        modelMap.addAttribute("continent", ContinentEnum.values());
        return "continent";
    }

    @GetMapping(value = {"/Pecunia/continent/{continent}","/continent/{continent}"})
    public String getContinent(@PathVariable String continent, ModelMap modelMap) {
        modelMap.addAttribute("continentTrue", true);

        modelMap.addAttribute("continent", ContinentEnum.values());
//        modelMap.addAttribute("countrys", noteService.CountryFromContinent(continent.replace("_"," ")));
        modelMap.addAttribute("countrys", noteService.countryInColection(continent.replace("_"," ")));

        modelMap.addAttribute("countryFromContinent", continent.replace("_"," "));
        return "continent";
    }


    /**
     * Banknoty na sprzedaż
     */
    @GetMapping(value = {"/Pecunia/for_sell", "/for_sell"})
    public String getForSell(ModelMap modelMap) {
        modelMap.addAttribute("heder", Constans.NOTE_STATUS_FOR_SELL);
        modelMap.addAttribute("countrys",noteService.countryNoteList(Constans.NOTE_STATUS_FOR_SELL));
//        System.out.println(noteService.countryNoteForSell());
        return "for_sell";
    }

    @GetMapping(value = {"/Pecunia/for_sell/{country}","/for_sell/{country}"})
    public String getNoteForSell(@PathVariable String country, ModelMap modelMap) {
        modelMap.addAttribute("heder", Constans.NOTE_STATUS_FOR_SELL);
        modelMap.addAttribute("noteForSell", true);
        modelMap.addAttribute("banknotes", noteService.noteForSell(country, Constans.NOTE_STATUS_FOR_SELL));
//        System.out.println(noteService.noteForSell_OrderByCountry(country));
        return "for_sell";
    }

    @GetMapping(value = {"/Pecunia/all_note_for_sell", "/all_note_for_sell"})
    public String getAllNoteForSell(ModelMap modelMap) {
        List<NoteInfoView> noteInfoViewList = noteInfoViewRepository.noteForSell_OrderByCountry(Constans.NOTE_STATUS_FOR_SELL);
        modelMap.addAttribute("banknotes", noteInfoViewList);
        modelMap.addAttribute("status", "Banknoty for sell");

        return "showNotes";
    }

    /**
     * Banknoty wyswietlanie bootstrap

     */

    @GetMapping(value = {"/Pecunia/select_continent", "/select_continent"})
    public String getContinent2(ModelMap modelMap) {
        modelMap.addAttribute("continentTrue", true);
        modelMap.addAttribute("continent", ContinentEnum.values());
        return "view_note";
    }

    @GetMapping(value = {"/Pecunia/select_country/{continent}", "/select_country/{continent}"})
    public String getSelectContinent(@PathVariable String continent, ModelMap modelMap) {
        modelMap.addAttribute("countryTrue", true);
        modelMap.addAttribute("countrys", noteService.countryInColection(continent.replace("_"," ")));
        modelMap.addAttribute("countryFromContinent", continent.replace("_"," "));
        return "view_note";
    }

    @GetMapping(value = {"/Pecunia/view_note/{countryEn}", "/view_note/{countryEn}"})
    public String getSelectCountry(@PathVariable String countryEn, ModelMap modelMap) {
        modelMap.addAttribute("noteTrue", true);
        modelMap.addAttribute("countryEn", countryEn);
        modelMap.addAttribute("banknotes", noteService.noteFromCountry(countryEn));
        return "view_note";
    }

    @GetMapping(value = {"/Pecunia/select_country", "/select_country"})
    public String getCountryInMyColection( ModelMap modelMap) {
        modelMap.addAttribute("countryTrue", true);
        modelMap.addAttribute("countrys", noteCountryViewRepository.countryInMyColection(Constans.NOTE_STATUS_KOLEKCJA));
        modelMap.addAttribute("countryFromContinent", "Wszystkie kontynenty");


        return "view_note";
    }

    /**
    NOWE BANKNOTY
     */

    //    Banknoty na sprzedaż
    @GetMapping(value = {"/Pecunia/new_note", "/new_note"})
    public String getNewNote(ModelMap modelMap) {
        modelMap.addAttribute("heder", Constans.NOTE_STATUS_NEW_NOTE);
        modelMap.addAttribute("countrys",noteService.countryNoteList(Constans.NOTE_STATUS_NEW_NOTE));

        return "for_sell";
    }

    @GetMapping(value = {"/Pecunia/new_note/{country}","/new_note/{country}"})
    public String getNewNote(@PathVariable String country, ModelMap modelMap) {
        modelMap.addAttribute("heder", Constans.NOTE_STATUS_NEW_NOTE);
        modelMap.addAttribute("noteForSell", true);
        modelMap.addAttribute("banknotes", noteService.noteForSell(country, Constans.NOTE_STATUS_NEW_NOTE));
//        System.out.println(noteService.noteForSell_OrderByCountry(country));
        return "for_sell";
    }

    /**
     * Wystawione banknoty
     */
    @GetMapping(value = {"/Pecunia/exposed_country", "/exposed_country"})
    public String getExposedCountry(ModelMap modelMap) {
        //TODO Poprwić zeby były wyświetlane państwa z których są wystawione bankoty
        modelMap.addAttribute("heder", Constans.NOTE_STATUS_OLX);
        modelMap.addAttribute("countrys",noteService.countryNoteList(Constans.NOTE_STATUS_FOR_SELL, Constans.NOTE_STATUS_OLX));
//        System.out.println(noteService.countryNoteForSell());
        return "for_sell";
    }

    @GetMapping(value = {"/Pecunia/exposed/{country}","/exposed/{country}"})
    public String getExposedNotes(@PathVariable String country, ModelMap modelMap) {
        List<NoteInfoViewDto> noteInfoViewDtoList = noteService.noteForSell(country, Constans.NOTE_STATUS_FOR_SELL, Constans.NOTE_STATUS_OLX);
        modelMap.addAttribute("exposedNote", noteInfoViewDtoList);
        modelMap.addAttribute("notesOnSell", noteInfoViewDtoList.size());
//        System.out.println(noteService.noteForSell_OrderByCountry(country));
        return "exposed";
    }

    @GetMapping(value = {"/Pecunia/exposed", "/exposed"})
    public String getExposedNote(ModelMap modelMap) {
        List<ExposedNoteDto> exposedNoteDtoList = noteService.exposedNote("OLX");
        modelMap.addAttribute("exposedNote",exposedNoteDtoList);
        modelMap.addAttribute("notesOnSell" ,exposedNoteDtoList.size());
        System.out.println("ILOść banknotów w sprzedaży: " + exposedNoteDtoList.size());
        return "exposed";
    }

    /**
     * Wyswitla państwa i banknoty które zostały sprzedane
     */

    @GetMapping(value = {"/Pecunia/sold", "/sold"})
    public String getForSeld(ModelMap modelMap) {
        modelMap.addAttribute("heder", Constans.NOTE_STATUS_SOLD);
        modelMap.addAttribute("countrys",noteService.countryNoteList(Constans.NOTE_STATUS_SOLD));
//        System.out.println(noteService.countryNoteForSell());
        return "for_sell";
    }

    @GetMapping(value = {"/Pecunia/sold/{country}","/sold/{country}"})
    public String getNoteSold(@PathVariable String country, ModelMap modelMap) {
        modelMap.addAttribute("heder", Constans.NOTE_STATUS_SOLD);
        modelMap.addAttribute("noteForSell", true);
        modelMap.addAttribute("banknotes", noteService.noteForSell(country, Constans.NOTE_STATUS_SOLD));
//        System.out.println(noteService.noteForSell_OrderByCountry(country));
        return "for_sell";
    }



}
