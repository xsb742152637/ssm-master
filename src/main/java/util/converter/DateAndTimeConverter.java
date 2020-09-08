package util.converter;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Comment：LocalDateTime、LocalDate、Long、Date、String 相互转换
 * Created by IntelliJ IDEA.
 * User: xie
 * Date: 2020/8/4 11:25
 */
public class DateAndTimeConverter {
    public static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static Long getLong(Date date){
        return date.getTime();
    }
    public static Long getLong(LocalDate date){
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    public static Long getLong(LocalDateTime datetime){
        return datetime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    public static String getDateString(Long date){
        return getDateString(getLocalDate(date));
    }
    public static String getDateString(Timestamp date){
        return getDateString(getLocalDate(date));
    }
    public static String getDateString(LocalDate date){
        return date.format(df);
    }
    public static String getDateString(LocalDateTime date){
        return date.format(df);
    }

    public static String getDateTimeString(Long date){
        if(date == null){
            return "";
        }
        return getDateTimeString(getLocalDateTime(date));
    }
    public static String getDateTimeString(Timestamp date){
        if(date == null){
            return "";
        }
        return getDateTimeString(getLocalDateTime(date));
    }
    public static String getDateTimeString(LocalDateTime datetime){
        if(datetime == null){
            return "";
        }
        return datetime.format(dtf);
    }


    public static Date getDate(Long date){
        return new Date(date);
    }
    public static Date getDate(String date){
        return Date.from(LocalDateTime.parse(date,dtf).atZone(ZoneId.systemDefault()).toInstant());
    }
    public static Date getDate(LocalDate date){
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    public static Date getDate(LocalDateTime datetime){
        return Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Timestamp getTimestamp(Long data){
        return getTimestamp(getLocalDateTime(data));
    }
    public static Timestamp getTimestamp(String data){
        if(data.length() == 19){
            getTimestamp(getLocalDateTime(data));
        }else if(data.length() >= 10){
            data = data.substring(0, 10);
            return getTimestamp(getLocalDate(data));
        }
        return null;
    }
    public static Timestamp getTimestamp(LocalDate data){
        return getTimestamp(getLocalDateTime(data));
    }
    public static Timestamp getTimestamp(LocalDateTime datetime){
        return Timestamp.valueOf(datetime);
    }

    public static LocalDate getLocalDate(Long date){
        return getLocalDate(getLocalDateTime(date));
    }
    public static LocalDate getLocalDate(String date){
        return LocalDate.parse(date,df);
    }
    public static LocalDate getLocalDate(Date date){
        return getLocalDate(getLocalDateTime(date));
    }
    public static LocalDate getLocalDate(Timestamp datetime){
        return getLocalDate(getLocalDateTime(datetime));
    }
    public static LocalDate getLocalDate(LocalDateTime datetime){
        return datetime.toLocalDate();
    }


    public static LocalDateTime getLocalDateTime(Long dateTime){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault());
    }
    public static LocalDateTime getLocalDateTime(String dateTime){
        return LocalDateTime.parse(dateTime,dtf);
    }
    public static LocalDateTime getLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
    public static LocalDateTime getLocalDateTime(Timestamp datetime){
        return datetime.toLocalDateTime();
    }
    public static LocalDateTime getLocalDateTime(LocalDate date){
        return LocalDateTime.of(date, LocalTime.parse("00:00:00"));
    }
}
