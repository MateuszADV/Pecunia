package pl.mateusz.Pecunia.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.mateusz.Pecunia.models.NoteInfoView;
import pl.mateusz.Pecunia.models.dtos.NoteInfoViewDto;
import pl.mateusz.Pecunia.models.forms.enums.ContinentEnum;
import pl.mateusz.Pecunia.models.repositories.NoteInfoViewRepository;
import pl.mateusz.Pecunia.services.NoteService.NoteService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    private NoteService noteService;
    private NoteInfoViewRepository noteInfoViewRepository;

    @Autowired
    public TestController(NoteService noteService, NoteInfoViewRepository noteInfoViewRepository) {
        this.noteService = noteService;
        this.noteInfoViewRepository = noteInfoViewRepository;
    }

    @GetMapping(value = {"/Pecunia/bootstraptest", "/bootstraptest"})
    public String getBootstrap(ModelMap modelMap) {

        modelMap.addAttribute("continent", ContinentEnum.values());
        return "bootstraptest";
    }

    @GetMapping(value = {"/Pecunia/bootstraptest/{continent}", "/bootstraptest/{continent}"})
    public String getBootstrapContinent(@PathVariable String continent, ModelMap modelMap) {
        modelMap.addAttribute("continentTrue", true);
        modelMap.addAttribute("countrys", noteService.CountryFromContinent(continent.replace("_"," ")));
        modelMap.addAttribute("countryFromContinent", continent.replace("_"," "));
        return "bootstraptest";
    }

    @GetMapping(value = {"/Pecunia/bootstraotest/note/{countryEn}","/bootstraotest/note/{countryEn}"})
    public String getBootstrapNote(@PathVariable String countryEn, ModelMap modelMap) {
        modelMap.addAttribute("noteTrue", true);
        List<NoteInfoView> noteInfoViewList = noteInfoViewRepository.findAllNoteCountry(countryEn);
        List<NoteInfoViewDto> noteInfoViewDtoList = new ArrayList<>();
        for (NoteInfoView noteInfoView : noteInfoViewList) {
            noteInfoViewDtoList.add(new ModelMapper().map(noteInfoView, NoteInfoViewDto.class));
        }

        modelMap.addAttribute("countryEn", noteInfoViewDtoList.get(0).getCountryEn());
        modelMap.addAttribute("banknotes", noteInfoViewDtoList);
        return "bootstraptest";
    }


//    BOOTSTRAP test 2

    @GetMapping(value = {"/Pecunia/bootstraptest2", "/bootstraptest2"})
    public String getBootstrap2(ModelMap modelMap) {

        modelMap.addAttribute("continent", ContinentEnum.values());
        return "bootstraptest2";
    }

    @GetMapping(value = {"/Pecunia/bootstraptest2/{continent}", "/bootstraptest2/{continent}"})
    public String getBootstrapContinent2(@PathVariable String continent, ModelMap modelMap) {
        modelMap.addAttribute("continentTrue", true);
        modelMap.addAttribute("countrys", noteService.CountryFromContinent(continent.replace("_"," ")));
        modelMap.addAttribute("countryFromContinent", continent.replace("_"," "));
        return "bootstraptest2";
    }

    @GetMapping(value = {"/Pecunia/bootstraotest2/note/{countryEn}","/bootstraotest2/note/{countryEn}"})
    public String getBootstrapNote2(@PathVariable String countryEn, ModelMap modelMap) {
        modelMap.addAttribute("noteTrue", true);
        List<NoteInfoView> noteInfoViewList = noteInfoViewRepository.findAllNoteCountry(countryEn);
        List<NoteInfoViewDto> noteInfoViewDtoList = new ArrayList<>();
        for (NoteInfoView noteInfoView : noteInfoViewList) {
            noteInfoViewDtoList.add(new ModelMapper().map(noteInfoView, NoteInfoViewDto.class));
        }

        modelMap.addAttribute("countryEn", noteInfoViewDtoList.get(0).getCountryEn());
        modelMap.addAttribute("banknotes", noteInfoViewDtoList);
        return "bootstraptest2";
    }

//    BOOTSTRAP test3

    @GetMapping(value = {"/Pecunia/bootstraptest3", "/bootstraptest3"})
    public String getBootstrap3(ModelMap modelMap) {

        List<NoteInfoView> banknotes = noteInfoViewRepository.findAllByCountryId(61l);

        for (NoteInfoView banknote : banknotes) {
            System.out.println(banknote.getAversPath());
            System.out.println(banknote.getReversePath());
        }

        modelMap.addAttribute("banknotes", banknotes);
        modelMap.addAttribute("continent", ContinentEnum.values());
        return "bootstraptest3";
    }
}
