package pl.mateusz.Pecunia.controllers.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoteController {

    @GetMapping("/bills")
    public String getBills() {

        return "/bills";
    }
}
