package pl.mateusz.Pecunia.countryService;

import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.Country;
import pl.mateusz.Pecunia.models.CountryCurrencyView;
import pl.mateusz.Pecunia.models.Currency;
import pl.mateusz.Pecunia.models.dtos.CountryDto;
import pl.mateusz.Pecunia.models.dtos.CountryDtoList;
import pl.mateusz.Pecunia.models.dtos.CountryViewDto;
import pl.mateusz.Pecunia.models.dtos.CurrencyDto;
import pl.mateusz.Pecunia.models.forms.*;
import pl.mateusz.Pecunia.models.forms.enums.ContinentEnum;
import pl.mateusz.Pecunia.models.repositories.CountryRepository;
import pl.mateusz.Pecunia.models.repositories.CountryCurencyViewRepository;
import pl.mateusz.Pecunia.models.repositories.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private CountryCurencyViewRepository countryCurrencyViewRepository;
    private CountryRepository countryRepository;
    private CurrencyRepository currencyRepository;

    @Autowired
    public CountryServiceImpl(CountryCurencyViewRepository countryCurrencyViewRepository, CountryRepository countryRepository, CurrencyRepository currencyRepository) {
        this.countryCurrencyViewRepository = countryCurrencyViewRepository;
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public CountryViewList countryViewList() {
        List<CountryCurrencyView> countryCountryViews = countryCurrencyViewRepository.findAll();
        CountryViewList countryViewList = new CountryViewList();
        countryViewList.setCountryList(countryCountryViews);
        return countryViewList;
    }

    @Override
    public CountryDtoList countryDtoList() {
        List<Country> countrys = countryRepository.findAllByOrderById();
        CountryDtoList countryDtoList = new CountryDtoList();
        List<CountryDto> countryDtos = new ArrayList<>();

        for (Country country : countrys) {
            countryDtos.add(new ModelMapper().map(country, CountryDto.class));
        }

        countryDtoList.setCountryDtoList(countryDtos);

        return countryDtoList;
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

        System.out.println(continentResponse);
        return continentResponse;
    }

    private List<CountryViewDto> countryViewDtos(String continent) {
        List<CountryCurrencyView> countryCurrencyViews = countryCurrencyViewRepository.findByContinent(continent);
        List<CountryViewDto> countryViewDtos = new ArrayList<>();

        System.out.println("powinien byćkontynent: " + continent);
        for (CountryCurrencyView countryCurrencyView : countryCurrencyViews) {
            countryViewDtos.add(new ModelMapper().map(countryCurrencyView, CountryViewDto.class));
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
        if (BooleanUtils.isTrue(request.getAutonomousTerritories())) {
            continentActive.add(ContinentEnum.AUTONOMOUS_TERRITORIES);
        }
        return continentActive;
    }

    @Override
    public List<CurrencyDto> curencyFromCountryId(Long currencyId) {
        List<Currency> currencys = currencyRepository.findByCountry_IdOrderByDataExchangeDesc(currencyId);
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
}
