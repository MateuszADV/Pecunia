package pl.mateusz.Pecunia.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.codec.Utf8;

import java.nio.charset.StandardCharsets;
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
        String text = new String(Base64.encodeBase64String(encodetext.getBytes(StandardCharsets.UTF_8)));
        return text;
    }

    public static String decodeData(String decodeText) {
        if (decodeText == "" || decodeText.equals(null)) {
             return null;
        }

        String text = new String(Base64.decodeBase64(decodeText.getBytes(StandardCharsets.UTF_8)));
        return text;
    }


}
