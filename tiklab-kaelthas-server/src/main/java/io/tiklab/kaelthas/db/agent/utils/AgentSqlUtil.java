package io.tiklab.kaelthas.db.agent.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AgentSqlUtil {

    //返回版本号
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

    //拼接插入的SQL
    public static String getBatchInsertSql(String tableName, List<Map<String, Object>> dataList) {
        StringBuffer sb = new StringBuffer();
        StringBuilder valueSb = new StringBuilder();
        valueSb.append(" values (");
        sb.append("insert into ");
        sb.append(tableName);
        sb.append(" (");
        for (int i = 0, size = dataList.size(); i < size; i++) {
            Map<String, Object> dataObj = dataList.get(i);
            Set<String> keySet =  dataObj.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                if (i == 0){
                    sb.append(key);
                    if (i != 0){
                        sb.append(")");
                    }else {
                        sb.append(",");
                    }
                }
                valueSb.append("'");
                valueSb.append(dataObj.get(key));
                valueSb.append("',");
            }
            if (i == size - 1){
                sb.append(") ");
                valueSb.append(")");
            }else {
                valueSb.append("),(");
            }
        }
        sb.append(valueSb);
        return sb.toString().replace(",)", ")");
    }

    //判断是否是1分钟,5分钟,15分钟的整除
    public static boolean isDivisible(String dateTimeString, int minuteNum) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        int timeInSeconds = dateTime.getMinute() * 60 + dateTime.getSecond();
        return timeInSeconds % (minuteNum * 60) == 0;
    }
}
