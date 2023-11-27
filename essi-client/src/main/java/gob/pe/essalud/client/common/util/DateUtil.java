package gob.pe.essalud.client.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class DateUtil {

    public static Date currentDateLima() {
        ZoneId zoneId = ZoneId.of("America/Lima");
        ZonedDateTime zoneDateTime = ZonedDateTime.now(zoneId);
        return Date.from(zoneDateTime.toInstant());
    }

    public static Date currentDate() {
        Date currentDate = new Date();
        currentDate.setHours(0);
        currentDate.setMinutes(0);
        currentDate.setSeconds(0);
        return currentDate;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDate = LocalDate.parse(pBirthDate, formatter);
        return Period.between(birthDate, currentDate).getYears();
    }

    public static Date stringDateToDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(dateString);
    }

    public static String getFirstDayOfYear() {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.with(firstDayOfYear());
        return format(firstDay);
    }

    public static String getLastDayOfYear() {
        LocalDate now = LocalDate.now();
        LocalDate lastDay = now.with(lastDayOfYear());
        return format(lastDay);
    }

    public static String format(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(date);
    }

    public static String format(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static String fullFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:MM");
        return formatter.format(date);
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    public static Date stringToDate(String strDate, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(strDate);
    }

    public static long dateDiffInDays(Date minDate, Date maxDate) {
        long difference = maxDate.getTime() - minDate.getTime();
        long daysBetween = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
        return daysBetween;
    }
}
