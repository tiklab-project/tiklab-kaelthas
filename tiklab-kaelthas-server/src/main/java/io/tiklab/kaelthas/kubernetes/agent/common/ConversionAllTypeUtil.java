package io.tiklab.kaelthas.kubernetes.agent.common;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.kaelthas.history.model.History;
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
