package pl.mateusz.Pecunia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.models.forms.ContinentCountryCurrencyNote;
import pl.mateusz.Pecunia.models.forms.metal.DataSet;
import pl.mateusz.Pecunia.services.NoteService.NoteService;
import pl.mateusz.Pecunia.services.exchangeService.ExchangeService;

@RestController
@RequestMapping("/api/")
public class TestApiComntroller {

    private ExchangeService exchangeService;

    @Autowired
    public TestApiComntroller(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("testprice")
    public ResponseEntity<DataSet> getPriceMetal() {

        DataSet dataSet = exchangeService.dataSet("https://www.quandl.com/api/v3/datasets/LBMA/SILVER.json?limit=2", 2);
        System.out.println(dataSet);


        return ResponseEntity.ok().body(dataSet);
    }
}

