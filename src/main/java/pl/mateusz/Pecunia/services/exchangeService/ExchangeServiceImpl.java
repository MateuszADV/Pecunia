package pl.mateusz.Pecunia.services.exchangeService;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.forms.Exchange;
import pl.mateusz.Pecunia.models.forms.Rates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    @Override
    public Exchange exchange() {
        return exchange("http://api.nbp.pl/api/exchangerates/tables/a/last/");
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
