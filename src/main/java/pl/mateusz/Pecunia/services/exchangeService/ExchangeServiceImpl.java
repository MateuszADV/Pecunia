package pl.mateusz.Pecunia.services.exchangeService;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.forms.Exchange;
import pl.mateusz.Pecunia.models.GoldRate;
import pl.mateusz.Pecunia.models.forms.Rates;
import pl.mateusz.Pecunia.models.forms.enums.GoldApiCodeEnum;
import pl.mateusz.Pecunia.models.forms.enums.WeightEnum;
import pl.mateusz.Pecunia.models.forms.goldApi.MetalPrice;
import pl.mateusz.Pecunia.models.forms.goldApi.MetalPriceDate;
import pl.mateusz.Pecunia.models.forms.metal.DataSet;
import pl.mateusz.Pecunia.utils.JsonUtils;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
//            System.out.println(goldRates);
//            System.out.println(goldRates.size());
            jsonArray.put(goldRates);

            JSONObject jsonObject = new JSONObject();
            for (Object o : jsonArray.getJSONArray(0)) {
                GoldRate goldRate1 = new GoldRate();
                jsonObject.put("gold", o);
                goldRate1.setDataRate(Date.valueOf(jsonObject.getJSONObject("gold").getString("data")));
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

//        System.out.println(goldRateList.size());
        return goldRateList;
    }

    private String jsonString(String url) {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse clientResponse = webResource.accept("application/json")
                .get(ClientResponse.class);

        if(clientResponse.getStatus() !=200){
            System.out.println( "Cos poszło ni tak!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            throw new RuntimeException("Błąd pobrania... " + clientResponse.getStatus());
        }

        client.destroy();
        return clientResponse.getEntity(String.class);
    }


    /*
    Test cen metali
     */

    @Override
    public DataSet dataSet(String url, Integer limit) {
        String url1 = url + "limit=" + limit;
        DataSet dataSet = new DataSet();
        pl.mateusz.Pecunia.models.forms.metal.MetalPrice metalPrice = new pl.mateusz.Pecunia.models.forms.metal.MetalPrice();
        try {
            String dataSet1 = jsonString("https://www.quandl.com/api/v3/datasets/LBMA/SILVER.json?api_key=GU7pHR2TyK6qrZs7FsgH&limit=2");
//            System.out.println(dataSet1);
            JSONObject jsonObject = new JSONObject(dataSet1);

            metalPrice.setId(jsonObject.getJSONObject("dataset").getLong("id"));
            metalPrice.setDataSetCode(jsonObject.getJSONObject("dataset").getString("dataset_code"));
            metalPrice.setDataBaseCode(jsonObject.getJSONObject("dataset").getString("database_code"));
            metalPrice.setName((jsonObject.getJSONObject("dataset").getString("name")));
            metalPrice.setDescription(jsonObject.getJSONObject("dataset").getString("description"));
            metalPrice.setRefreshedAt(jsonObject.getJSONObject("dataset").getString("refreshed_at"));
            metalPrice.setNewestAvailableDate(jsonObject.getJSONObject("dataset").getString("newest_available_date"));
            metalPrice.setOldestAvailableDate(jsonObject.getJSONObject("dataset").getString("oldest_available_date"));

            List<String> columnNames = new ArrayList<>();
            for (Object o : jsonObject.getJSONObject("dataset").getJSONArray("column_names")) {
                columnNames.add(o.toString());
            }
            metalPrice.setColumnNames(columnNames);
            metalPrice.setFrequency(jsonObject.getJSONObject("dataset").getString("frequency"));
            metalPrice.setType(jsonObject.getJSONObject("dataset").getString("type"));
            metalPrice.setPremium(jsonObject.getJSONObject("dataset").getBoolean("premium"));



            System.out.println(JsonUtils.gsonPretty(metalPrice));
        }catch (Exception e) {
            System.out.println("błąd!!!!!!!!!!" + e.getMessage());
        }

//        System.out.println(metalPrice);


        dataSet.setMetalPrice(metalPrice);
        return new DataSet();
    }

    private String jsonStringApi(String url) {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse clientResponse = webResource.accept("application/json")
                .header("x-access-token", "goldapi-onpitykebb6fuy-io")
                .get(ClientResponse.class);

        if(clientResponse.getStatus() !=200){
            System.out.println( "Cos poszło ni tak!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            throw new RuntimeException("Błąd pobrania... " + clientResponse.getStatus());
        }

        client.destroy();
        return clientResponse.getEntity(String.class);
    }

    @Override
    public MetalPrice metalPrice(String metal, String currency) {

        try {
            String metalPrice = jsonStringApi("https://www.goldapi.io/api/" + metal + "/" + currency);
            return getGoldApiMetal(metalPrice);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public MetalPriceDate metalPriceDate(String metal, String currency, String date) {

        try {
            String metalPrice = jsonStringApi("https://www.goldapi.io/api/" + metal + "/" + currency + "/" + date.replace("-", ""));
            MetalPriceDate metalPriceDate = new MetalPriceDate();
            JSONObject jsonObject = new JSONObject(metalPrice);

            metalPriceDate.setDate(jsonObject.getString("date"));

            System.out.println(jsonObject);
            System.out.println("POWINNA byc data!!!!!!!!!!!  " + metalPriceDate.getDate());

            return metalPriceDate;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

        //TODO uzupełnić kod, pobieranie kursu metali z konkretnej daty
    }

    private MetalPrice getGoldApiMetal(String jsonMetalPrice) {
        MetalPrice metalPrice = new MetalPrice();
        JSONObject jsonObject = new JSONObject(jsonMetalPrice);

        metalPrice.setTimestamp(longToDate(jsonObject.getLong("timestamp")));
        metalPrice.setMetalCod(jsonObject.getString("metal"));
        metalPrice.setMetal(GoldApiCodeEnum.valueOf(metalPrice.getMetalCod()).getEn());
        metalPrice.setCurrency(jsonObject.getString("currency"));
        metalPrice.setExchange(jsonObject.getString("exchange"));
        metalPrice.setSymbol(jsonObject.getString("symbol"));
        metalPrice.setPrevClosePrice(jsonObject.getDouble("prev_close_price"));
        metalPrice.setOpenPrice(jsonObject.getDouble("open_price"));
        metalPrice.setLowPrice(jsonObject.getDouble("low_price"));
        metalPrice.setHighPrice(jsonObject.getDouble("high_price"));
        metalPrice.setOpenTime(longToDate(jsonObject.getLong("open_time")));
        metalPrice.setPrice(jsonObject.getDouble("price"));
        metalPrice.setCh(jsonObject.getDouble("ch"));
        metalPrice.setChp(jsonObject.getDouble("chp"));
        metalPrice.setAsk(jsonObject.getDouble("ask"));
        metalPrice.setBid(jsonObject.getDouble("bid"));


        return metalPrice;
    }

    private String longToDate(Long dateLong) {
        Date date = new Date(dateLong * 1000l);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
        return df.format(date);
    }


}
