package io.thoughtware.kaelthas;

import java.util.ArrayList;
import java.util.List;

/**
 * 去掉下划线后面的首个字母转为大写
 */
public class LowercaseToUppercase {

    private static List<String> stringList = new ArrayList<>();

    public static void main(String[] args) {
        String[] strings = {
                "id",
                "host_id",
                "monitor_id",
                "report_data",
                "source",
                "monitor_item_id",
                "interval_time",
                "data_subclass",
                "monitor_name",
                "data_categories",
                "report_type",
                "gather_time",
                "expression",
        };
        for (String s : strings) {
            String entityField = toEntityField(s);
//            String string = capitalizeFirstLetter(entityField);
            System.out.println(entityField);
        }

    }

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // 将首字母大写，然后与剩余部分拼接起来
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    //将数据库字段修改成实体类的字段,下划线去掉,然后将下划线后的首字母大写
    public static String toEntityField(String dbField) {
        StringBuilder sb = new StringBuilder();
        sb.append(dbField);
        int index = 0, num = 0;
        char[] chars = dbField.toCharArray();
        for (char aChar : chars) {
            //找到_位置并且该位置不是最后一位
            if (aChar == '_' && index != chars.length - 1) {
                int i1 = index - num;
                sb.replace(i1, i1 + 2, String.valueOf(Character.toUpperCase(chars[index + 1])));
                num++;
            }
            index++;
        }
        return sb.toString();
    }
}