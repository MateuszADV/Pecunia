package pl.mateusz.Pecunia.services.HomeService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.CodeParam;
import pl.mateusz.Pecunia.models.repositories.CodeParamRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private CodeParamRepository codeParamRepository;

    @Autowired
    public HomeServiceImpl(CodeParamRepository codeParamRepository) {
        this.codeParamRepository = codeParamRepository;
    }

    @Override
    public List<String> currencyCode() {
        CodeParam codeParam = codeParamRepository.findByWebName("index");
        JSONObject codeJson = new JSONObject(codeParam.getParameters());

        List<String > codelist = new ArrayList<>();
        for (Object code : codeJson.getJSONArray("code")) {
            codelist.add(code.toString());
        }
        return codelist;
    }
}
