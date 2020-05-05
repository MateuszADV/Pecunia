package pl.mateusz.Pecunia.services.exchangeService;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.forms.Exchange;
import pl.mateusz.Pecunia.models.forms.GoldRate;
import pl.mateusz.Pecunia.models.forms.Rates;
import pl.mateusz.Pecunia.models.forms.enums.WeightEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService {


    String url = "http://api.nbp.pl/api/exchangerates/tables/a/last/";

    @Override
    public Exchange exchange() {
        return exchange(url);
    }

    @Override
    public Exchange exchange(List<String> listCode) {
        Exchange exchange = exchange("http://api.nbp.pl/api/exchangerates/tables/a/last/");
        List<Rates> ratesList = new ArrayList<>();
        for (Rates rate : exchange.getRates()) {
            if (listCode.contains(rate.getCod())) {
                ratesList.add(rate);
            }
        }
        exchange.setRates(ratesList);
        return exchange;
    }

    private Exchange exchange (String url) {
        String exchangeRate = jsonString(url);

        Exchange exchange = new Exchange();
        List<Rates> ratesList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Exchange> exchangeList = (List<Exchange>) mapper.readValue(exchangeRate, List.class);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(exchangeList);

            exchange.setTable(jsonArray.getJSONArray(0).getJSONObject(0).getString("table"));
            exchange.setNo(jsonArray.getJSONArray(0).getJSONObject(0).getString("no"));
            exchange.setEffectiveDate(jsonArray.getJSONArray(0).getJSONObject(0).getString("effectiveDate"));

            JSONArray jsonArrayRates = new JSONArray();
            jsonArrayRates.put(jsonArray.getJSONArray(0).getJSONObject(0).getJSONArray("rates"));

            for (int i = 0; i <= jsonArrayRates.getJSONArray(0).length() - 1; i++ ) {
                Rates rates = new Rates();
                rates.setCod(jsonArrayRates.getJSONArray(0).getJSONObject(i).getString("code"));
                rates.setCurrency(jsonArrayRates.getJSONArray(0).getJSONObject(i).getString("currency"));
                rates.setMid(jsonArrayRates.getJSONArray(0).getJSONObject(i).getDouble("mid"));
                ratesList.add(rates);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        exchange.setRates(ratesList);
        return exchange;
    }

    @Override
    public List<GoldRate> goldRate() {
        String exchangeRate = jsonString("https://api.nbp.pl/api/cenyzlota/last/11/?format=json");
        JSONArray jsonArray = new JSONArray();
        ObjectMapper mapper = new ObjectMapper();
        List<GoldRate> goldRateList = new ArrayList<>();

        try {
            List<GoldRate> goldRates = (List<GoldRate>) mapper.readValue(exchangeRate, List.class);
            System.out.println(goldRates);
            System.out.println(goldRates.size());
            jsonArray.put(goldRates);

            JSONObject jsonObject = new JSONObject();
            for (Object o : jsonArray.getJSONArray(0)) {
                GoldRate goldRate1 = new GoldRate();
                jsonObject.put("gold", o);
                goldRate1.setDataRate(jsonObject.getJSONObject("gold").getString("data"));
                goldRate1.setPriceForGram(jsonObject.getJSONObject("gold").getDouble("cena"));
                goldRate1.setPriceForOunce(goldRate1.getPriceForGram() * WeightEnum.OUNCE.getWeight());

                goldRateList.add(goldRate1);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return changeRate(goldRateList);
    }

    private List<GoldRate> changeRate(List<GoldRate> goldRateList) {

        Double changeRate;
        for (int i = 1; i < goldRateList.size(); i++ ) {
            changeRate = ((goldRateList.get(i).getPriceForGram() - goldRateList.get(i-1).getPriceForGram()) / goldRateList.get(i-1).getPriceForGram());
            goldRateList.get(i).setChange(changeRate * 100);
        }
        goldRateList.remove(0);

        System.out.println(goldRateList.size());
        return goldRateList;
    }

    private String jsonString(String url) {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);

        if(clientResponse.getStatus() !=200){
            throw new RuntimeException("Błąd pobrania... " + clientResponse.getStatus());
        }
        return clientResponse.getEntity(String.class);
    }


}
