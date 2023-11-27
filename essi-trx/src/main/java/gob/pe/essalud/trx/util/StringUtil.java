package gob.pe.essalud.trx.util;

import java.text.Normalizer;
import java.util.Random;

public class StringUtil {

    private Random random = new Random();

    public static String capitalize(final String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static boolean isNullOrEmpty(String text) {
        return (text == null || text.trim().isEmpty());
    }

    public static String getRandomNumber(Integer digits) {       
        String value = String.format("%0" + digits + "d", random.nextInt(10000));
        return value;
    }

    public static boolean equalsIgnoreSpecial(String c1, String c2) {
        boolean result = false;
        if (c1 != null && c2 != null) {
            c1 = replaceSpecialCharacters(c1.toUpperCase());
            c2 = replaceSpecialCharacters(c2.toUpperCase());
            result = c1.equals(c2);
        }
        return result;
    }

    public static boolean containsIgnoreSpecial(String c1, String c2) {
        boolean result = false;
        if (c1 != null && c2 != null) {
            c1 = replaceSpecialCharacters(c1.toUpperCase());
            c2 = replaceSpecialCharacters(c2.toUpperCase());
            result = c1.contains(c2);
        }
        return result;
    }

    private static String replaceSpecialCharacters(String original) {
        String cadenaNormalize = Normalizer.normalize(original, Normalizer.Form.NFD);
        String result = cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
        return result;
    }
}