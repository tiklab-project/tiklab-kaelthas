package io.tiklab.kaelthas.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtil {

    public static String getDataTimeNow(){
        // 获取当前日期
        LocalDateTime currentDate = LocalDateTime.now();

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 将日期格式化为字符串
        return currentDate.format(formatter);
    }
}
