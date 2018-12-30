package pl.mateusz.Pecunia.countryService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.CountryCountryView;
import pl.mateusz.Pecunia.models.dtos.CountryDto;
import pl.mateusz.Pecunia.models.dtos.CountryDtoList;
import pl.mateusz.Pecunia.models.forms.CountryViewList;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CountryViewRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private CountryViewRepository countryViewRepository;
    private CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryViewRepository countryViewRepository, CountryRepository countryRepository) {
        this.countryViewRepository = countryViewRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryViewList countryViewList() {
        List<CountryCountryView> countryCountryViews = countryViewRepository.findAll();
        CountryViewList countryViewList = new CountryViewList();
        countryViewList.setCountryList(countryCountryViews);
        return countryViewList;
    }

    @Override
    public CountryDtoList countryDtoList() {
        List<Country> countrys = countryRepository.findAll();
        CountryDtoList countryDtoList = new CountryDtoList();
        List<CountryDto> countryDtos = new ArrayList<>();

        for (Country country : countrys) {
            countryDtos.add(new ModelMapper().map(country, CountryDto.class));
        }

        countryDtoList.setCountryDtoList(countryDtos);

        return countryDtoList;
    }
}
