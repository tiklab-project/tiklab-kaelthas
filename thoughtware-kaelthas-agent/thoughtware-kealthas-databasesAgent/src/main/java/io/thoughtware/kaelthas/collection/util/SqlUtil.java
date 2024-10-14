package io.thoughtware.kaelthas.collection.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SqlUtil {

    public static Double parseVersionToNumber(String versionString) {
        if (versionString == null || versionString.isEmpty()) {
            return 0.0;
        }

        // 提取主要和次要版本号
        String[] versionParts = versionString.split("\\.");
        if (versionParts.length < 2) {
            return 0.0;
        }

        try {
            int majorVersion = Integer.parseInt(versionParts[0]);
            int minorVersion = Integer.parseInt(versionParts[1]);

            // 使用主要版本和次要版本构造一个整数版本号，例如 8.0 -> 8000
            return majorVersion + minorVersion*0.1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static String getDataTimeNow(){
        // 获取当前日期
        LocalDateTime currentDate = LocalDateTime.now();

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 将日期格式化为字符串
        return currentDate.format(formatter);
    }

    public static String date(int type) {
        switch (type) {
            case 2 -> {
                return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            }
            case 3 -> {
                return new SimpleDateFormat("HH:mm:ss").format(new Date());
            }
            case 4 -> {
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                return "[" + format + "]" + "  ";
            }
            case 5 -> {
                return new SimpleDateFormat("HH:mm").format(new Date());
            }
            case 6 -> {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
            }
            default -> {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            }
        }
    }
}
