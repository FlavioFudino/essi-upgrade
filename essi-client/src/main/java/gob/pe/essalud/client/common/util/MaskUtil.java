package gob.pe.essalud.client.common.util;

public class MaskUtil {

    private static final int DIVISOR = 2;
    private static final String LEFT_PAD_CHARACTER = "*";

    public static String MaskString(String value, int startPos) {
        String result = "";

        if (StringUtil.isNullOrEmpty(value))
            return result;

        int middleDigitPos = (value.length() / DIVISOR);
        int partDigits = (middleDigitPos / DIVISOR);
        int middlePos = (startPos + (partDigits));
        int endPos = (middlePos + partDigits);
        int endPosRest = (middlePos + partDigits);
        String restCharacters = value.substring((endPosRest + partDigits));

        String s1 = value.substring(startPos, (startPos + partDigits));
        String s2 = StringUtil.leftPad(LEFT_PAD_CHARACTER, partDigits);
        String s3 = value.substring(endPos, (endPos + partDigits));
        String s4 = restCharacters;

        result = result
                .concat(s1)
                .concat(s2)
                .concat(s3)
                .concat(s4);

        int firstDigitsLength = restCharacters.length();

        if (firstDigitsLength > 2) {
            firstDigitsLength = 2;
            restCharacters = result.substring(firstDigitsLength);
        }

        result = StringUtil.leftPad("*",firstDigitsLength).concat(restCharacters);

        return result;
    }

    private static String MaskEmailString(String value, int startPos, int maxDigits) {
        String result = "";

        if (StringUtil.isNullOrEmpty(value))
            return result;

        if (maxDigits == 0)
            maxDigits = value.length();

        int partDigits = (maxDigits / DIVISOR);
        int middlePos = (startPos + (partDigits));
        int endPos = (middlePos + partDigits);
        int endPosRest = (middlePos + partDigits);
        String restCharacters = value.substring((endPosRest + partDigits));

        String s1 = value.substring(startPos, (startPos + partDigits));
        String s2 = StringUtil.leftPad(LEFT_PAD_CHARACTER, partDigits);
        String s3 = value.substring(endPos, (endPos + partDigits));
        String s4 = MaskString(restCharacters,0);

        int mailPos = s3.indexOf("@") + 1;
        int mailLength = (s3.length() - mailPos);
        String mailPad = StringUtil.leftPad("*",mailLength);
        s3 = s3.substring(0,mailPos).concat(mailPad);

        result = result
                .concat(s1)
                .concat(s2)
                .concat(s3)
                .concat(s4);

        int firstDigitsLength = 2;
        restCharacters = result.substring(firstDigitsLength);
        result = StringUtil.leftPad("*",firstDigitsLength).concat(restCharacters);

        return result;
    }

    public static String MaskPhone(String value) {
        return MaskUtil.MaskString(value, 0);
    }

    public static String MaskEmail(String value) {
        int startPos = 0;
        int endPos = (value.indexOf('@') - 1);
        return MaskUtil.MaskEmailString(value, startPos, endPos);
    }

}
