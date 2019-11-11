package pl.mateusz.Pecunia.utils;

public class PecuniaUtils {

    public Double stringToDouble(String value) {
        try {
            return Double.valueOf(value);
        }catch (Exception e) {
            return 0d;
        }
    }

    public Integer stringToInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
