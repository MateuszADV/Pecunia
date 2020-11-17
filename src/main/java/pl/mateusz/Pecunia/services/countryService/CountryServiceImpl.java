package pl.mateusz.Pecunia.services.countryService;

import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.CurrencyCountryActiveView;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.dtos.*;
import pl.mateusz.Pecunia.models.forms.*;
import pl.mateusz.Pecunia.models.forms.enums.ContinentEnum;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyCountryActiveViewRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;
import pl.mateusz.Pecunia.models.repositories.NoteCountryViewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CountryServiceImpl implements CountryService {

    private CurrencyCountryActiveViewRepository currencyCountryActiveViewRepository;
    private CountryRepository countryRepository;
    private CurrencyRepository currencyRepository;
    private NoteCountryViewRepository noteCountryViewRepository;

    @Autowired
    public CountryServiceImpl(CurrencyCountryActiveViewRepository currencyCountryActiveViewRepository, CountryRepository countryRepository, CurrencyRepository currencyRepository,
                              NoteCountryViewRepository noteCountryViewRepository) {
        this.currencyCountryActiveViewRepository = currencyCountryActiveViewRepository;
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.noteCountryViewRepository = noteCountryViewRepository;
    }

    public CountryServiceImpl() {

    }

    @Override
    public CountryViewList countryViewList() {
        List<CurrencyCountryActiveView> countryCountryViews = currencyCountryActiveViewRepository.findAll();
        CountryViewList countryViewList = new CountryViewList();
        countryViewList.setCountryList(countryCountryViews);
        return countryViewList;
    }

    @Override
    public CountryDtoList countryDtoList() {
        List<Country> countrys = countryRepository.findAllByOrderByCountryEn();
        return getCountryDtoList(countrys);
    }

    @Override
    public ContinentResponse continentResponse() {
        ContinentResponse continentResponse = new ContinentResponse();
        List<CountryFromContinent> countryFromContinents = new ArrayList<>();

        for (ContinentEnum continent : ContinentEnum.values()) {
            countryFromContinents.add(new CountryFromContinent(continent.getNamePl(), countryViewDtos(continent.getNamePl())));

        }
        continentResponse.setContinents(countryFromContinents);

        return continentResponse;
    }

    @Override
    public ContinentResponse continentResponse(ContinentRequest request) {
        ContinentResponse continentResponse = new ContinentResponse();
        List<CountryFromContinent> countryFromContinents = new ArrayList<>();

        for (ContinentEnum continent : continentActive(request)) {
            countryFromContinents.add(new CountryFromContinent(continent.getNamePl(), countryViewDtos(continent.getNamePl())));

        }
        continentResponse.setContinents(countryFromContinents);

        return continentResponse;
    }

    private List<CountryViewDto> countryViewDtos(String continent) {
        List<CurrencyCountryActiveView> currencyCountryActiveViews = currencyCountryActiveViewRepository.findByContinent(continent);
        List<CountryViewDto> countryViewDtos = new ArrayList<>();

        for (CurrencyCountryActiveView currencyCountryActiveView : currencyCountryActiveViews) {
            countryViewDtos.add(new ModelMapper().map(currencyCountryActiveView, CountryViewDto.class));
        }
        return countryViewDtos;
    }

    private List<ContinentEnum> continentActive(ContinentRequest request) {
        List<ContinentEnum> continentActive = new ArrayList<>();

        if (BooleanUtils.isTrue(request.getEurope())) {
            continentActive.add(ContinentEnum.EUROPE);
        }
        if (BooleanUtils.isTrue(request.getAfrica())) {
            continentActive.add(ContinentEnum.AFRICA);
        }
        if (BooleanUtils.isTrue(request.getAsia())) {
            continentActive.add(ContinentEnum.ASIA);
        }
        if (BooleanUtils.isTrue(request.getAustralia())) {
            continentActive.add(ContinentEnum.AUSTRALIA_OCEANIA);
        }
        if (BooleanUtils.isTrue(request.getNorthAmerica())) {
            continentActive.add(ContinentEnum.NORTH_AMERICA);
        }
        if (BooleanUtils.isTrue(request.getSouthAmerica())) {
            continentActive.add(ContinentEnum.SOUTH_AMERICA);
        }
        if (BooleanUtils.isTrue(request.getAntarctica())) {
            continentActive.add(ContinentEnum.ANTARCTICA);
        }
//        if (BooleanUtils.isTrue(request.getAutonomousTerritories())) {
//            continentActive.add(ContinentEnum.AUTONOMOUS_TERRITORIES);
//        }
        return continentActive;
    }

    @Override
    public List<CurrencyDto> currencyFromCountryId(Long countryId) {
        List<Currency> currencys = currencyRepository.findByCountry_IdOrderByDataExchangeDesc(countryId);
        List<CurrencyDto> currencyDtos = new ArrayList<>();

        for (Currency currency : currencys) {
            currencyDtos.add(new ModelMapper().map(currency, CurrencyDto.class));
        }

        return currencyDtos;
    }

    @Override
    public List<CurrencyDto> currencyFromCountryId(Country countryId, String pattern) {
        List<Currency> currencys = currencyRepository.findByCountry_IdOrderByDataExchangeDesc(countryId, pattern.toUpperCase());
        List<CurrencyDto> currencyDtos = new ArrayList<>();

        for (Currency currency : currencys) {
            currencyDtos.add(new ModelMapper().map(currency, CurrencyDto.class));
        }

        return currencyDtos;
    }

    @Override
    public ContinentCountryCurrencysResponse continentCountryCurrencysResponse() {
        ContinentCountryCurrencysResponse continentCountryCurrencysResponse = new ContinentCountryCurrencysResponse();
        List<ContinentCountrys> continentCountrysList = new ArrayList<>();

        for (ContinentEnum continent : ContinentEnum.values()) {
            continentCountryCurrencys(continentCountrysList, continent);
        }
        continentCountryCurrencysResponse.setContinents(continentCountrysList);

        return continentCountryCurrencysResponse;
    }

    @Override
    public ContinentCountryCurrencysResponse continentCountryCurrencysResponse(ContinentRequest request) {
        ContinentCountryCurrencysResponse continentCountryCurrencysResponse = new ContinentCountryCurrencysResponse();
        List<ContinentCountrys> continentCountrysList = new ArrayList<>();

        for (ContinentEnum continent : continentActive(request)) {
            continentCountryCurrencys(continentCountrysList, continent);
        }
        continentCountryCurrencysResponse.setContinents(continentCountrysList);

        return continentCountryCurrencysResponse;
    }

    private void continentCountryCurrencys(List<ContinentCountrys> continentCountrysList, ContinentEnum continent) {
        List<CountryOfCurrency> countryOfCurrencyList = new ArrayList<>();
        List<Country> countryList = countryRepository.findByContinent(continent.getNamePl());

        for (Country country : countryList) {
            List<Currency> currencyList = currencyRepository.findByCountry_IdOrderByDataExchangeDesc(country.getId());
            List<CurrencyDto> currencyDtoList = new ArrayList<>();

            for (Currency currency : currencyList) {
                currencyDtoList.add(new ModelMapper().map(currency, CurrencyDto.class));
            }
            countryOfCurrencyList.add(new CountryOfCurrency(country.getCountryEn(),currencyDtoList));
        }
        continentCountrysList.add(new ContinentCountrys(continent.getNamePl(), countryOfCurrencyList));
    }

    @Override
    public CountryJsonDto countryJson(Long country_id) {
        Country country = countryRepository.findById(country_id).get();
        List<Currency> currencyList = currencyRepository.findByCountry_IdOrderByDataExchangeDesc(country_id);
        CountryJsonDto countryJsonDto = new ModelMapper().map(country, CountryJsonDto.class);

        List<CurrencyDto> currencyDtoList = new ArrayList<>();

        for (Currency currency : currencyList) {
            currencyDtoList.add(new ModelMapper().map(currency, CurrencyDto.class));
        }
        countryJsonDto.setCurrencys(currencyDtoList);
        return countryJsonDto;
    }


    //    @Override
//    public CountryDtoList CountryFromContinent(String continent) {
//        List<Country> countrys = countryRepository.findByContinent(continent);
//        return getCountryDtoList(countrys);
//    }
//
    private CountryDtoList getCountryDtoList(List<Country> countrys) {
        CountryDtoList countryDtoList = new CountryDtoList();
        List<CountryDto> countryDtos = new ArrayList<>();

        for (Country country : countrys) {
            countryDtos.add(new ModelMapper().map(country, CountryDto.class));
        }

        countryDtoList.setCountryDtoList(countryDtos);

        return countryDtoList;
    }

    @Override
    public CountryDto countryFromId(Long country_id) {
        Country country = countryRepository.findById(country_id).get();
        CountryDto countryDto = new ModelMapper().map(country, CountryDto.class);

        return countryDto;
    }

    /**
     * Metoda sprawdza czhy kod waluty jest prwidłowy
     */

    @Override
    public List<String> codeCurrency(List<String> code) {
        List<String> codeList = new ArrayList<>();

        for (String s : codeLengthCheckList(code)) {
            if (s != null) {
                codeList.add(s.toUpperCase());
            }
        }

        return codeList;
    }

    private String codeLengthCheck(String code) {
        if (code.length() == 3) {
            return code;
        }
        return null;
    }

    private List<String> codeLengthCheckList(List<String> codeList) {
        List<String> codeCheckedList = new ArrayList<>();

        for (String s : codeList) {
            if ( s != null && BooleanUtils.isTrue(checkCodeInsaidNotNumber(s))) {
                codeCheckedList.add(codeLengthCheck(s));
            }

        }
        return codeCheckedList;
    }

    private Boolean checkCodeInsaidNotNumber(String code) {
        Pattern pattern = Pattern.compile("[a-zA-Z]{3}");
        return pattern.matcher(code).matches();
    }

    @Override
    public Boolean saveCurrency(CurrencyDto currencyDto, String countryEN) {
        try {
            Country country = countryRepository.findByCountryEn(countryEN);
            Currency currency = (new ModelMapper().map(currencyDto, Currency.class));
            currency.setCountry(country);
            currencyRepository.save(currency);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
