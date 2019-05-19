package pl.mateusz.Pecunia.services.HomeService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.CodeParam;
import pl.mateusz.Pecunia.models.repositories.CodeParamRepository;
import pl.mateusz.Pecunia.services.countryService.CountryServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private CodeParamRepository codeParamRepository;
    private CountryServiceImpl countryServiceImpl;


    @Autowired
    public HomeServiceImpl(CodeParamRepository codeParamRepository, CountryServiceImpl countryServiceImpl) {
        this.codeParamRepository = codeParamRepository;
        this.countryServiceImpl = countryServiceImpl;
    }

    @Override
    public List<String> currencyCode() {
        CodeParam codeParam = codeParamRepository.findByWebName("index");
        JSONObject codeJson = new JSONObject(codeParam.getParameters());

        List<String > codelist = new ArrayList<>();
        for (Object code : codeJson.getJSONArray("code")) {
            codelist.add(code.toString());
        }
//        return codelist;

        return countryServiceImpl.codeCurrency(codelist);
    }
}
