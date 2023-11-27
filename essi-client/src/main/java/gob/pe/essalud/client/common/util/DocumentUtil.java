package gob.pe.essalud.client.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class DocumentUtil {

    private static final Logger logger = LogManager.getLogger(DocumentUtil.class);

    private static int DNI_LENGTH = 8;
    private static String LETTERS = "KABCDEFGHIJ";
    private static String NUMBERS = "67890112345";

    /**
     * Convierte el caracter verificador de DNI a número
     * Si el valor es númerico se devuelve sin cambios
     * Si el valor es una letra, se cambia a su equivalente numérico
     * Si no está en la lista, se devuelve sin cambios
     *
     * @return character equivalente
     **/
    private static String dniVerificationLetterToNumber(String character) {
        if (StringUtils.isEmpty(character.trim()))
            return character;

        if (character.length() > 1) {
            throw new IllegalArgumentException("El valor del caracter de verificación debe ser un solo caracter");
        }
        if (Character.isLetter(character.trim().charAt(0))) {
            int index = LETTERS.indexOf(character.toUpperCase());
            if (index >= 0)
                character = Character.toString(NUMBERS.charAt(index));
        }
        return character;
    }

    /** Validar el digito verificador por el indice correspondiente **/
    private static boolean dniVerificationIsDigitValidByIndex(int index, int digitExpected) {
        if (index < 0)
            return false;

        int digitFound = Integer.parseInt(Character.toString(NUMBERS.charAt(index - 1)));
        return (digitExpected == digitFound);
    }

    public static boolean checkCharacterVerifier(String dni, String character) {
        logger.info(String.format("* checkCharacterVerifier(%s, %s)", dni, character));

        if (StringUtil.isNullOrEmpty(dni))
            throw new IllegalArgumentException("El DNI es obligatorio");

        if (StringUtil.isNullOrEmpty(character))
            throw new IllegalArgumentException("El caracter verificador es obligatorio");

        if (dni.length() != DNI_LENGTH)
            throw new IllegalArgumentException("El DNI no tiene 8 caracteres");

        int digitExpected = Integer.parseInt(dniVerificationLetterToNumber(character));
        int acumulator = 0;
        int index = 0;

        List<Integer> data = Arrays.asList(3, 2, 7, 6, 5, 4, 3, 2);
        for (Integer datum : data) {
            int number = Character.getNumericValue(dni.charAt(index++));
            acumulator += number * datum;
        }

        int residuo = acumulator % 11;
        int nuevoResiduo = (11 - residuo);

        if (nuevoResiduo == 11)
            nuevoResiduo = 0;

        nuevoResiduo += 1;

        boolean isValid = dniVerificationIsDigitValidByIndex(nuevoResiduo,digitExpected);
        return isValid;
    }

}
