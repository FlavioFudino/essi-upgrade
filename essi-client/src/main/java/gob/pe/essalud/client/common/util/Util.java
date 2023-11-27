package gob.pe.essalud.client.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class Util {
    private static final Logger logger = LogManager.getLogger(Util.class);

    Util() {
        super();
    }

    public static String addOneDay(String date) {
        String nextDate = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter).plusMonths(1);
            String dd = String.valueOf(localDate.getDayOfMonth());
            dd = dd.length() == 1 ? "0" + dd : dd;
            String mm = String.valueOf(localDate.getMonthValue());
            mm = mm.length() == 1 ? "0" + mm : mm;
            String yy = String.valueOf(localDate.getYear());

            nextDate = dd + "/" + mm + "/" + yy;
        } catch (Exception e) {
            logger.info(e);
        }
        return nextDate;
    }

    public static Double getNumberFormat(Double number) {
        Double outNumber = 0.0;
        DecimalFormat df2;
        try {
            Locale currentLocale = Locale.getDefault();
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
            otherSymbols.setDecimalSeparator('.');
            df2 = new DecimalFormat(".##", otherSymbols);
            outNumber = Double.parseDouble(df2.format(number));
        } catch (Exception e) {
            logger.info(e);
        }
        return outNumber;
    }

    public static Integer integerTryParse(String obj) {
        Integer retVal = null;
        try {
            retVal = Integer.parseInt(obj);
        } catch (NumberFormatException nfe) {
            logger.error(nfe.getMessage());
            retVal = null;
        }
        return retVal;
    }

    public static Double doubleTryParse(String obj) {
        Double value = null;
        try {
            value = Double.parseDouble(obj);
        } catch (NumberFormatException nfe) {
            logger.error(nfe.getMessage());
            value = null;
        }
        return value;
    }

    public static String mapTojson(Map<?, ?> mapa) {
        try {
            ObjectMapper mapperObj = new ObjectMapper();
            mapperObj.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapperObj.writeValueAsString(mapa);
        } catch (JsonProcessingException e) {
            logger.info(e);
            return null;
        }
    }

    public static String objectToJson(Object o) {
        String jsonInString = null;
        try {
            ObjectMapper mapperObj = new ObjectMapper();
            mapperObj.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapperObj.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            jsonInString = mapperObj.writeValueAsString(o);
        } catch (Exception e) {
            logger.info(e);
        }
        return jsonInString;
    }

    public static <T> T mapToObject(Class<T> type, Map<?, ?> mapa) {
        try {
            ObjectMapper mapperObj = new ObjectMapper();
            mapperObj.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            String jsonString = mapTojson(mapa);
            return mapperObj.readValue(jsonString, type);
        } catch (Exception e) {
            logger.info(e);
            return null;
        }
    }

    public static <T> T objectToObject(Class<T> type, Object o) {
        try {
            ObjectMapper mapperObj = new ObjectMapper();
            mapperObj.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapperObj.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            mapperObj.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
            String jsonString = objectToJson(o);
            return mapperObj.readValue(jsonString, type);
        } catch (Exception e) {
            logger.info(e);
            return null;
        }
    }

    public static <T> List<T> listObjectToListObject(Class<T> type, Iterable<?> inList) {
        ArrayList<T> outList = new ArrayList<>();
        try {
            for (Object o : inList) {
                T t = objectToObject(type, o);
                outList.add(t);
            }
        } catch (Exception e) {
            logger.info(e);
        }
        return outList;
    }

    public static <T> Set<T> listObjectToListObject2(Class<T> type, Iterable<?> inList) {
        Set<T> outList = new HashSet<T>();
        try {
            for (Object o : inList) {
                T t = objectToObject(type, o);
                outList.add(t);
            }
        } catch (Exception e) {
            logger.info(e);
        }
        return outList;
    }

    public static <T> List<T> listMapToListObject(Class<T> type, List<?> list) {
        List<T> lista = new ArrayList<>();
        try {
            if (list == null) {
                return lista;
            }
            ObjectMapper mapperObj = new ObjectMapper();
            mapperObj.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
            for (int i = 0; i < list.size(); i++) {
                Map<?, ?> m = (Map<?, ?>) list.get(i);
                T t = Util.mapToObject(type, m);
                lista.add(t);
            }
        } catch (Exception e) {
            logger.info(e);
        }
        return lista;
    }

    public static Boolean isNull(Object type) {
        Boolean bol = false;
        try {
            Field[] fields = type.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (f.isSynthetic()) {
                    continue;
                }
                Object value = f.get(type);
                if (value == null) {
                    return false;
                }
            }
            bol = true;
        } catch (Exception e) {
            bol = false;
        }
        return bol;
    }


    public static <T> List<T> getListFromMapObject(Object obj, Class<T> c) {
        List<T> lista = new ArrayList<>();
        try {
            if (obj != null) {
                if (obj instanceof List<?>) {
                    lista = Util.listMapToListObject(c, (ArrayList<?>) obj);
                } else {
                    lista.add(Util.mapToObject(c, (Map<?, ?>) obj));
                }
            }
        } catch (Exception e) {
            logger.info(e);
        }
        return lista;
    }

    public static Optional<Object> getOptional(Object o) {
        return Optional.of(o);
    }
}