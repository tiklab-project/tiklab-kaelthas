package io.thoughtware.kaelthas.common.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SqlUtil {

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
}
