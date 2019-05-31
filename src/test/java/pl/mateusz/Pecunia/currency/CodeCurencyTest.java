package pl.mateusz.Pecunia.currency;

import org.junit.Test;
import pl.mateusz.Pecunia.services.countryService.CountryServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class CodeCurencyTest {

    private CountryServiceImpl countryService = new CountryServiceImpl();


    @Test
    public void checkCorectCodeCurrency() {
//        given
        List<String> code = Arrays.asList("usd", "EuR", "Cad", "THB");

//        when
        List<String> codeResult = countryService.codeCurrency(code);

//        then
        List<String> codeCorrect = Arrays.asList("USD", "EUR", "CAD", "THB");
        assertEquals(codeCorrect, codeResult);

    }

    @Test
    public void checkCorectCodeCurrencyLength() {
//        given
        List<String> code = Arrays.asList("usd", "EuR", "Cad", "THB", "asdf", "JPAS");

//        when
        List<String> codeResult = countryService.codeCurrency(code);

//        then
        List<String> codeCorrect = Arrays.asList("USD", "EUR", "CAD", "THB");
        assertEquals(codeCorrect, codeResult);
    }

    @Test
    public void checkCodeCourrencyNotInsideIsNumber() {
        Pattern pattern = Pattern.compile("[a-zA-Z]{3}");

        assertTrue(pattern.matcher("ABS").matches());
        assertTrue(pattern.matcher("asd").matches());
        assertFalse(pattern.matcher("as8").matches());
    }

    @Test
    public void checkCodeCurrencyNotNumber() {

//        given
        List<String> code = Arrays.asList("usd", "EuR", "Cad", "THB", "asdf", "JPAS", "Ru4", "RUB");

//        when
        List<String> codeResult = countryService.codeCurrency(code);

//        then
        List<String> codeCorrect = Arrays.asList("USD", "EUR", "CAD", "THB","RUB");
        assertEquals(codeCorrect, codeResult);
    }

    @Test
    public void checkCodeCurrency_WhereCodeIsEmpty() {

//        given
        List<String> code = Arrays.asList("usd", "EuR", "Cad", "THB", "asdf", "JPAS", "Ru4", "RUB", "   ");

//        when
        List<String> codeResult = countryService.codeCurrency(code);

//        then
        List<String> codeCorrect = Arrays.asList("USD", "EUR", "CAD", "THB","RUB");
        assertEquals(codeCorrect, codeResult);
    }

    @Test
    public void checkCodeCurrency_WhereCodeIsNull() {

//        given
        List<String> code = Arrays.asList("usd", "EuR", "Cad", "THB", "asdf", "JPAS", "Ru4", "RUB", null);

//        when
        List<String> codeResult = countryService.codeCurrency(code);

//        then
        List<String> codeCorrect = Arrays.asList("USD", "EUR", "CAD", "THB","RUB");
        assertEquals(codeCorrect, codeResult);
    }

}
