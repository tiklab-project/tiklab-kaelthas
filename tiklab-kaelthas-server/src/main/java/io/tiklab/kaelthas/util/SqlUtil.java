package io.tiklab.kaelthas.util;

import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.KuOverview;

import java.util.*;

public class SqlUtil {



    /**
     * 拼接添加历史的数据的sql
     * @param tableName 仓库表明
     * @param dataList 填充数据
     */
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


    /**
     * 拼接更新历史的数据的sql
     * @param tableName 仓库表明
     * @param updateList 填充数据
     */
    public static String getKuViewBatchUpdateSql(String tableName,  List<KuOverview> updateList) {
        StringBuffer sql = new StringBuffer();
        StringBuilder valueSb = new StringBuilder();

        sql.append("UPDATE  ");
        sql.append(tableName);
        sql.append(" SET report_data= CASE ");

        List<String> idList=new ArrayList<>();
        for (int i = 0; i < updateList.size(); i++){
            KuOverview kuOverview = updateList.get(i);
            sql.append("WHEN id= '");
            sql.append(kuOverview.getId());
            sql.append("' THEN '");
            sql.append(kuOverview.getReportData());
            sql.append("' ");

            valueSb.append("'");
            valueSb.append(kuOverview.getId());
            if (i==updateList.size()-1){
                valueSb.append("'");
            }else {
                valueSb.append("',");
            }
        }
        sql.append("ELSE report_data END WHERE id IN ( ");
        sql.append(valueSb);
        sql.append(")");
        return sql.toString();
    }
}
