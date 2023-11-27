package gob.pe.essalud.client.common.util;

public class NumberUtil {
    public static int truncate(int value) {
        double result = 0;
        if (value < 0) {
            result = Math.ceil(value);
        } else {
            result = Math.floor(value);
        }
        return (int)result;
    }

    public static double truncate(double value) {
        double result = 0;
        if (value < 0) {
            result = Math.ceil(value);
        } else {
            result = Math.floor(value);
        }
        return result;
    }
}
