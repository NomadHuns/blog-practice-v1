package shop.mtcoding.blog2.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class dateUtil {

    public static String format(Timestamp stamp) {
        LocalDateTime nowTime = stamp.toLocalDateTime();
        String nowStr = nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return nowStr;
    }

    public static Timestamp now() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}
