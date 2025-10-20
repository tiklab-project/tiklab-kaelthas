package io.tiklab.kaelthas.util;
import java.time.LocalDate;

public class TableUtil {


    /**
     * 获取数据库表名
     * @param currentDate : 当前月数据、客户端传入的月数据
     * @param endName 历史数据结尾字段。0（所有数据，结尾为空）、1（1分钟数据表，结尾1）、 5（5分钟数据表，结尾5）、15（15分钟数据表，结尾15）
     */
    public static String getDbTableName(LocalDate currentDate,int endName){

        // 获取当前年、月
        int year = currentDate.getYear();
        // 获取当前月份
        int month = currentDate.getMonthValue();
        String value = String.valueOf(month);
        if (value.length()<2){
            value="0"+month;
        }
        String dbTableName="mtc_db_history_"+year+value;
        if (endName!=0){
             dbTableName="mtc_db_history_"+year+value+"_"+endName;
        }

        return dbTableName;
    }

    /**
     * 获取网络历史表名
     * @param currentDate : 当前月数据、客户端传入的月数据
     * @param endName 历史数据结尾字段。0（所有数据，结尾为空）、1（1分钟数据表，结尾1）、 5（5分钟数据表，结尾5）、15（15分钟数据表，结尾15）
     */
    public static String getInternetTableName(LocalDate currentDate,int endName){

        // 获取当前年、月
        int year = currentDate.getYear();
        // 获取当前月份
        int month = currentDate.getMonthValue();
        String value = String.valueOf(month);
        if (value.length()<2){
            value="0"+month;
        }
        String dbTableName="mtc_internet_history_"+year+value;
        if (endName!=0){
            dbTableName="mtc_internet_history_"+year+value+"_"+endName;
        }

        return dbTableName;
    }

    /**
     * 获取主机历史表名
     * @param currentDate : 当前月数据、客户端传入的月数据
     * @param endName 历史数据结尾字段。0（所有数据，结尾为空）、1（1分钟数据表，结尾1）、 5（5分钟数据表，结尾5）、15（15分钟数据表，结尾15）
     */
    public static String getHostTableName(LocalDate currentDate,int endName){

        int year = currentDate.getYear();
        // 获取当前月份
        int month = currentDate.getMonthValue();
        String value = String.valueOf(month);
        if (value.length()<2){
            value="0"+month;
        }
        String dbTableName="mtc_host_history_"+year+value;
        if (endName!=0){
            dbTableName="mtc_host_history_"+year+value+"_"+endName;
        }

        return dbTableName;
    }


    /**
     * 获取数据库表名
     *  @param currentDate : 当前月数据、客户端传入的月数据
     * @param endName 历史数据结尾字段。0（所有数据，结尾为空）、1（1分钟数据表，结尾1）、 5（5分钟数据表，结尾5）、15（15分钟数据表，结尾15）
     */
    public static String getK8sTableName(LocalDate currentDate,int endName){

        // 获取当前年、月
        int year = currentDate.getYear();
        // 获取当前月份
        int month = currentDate.getMonthValue();
        String value = String.valueOf(month);
        if (value.length()<2){
            value="0"+month;
        }
        String dbTableName="mtc_ku_history_"+year+value;
        if (endName!=0){
            dbTableName="mtc_ku_history_"+year+value+"_"+endName;
        }

        return dbTableName;
    }

    /**
     * 通过类型查询历史数据表end 名
     * @param type 1:5分钟内、2:60分钟内、 3:720分钟内、4:其他的
     */
    public static int getHistoryEndName(int type){
        switch (type){
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 5;
            default:
                return 15;
        }

    }
}
