package io.tiklab.kaelthas.common.util;

import io.tiklab.kaelthas.history.model.History;

import java.util.Collection;
import java.util.List;

public class StringUtil {

    public static String getLastValue(List<History> historyList) {
        String strJson = "{";
        for (int i = 0; i < historyList.size(); i++) {
            strJson = strJson.concat("'" + historyList.get(i).getExpression()
                    + "':'" + historyList.get(i).getReportData() + "'");
            if (i != historyList.size() - 1) {
                strJson = strJson.concat(",");
            }
        }
        strJson = strJson.concat("}");
        return strJson;
    }

    public static String getAvgNumber(Collection<List<History>> values1) {
        String strJson = "{";
        for (List<History> histories : values1) {
            double avg = histories.stream()
                    .mapToDouble(item ->
                            Double.parseDouble((item.getReportData() == null || item.getReportData().equals("null")) ?
                                    "0" : item.getReportData())).average().orElse(0);
            strJson = strJson.concat("'" + histories.get(0).getExpression() + "':'" + avg + "',");
        }

        strJson = strJson.concat("}");
        return strJson;
    }

    public static String getString(List<History> value) {
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
