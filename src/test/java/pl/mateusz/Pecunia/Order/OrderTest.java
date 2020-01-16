package pl.mateusz.Pecunia.Order;

import org.junit.Test;
import pl.mateusz.Pecunia.services.OrderService.OrderServiceImpl;
import pl.mateusz.Pecunia.services.countryService.CountryServiceImpl;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class OrderTest {

    private CountryServiceImpl countryService = new CountryServiceImpl();
    private OrderServiceImpl orderService = new OrderServiceImpl();


    @Test
    public void nextNumberOrderTest() {

    }
}
