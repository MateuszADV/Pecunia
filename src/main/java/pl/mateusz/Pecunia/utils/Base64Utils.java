package pl.mateusz.Pecunia.utils;

import org.apache.commons.codec.binary.Base64;

import java.util.UUID;

public class Base64Utils {

    public static String uniqueCustomerId() {
        UUID customerId = UUID.randomUUID();
        String customerUniqueId = customerId.toString().replace("-", "");
        return customerUniqueId;
    }

    public static String encodeString(String encodetext) {
        if (encodetext.equals(null)) {
            return null;
        }
        return new String(Base64.encodeBase64(encodetext.getBytes()));
    }

    public static String decodeData(String decodeText) {
        if (decodeText == "" || decodeText.equals(null)) {
             return null;
        }
        return new String(Base64.decodeBase64(decodeText.getBytes()));
    }


}
