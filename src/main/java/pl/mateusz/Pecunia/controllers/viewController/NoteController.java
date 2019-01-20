package pl.mateusz.Pecunia.controllers.viewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mateusz.Pecunia.services.countryService.CountryService;

@Controller
public class NoteController {


    private CountryService countryService;

    @Autowired
    public NoteController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/bills")
    public String getBills(ModelMap modelMap) {


        return "/bills";
    }
}
