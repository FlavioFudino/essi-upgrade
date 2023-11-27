package gob.pe.essalud.client.common.util;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private StringUtil() {
    }

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

    public static boolean hasLength(String text, int length) {
        if (isNullOrEmpty(text)) return false;
        return text.length() == length;
    }

    public static boolean hasLengthBetween(String text, int minLength, int maxLength) {
        if (isNullOrEmpty(text)) return false;
        return text.length() >= minLength && text.length() <= maxLength;
    }

    public static boolean hasLengthGreaterOrEqualThan(String text, int minLength) {
        if (isNullOrEmpty(text)) return false;
        return text.length() >= minLength;
    }

    public static boolean hasLengthLessOrEqualThan(String text, int maxLength) {
        if (isNullOrEmpty(text)) return false;
        return text.length() <= maxLength;
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
        return cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
    }

    public static String leftPad(String padString, int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += padString;
        }
        return result;
    }

    public static String reverse(String str) {
        if (isNullOrEmpty(str)) return null;
        return new StringBuffer(str).reverse().toString();
    }

    public static boolean isStringInMatcher(String strFind, String strDataPattern) {
        Pattern p = Pattern.compile(strDataPattern);
        Matcher m = p.matcher(strFind);
        boolean isFound = m.find();
        return isFound;
    }
}