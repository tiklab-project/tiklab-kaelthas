package io.tiklab.kaelthas.util;

import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.host.history.model.HostHistory;
import io.tiklab.kaelthas.internet.history.model.InternetHistory;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistory;

import java.util.Collection;
import java.util.List;

public class StringUtil {


    public static String getAvgNumber(Collection<List<DbHistory>> values1) {
        String strJson = "{";
        for (List<DbHistory> histories : values1) {
            double avg = histories.stream()
                    .mapToDouble(item ->
                            Double.parseDouble((item.getReportData() == null || item.getReportData().equals("null")) ?
                                    "0" : item.getReportData())).average().orElse(0);
            strJson = strJson.concat("'" + histories.get(0).getExpression() + "':'" + avg + "',");
        }

        strJson = strJson.concat("}");
        return strJson;
    }




    //解析K8sList为json字符串
    public static String getKuAvgNumber(Collection<List<KubernetesHistory>> values1) {
        String strJson = "{";
        for (List<KubernetesHistory> histories : values1) {
            double avg = histories.stream()
                    .mapToDouble(item ->
                            Double.parseDouble((item.getReportData() == null || item.getReportData().equals("null")) ?
                                    "0" : item.getReportData())).average().orElse(0);
            strJson = strJson.concat("'" + histories.get(0).getExpression() + "':'" + avg + "',");
        }

        strJson = strJson.concat("}");
        return strJson;
    }



    //解析网络List为json字符串
    public static String getInAvgNumber(Collection<List<InternetHistory>> values1) {
        String strJson = "{";
        for (List<InternetHistory> histories : values1) {
            double avg = histories.stream()
                    .mapToDouble(item ->
                            Double.parseDouble((item.getReportData() == null || item.getReportData().equals("null")) ?
                                    "0" : item.getReportData())).average().orElse(0);
            strJson = strJson.concat("'" + histories.get(0).getExpression() + "':'" + avg + "',");
        }

        strJson = strJson.concat("}");
        return strJson;
    }


   //解析主机List为json字符串
    public static String getHostAvgNumber(Collection<List<HostHistory>> values1) {
        String strJson = "{";
        for (List<HostHistory> histories : values1) {
            double avg = histories.stream()
                    .mapToDouble(item ->
                            Double.parseDouble((item.getReportData() == null || item.getReportData().equals("null")) ?
                                    "0" : item.getReportData())).average().orElse(0);
            strJson = strJson.concat("'" + histories.get(0).getExpression() + "':'" + avg + "',");
        }

        strJson = strJson.concat("}");
        return strJson;
    }

    //解析主机为json字符串
    public static String getHostString(HostHistory history) {
        String strJson = "{'"+history.getExpression()+ "':'" + history.getReportData() + "'}";

        return strJson;
    }

    //解析数据库为json字符串
    public static String getDbString(DbHistory dbHistory) {
        String strJson = "{'"+dbHistory.getExpression()+ "':'" + dbHistory.getReportData() + "'}";

        return strJson;
    }

    //解析K8s为json字符串
    public static String getKuString(KubernetesHistory kubernetesHistory) {
        String strJson = "{'"+kubernetesHistory.getExpression()+ "':'" + kubernetesHistory.getReportData() + "'}";

        return strJson;
    }
    //解析网络List为json字符串
    public static String getInString(List<InternetHistory> value) {
        String strJson = "{";
        for (int i = 0; i < value.size(); i++) {
            strJson = strJson.concat("'" + value.get(i).getExpression() + "':'" + value.get(i).getReportData() + "'");
            if (i != value.size() - 1) {
                strJson = strJson.concat(",");
            }
        }
        strJson = strJson.concat("}");
        return strJson;
    }

    //解析K8sList为json字符串
    public static String getKuListString(List<KubernetesHistory> value) {
        String strJson = "{";
        for (int i = 0; i < value.size(); i++) {
            strJson = strJson.concat("'" + value.get(i).getExpression() + "':'" + value.get(i).getReportData() + "'");
            if (i != value.size() - 1) {
                strJson = strJson.concat(",");
            }
        }
        strJson = strJson.concat("}");
        return strJson;
    }
}
