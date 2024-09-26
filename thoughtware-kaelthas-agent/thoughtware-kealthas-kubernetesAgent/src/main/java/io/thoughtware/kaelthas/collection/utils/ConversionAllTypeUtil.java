package io.thoughtware.kaelthas.collection.utils;

import com.alibaba.fastjson.JSONObject;
import io.thoughtware.kaelthas.history.model.History;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConversionAllTypeUtil {

    public static String getDataTimeNow(){
        // 获取当前日期
        LocalDateTime currentDate = LocalDateTime.now();

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 将日期格式化为字符串
        return currentDate.format(formatter);
    }

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

    public static List<Map<String, Object>> getMapList(List<History> entityList) {
        return entityList.stream().map(history -> {
            Map<String, Object> map = new HashMap<>();
            String uuid = RandomStringUtils.randomAlphanumeric(12);
            map.put("id", uuid);
            map.put("host_id", history.getHostId());
            map.put("monitor_id", history.getMonitorId());
            map.put("report_data", history.getReportData());
            map.put("gather_time", history.getGatherTime());
            return map;
        }).toList();
    }

    /**
     * 正则匹配变量 {{}} 替换里面的值
     */
    public String replaceParamValue(String text, JSONObject variableJson) {
        if (text == null || variableJson == null) {
            // 添加空值检查
            return text;
        }

        String string = text.replaceAll("\\[", "").replaceAll("]", "");

        Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}\\}");
        Matcher matcher = pattern.matcher(string);
        StringBuilder result = new StringBuilder();

        // 查找所有匹配,替换为值
        while(matcher.find()){
            String varName = matcher.group(1);
            String varValue = variableJson.getString(varName);

            if (varValue != null) {
                // 变量存在于 variableJson 中
                matcher.appendReplacement(result, varValue);
            } else {
                // 变量不存在，保持原样
                matcher.appendReplacement(result, matcher.group(0));
            }
        }

        matcher.appendTail(result);

        return result.toString();
    }

    /**
     * 获取{{}}中的表达式
     */
    public Set<String> getFunctionParam(String function) {
        if (function == null) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}\\}");
        Matcher matcher = pattern.matcher(function);
        Set<String> stringList = new HashSet<>();
        while (matcher.find()) {
            stringList.add(matcher.group(1));
        }
        return stringList;
    }


    /**
     * 正则匹配变量获取 () 里面的值
     */
    public static String getParamValue(String text) {
        if (text == null) {
            // 添加空值检查
            return null;
        }

        Pattern pattern = Pattern.compile("\\((.+?)\\)");
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()){
            return matcher.group(1);
        }

        return null;
    }
}
