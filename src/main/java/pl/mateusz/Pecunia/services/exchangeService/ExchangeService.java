package pl.mateusz.Pecunia.services.exchangeService;

import pl.mateusz.Pecunia.models.forms.Exchange;

import java.util.List;

public interface ExchangeService {
    Exchange exchange();

    Exchange exchange(List<String> listCode);
}
