package gob.pe.essalud.trx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_FORMAT_HHMMSS = "dd/MM/yyyy HH:mm:ss";

    public static Date currentDateLima() {
        ZoneId zoneId = ZoneId.of("America/Lima");
        ZonedDateTime zoneDateTime = ZonedDateTime.now(zoneId);
        return Date.from(zoneDateTime.toInstant());
    }

    public static int calculateAge(Date pBirthDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = pBirthDate.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        if (birthDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    public static int calculateAge(String pBirthDate) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate birthDate = LocalDate.parse(pBirthDate, formatter);
        return Period.between(birthDate, currentDate).getYears();
    }

    public static Date stringDateToDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.parse(dateString);
    }

    public static String format(Date date, String format) {
        if (date == null)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static String fullFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_HHMMSS);
        return formatter.format(date);
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static long dateDiffInSeconds(Date first, Date second) {
        return (first.getTime() - second.getTime()) / 1000;
    }
}
