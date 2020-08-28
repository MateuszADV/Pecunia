package pl.mateusz.Pecunia.services.exchangeService;

import pl.mateusz.Pecunia.models.forms.Exchange;
import pl.mateusz.Pecunia.models.GoldRate;
import pl.mateusz.Pecunia.models.forms.goldApi.GoldApi;
import pl.mateusz.Pecunia.models.forms.metal.DataSet;

import java.util.List;

public interface ExchangeService {
    Exchange exchange();

    Exchange exchange(List<String> listCode);
    List<GoldRate> goldRate();

    DataSet dataSet(String url, Integer limit);

    GoldApi metalPrice(String metal, String currency);
    GoldApi metalPrice(String metal, String currency, String date);
}
