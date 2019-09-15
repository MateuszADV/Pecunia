package pl.mateusz.Pecunia.utils;

import org.apache.commons.codec.binary.Base64;

import java.util.UUID;

public class CustomerUtils {

    public static String uniqueCustomerId() {
        UUID customerId = UUID.randomUUID();
        String customerUniqueId = customerId.toString().replace("-", "");
        return customerUniqueId;
    }

    public static String encodeData(String encodetext) {
        if (encodetext.equals(null)) {
            return null;
        }

        String encodeTextCustomer = new String(Base64.encodeBase64(encodetext.getBytes()));
        return encodeTextCustomer;
    }

    public static String decodeData(String decodeText) {
        if (decodeText.equals(null)) {
             return null;
        }

        String decodeTextCustomer = new String(Base64.decodeBase64(decodeText.getBytes()));
        return decodeTextCustomer;
    }
}
