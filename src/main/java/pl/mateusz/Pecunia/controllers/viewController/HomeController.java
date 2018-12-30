package pl.mateusz.Pecunia.controllers.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String getlogin(){


        return "login";
    }

    @ResponseBody
    @GetMapping("/error")
    public String getError(){

        return "<h1>BRAK DOSTEPU</h1";
    }
}
