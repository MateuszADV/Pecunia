package pl.mateusz.Pecunia.controllers.apiControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.models.forms.CurrencyCode;
import pl.mateusz.Pecunia.models.forms.Exchange;
import pl.mateusz.Pecunia.services.exchangeService.ExchangeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/exchange")
public class ApiExchangeController {

    private ExchangeServiceImpl exchangeService;

    @Autowired
    public ApiExchangeController(ExchangeServiceImpl exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/exchangeRate")
    public ResponseEntity<Exchange> getExchange() {

        return ResponseEntity.ok().body(exchangeService.exchange());
    }

    @PostMapping("/exchangeRate")
    public ResponseEntity<Exchange> postExchange(@RequestBody CurrencyCode currencyCode,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {


        return ResponseEntity.ok().body(exchangeService.exchange(currencyCode.getCurrencyCode()));
    }
}
