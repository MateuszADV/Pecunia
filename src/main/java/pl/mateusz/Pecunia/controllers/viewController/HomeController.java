package pl.mateusz.Pecunia.controllers.viewController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.Pecunia.controllers.Constans;
import pl.mateusz.Pecunia.models.CodeParam;
import pl.mateusz.Pecunia.models.dtos.CodeParamDto;
import pl.mateusz.Pecunia.models.forms.GoldRate;
import pl.mateusz.Pecunia.models.forms.enums.WeightEnum;
import pl.mateusz.Pecunia.models.repositories.CodeParamRepository;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.models.repositories.OrderRepository;
import pl.mateusz.Pecunia.services.HomeService.HomeService;
import pl.mateusz.Pecunia.services.OrderService.OrderService;
import pl.mateusz.Pecunia.services.countryService.CountryService;
import pl.mateusz.Pecunia.services.exchangeService.ExchangeService;
import pl.mateusz.Pecunia.utils.JsonUtils;

import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {


    private CountryService countryService;
    private CountryRepository countryRepository;
    private CurrencyRepository currencyRepository;
    private ExchangeService exchangeService;
    private CodeParamRepository codeParamRepository;
    private HomeService homeService;

    private OrderService orderService;
    private OrderRepository orderRepository;

    public HomeController(CountryService countryService, CountryRepository countryRepository, CurrencyRepository currencyRepository, ExchangeService exchangeService, CodeParamRepository codeParamRepository, HomeService homeService, OrderService orderService, OrderRepository orderRepository) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.exchangeService = exchangeService;
        this.codeParamRepository = codeParamRepository;
        this.homeService = homeService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/login")
    public String getlogin( ){

        return "login";
    }

    @GetMapping(value = {"Pecunia","/"})
    public String getIndex(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

        try {
            List<String> codeList = homeService.currencyCode();
            modelMap.addAttribute("exchange", true);
            modelMap.addAttribute("exchangeRate",exchangeService.exchange(codeList));
        }catch (Exception e) {
            modelMap.addAttribute("exchange", false);
            modelMap.addAttribute("error", e.getMessage());
        }

        modelMap.addAttribute("goldList", exchangeService.goldRate());
//        System.out.println("Powinien byćwynik jakiś tam");
//        System.out.println(exchangeService.goldRate());

//        System.out.println(orderService.getlastNumberOrder());
//        System.out.println(orderService.lastOrder().getOrderNumber());

        return "index";
    }

    @ResponseBody
    @GetMapping("/error")
    public String getError() {

        return "<h1>BRAK STRONY BŁĄD 404</h1>";
    }

    @PostMapping(value = {"/Pecunia/currency/showJson","/currency/showJson"})
    public String getShowJson(@RequestParam(value = "countryId") Long countryId, ModelMap modelMap) {

        modelMap.addAttribute("Gson", JsonUtils.gsonPretty(countryService.countryJson(countryId)));

        return "showJson";
    }

//    @GetMapping("/test")
//    @ResponseBody
//    public String getTest() {
//        List<Country> countCountries = countryRepository.CountryOn();
//        System.out.println(countCountries);
//        return "test";
//    }

    @GetMapping(value = {"/Pecunia/cod_currency", "/cod_currency"})
    public String getCodCurrency(ModelMap modelMap) {
        CodeParam codeParam = codeParamRepository.findByWebName("index");
//        System.out.println(codeParam);
//
//        JSONObject codeJson = new JSONObject(codeParam.getParameters());
//        System.out.println(codeJson);
//
//        List<String> code = Arrays.asList("GBP","USD","JPY","CAD","EUR");
//        System.out.println(code);
//        JSONArray jsonArray = new JSONArray(code);
//        System.out.println(jsonArray);
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code", code);
//        System.out.println(jsonObject);
//
//        CodeParam codeParam1 = new CodeParam();
//        codeParam1.setWebName("TEST");
//        codeParam1.setParameters(jsonObject.toString());
//
//        System.out.println("****************************************");
//        System.out.println(codeParam1);
//        System.out.println("*****************************************");

//
//        String codes = "GBP,EUR,CAD,EGP,USD";
//        List<String> codesList = new ArrayList<>();
//        codesList.addAll(Arrays.asList(codes.split(",")));
//        System.out.println("+++++++++++++++++++++++++++++++++++\n" +
//                codesList +
//                "\n++++++++++++++++++++++++++++++++++");

        codeParamList(modelMap);
        modelMap.addAttribute("codeParamDto", new CodeParamDto());
        modelMap.addAttribute("button", Constans.BUTTON_ADD_PARAMETER);

//        codeParamRepository.save(codeParam1);

        return "cod_currency";
    }

    /**
     * pobieranie korsu złota
     */
    @GetMapping(value = {"/Pecunia/gold", "/gold"})
    public String getGold(ModelMap modelMap) {
        String exchangeRate = jsonString("https://api.nbp.pl/api/cenyzlota/?format=json");

        GoldRate goldRate = new GoldRate();
        JSONArray jsonArray = new JSONArray();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<GoldRate> goldRates = (List<GoldRate>) mapper.readValue(exchangeRate, List.class);
            jsonArray.put(goldRates);
            goldRate.setDataRate(jsonArray.getJSONArray(0).getJSONObject(0).getString("data"));
            goldRate.setPriceForGram(jsonArray.getJSONArray(0).getJSONObject(0).getDouble("cena"));
            goldRate.setPriceForOunce(goldRate.getPriceForGram() * WeightEnum.OUNCE.getWeight());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        modelMap.addAttribute("gold", goldRate);

        return "cod_currency";
    }

//    @GetMapping(value = {"/Pecunia/goldList", "/goldlist"})
//    public String getGoldList(ModelMap modelMap) {
//        String exchangeRate = jsonString("https://api.nbp.pl/api/cenyzlota/last/11/?format=json");
//        JSONArray jsonArray = new JSONArray();
//        ObjectMapper mapper = new ObjectMapper();
//        List<GoldRate> goldRateList = new ArrayList<>();
//
//        try {
//            List<GoldRate> goldRates = (List<GoldRate>) mapper.readValue(exchangeRate, List.class);
//            System.out.println(goldRates);
//            System.out.println(goldRates.size());
//            jsonArray.put(goldRates);
//
//            JSONObject jsonObject = new JSONObject();
//            for (Object o : jsonArray.getJSONArray(0)) {
//                GoldRate goldRate1 = new GoldRate();
//                jsonObject.put("gold", o);
//                goldRate1.setDataRate(jsonObject.getJSONObject("gold").getString("data"));
//                goldRate1.setPriceForGram(jsonObject.getJSONObject("gold").getDouble("cena"));
//                goldRate1.setPriceForOunce(goldRate1.getPriceForGram() * WeightEnum.OUNCE.getWeight());
//
//                goldRateList.add(goldRate1);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        modelMap.addAttribute("goldList", exchangeService.goldRate());
//        return "cod_currency";
//    }


    private String jsonString(String url) {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);

        if(clientResponse.getStatus() !=200){
            throw new RuntimeException("Błąd pobrania... " + clientResponse.getStatus());
        }
        return clientResponse.getEntity(String.class);
    }

//    private List<GoldRate> changeRate(List<GoldRate> goldRateList) {
//
//        Double changeRate;
//        for (int i = 1; i < goldRateList.size(); i++ ) {
//            changeRate = ((goldRateList.get(i).getPriceForGram() - goldRateList.get(i-1).getPriceForGram()) / goldRateList.get(i-1).getPriceForGram());
//            goldRateList.get(i).setChange(changeRate * 100);
//        }
//        goldRateList.remove(0);
//
//        System.out.println(goldRateList.size());
//        return goldRateList;
//    }

}
