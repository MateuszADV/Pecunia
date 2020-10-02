package pl.mateusz.Pecunia.services.HomeService;

import pl.mateusz.Pecunia.models.CodeParam;

import java.util.List;

public interface HomeService {
    List<String> currencyCode();
    List<String> gatParameterList(CodeParam codeParam);

    String code (List<String> codeList);

}
