package io.tiklab.kaelthas.util;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.exception.ApplicationException;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ConversionScriptsUtils {

    public ScriptEngine getScriptEngine() throws ScriptException {
        ScriptEngineManager engineManager = new ScriptEngineManager();

        ScriptEngine engine = engineManager.getEngineByName("JavaScript");

        InputStream scriptStream = getClass().getClassLoader().getResourceAsStream("static/script.js");

        if (scriptStream == null) {
            throw new ApplicationException("Script not found in JAR");
        }

        InputStreamReader reader = new InputStreamReader(scriptStream, StandardCharsets.UTF_8);

        engine.eval(reader);
        return engine;
    }

    /**
     * 正则匹配变量 {{}} 替换里面的值
     */
    public String replaceValue(String text, JSONObject variableJson) {
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
    public Set<String> getFunctionList(String function) {
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
     * 正则匹配变量获取 [] 里面的值
     */
    public static String getValue(String text) {
        if (text == null) {
            // 添加空值检查
            return null;
        }

        Pattern pattern = Pattern.compile("\\[(.+?)]");
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()){
            return matcher.group(1);
        }

        return null;
    }
}
