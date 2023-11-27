package gob.pe.essalud.client.common.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;

public final class ArrayUtil {
    public static boolean allNotEmpty(String... data) {
        return Arrays.stream(data).noneMatch(StringUtils::isEmpty);
    }
}
