package io.tiklab.kaelthas.common.timer.service;

import io.tiklab.kaelthas.common.timer.dao.TimerDao;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import io.tiklab.kaelthas.db.database.model.DbInfo;
import io.tiklab.kaelthas.db.database.service.DbInfoService;
import io.tiklab.kaelthas.alarm.service.AlarmService;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.history.service.HistoryService;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.internet.internet.model.Internet;
import io.tiklab.kaelthas.internet.internet.service.InternetService;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.service.KubernetesService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时扫描主机,数据库,k8s,网络设备的状态,进行修改
 */
@Component
public class EquipmentTimer {

    @Autowired
    HostService hostService;

    @Autowired
    AlarmService alarmService;

    @Autowired
    HistoryService historyService;

    @Autowired
    private DbInfoService dbInfoService;

    @Autowired
    private KubernetesService kubernetesService;

    @Autowired
    private InternetService internetService;

    @Autowired
    private TimerDao timerDao;

    //定时扫描主机、数据库、k8s、网络的状态并且修改状态
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void updateUsability() {
        String updateSql = "";
        String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);
        List<History> histories = historyService.findHistoryByHostIds(beforeTime);
        List<String> historyHostIds = histories.stream().map(History::getHostId).toList();

        String hostSql = getHostSql(historyHostIds);

        String dbSql = getDbSql(historyHostIds);

        String kuSql = getKuSql(historyHostIds);

        String inSql = getInSql(historyHostIds);

        updateSql = updateSql
                .concat(hostSql).concat("\n")
                .concat(dbSql).concat("\n")
                .concat(kuSql).concat("\n")
                .concat(inSql);

        timerDao.updateBySQL(updateSql);
    }

    @NotNull
    private String getInSql(List<String> historyHostIds) {
        String updateSql;
        //过滤出存在的网络ids
        //查询网络列表
        List<Internet> internetList = internetService.findAll();
        if (internetList.isEmpty()) {
            return "";
        }
        List<String> inIds = internetList.stream().map(Internet::getId).toList();
        List<String> existInIds = inIds.stream().filter(historyHostIds::contains).toList();
        //如果查询的数据为空
        if (existInIds.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < inIds.size(); i++) {
                stringBuilder.append("'").append(inIds.get(i).concat("'"));
                if (i != inIds.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            updateSql = "update mtc_internet set usability = 0 where id in (" + stringBuilder + ");";
        } else {
            //找出不存在数据的主机ids
            //主机分为两类,有数据的和没有数据的都需要进行状态更新
            //List<String> resultIds = inIds.stream().filter(item -> !existInIds.contains(item)).toList();
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int i = 0; i < existInIds.size(); i++) {
                stringBuilder1.append("'").append(existInIds.get(i).concat("'"));
                if (i != existInIds.size() - 1) {
                    stringBuilder1.append(",");
                }
            }
            updateSql = "update mtc_internet set usability = 1 where id in (" + stringBuilder1 + ");";


            List<String> notInIds = inIds.stream().filter(item -> !historyHostIds.contains(item)).toList();

            if (!notInIds.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < notInIds.size(); i++) {
                    stringBuilder.append("'").append(notInIds.get(i).concat("'"));
                    if (i != notInIds.size() - 1) {
                        stringBuilder.append(",");
                    }
                }
                updateSql = updateSql.concat("\nupdate mtc_internet set usability = 0 where id in (" + stringBuilder + ");");
            }
        }
        return updateSql;
    }

    private String getKuSql(List<String> historyHostIds) {
        String updateSql;
        //过滤出k8s的ids
        //查询k8s列表
        List<Kubernetes> kubernetesList = kubernetesService.findAll();
        if (kubernetesList.isEmpty()) {
            return "";
        }
        List<String> kuIds = kubernetesList.stream().map(Kubernetes::getId).toList();
        List<String> existKuIds = kuIds.stream().filter(historyHostIds::contains).toList();
        //如果查询的数据为空
        if (existKuIds.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < kuIds.size(); i++) {
                stringBuilder.append("'").append(kuIds.get(i).concat("'"));
                if (i != kuIds.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            updateSql = "update mtc_ku_info set usability = 0 where id in (" + stringBuilder + ");";
        } else {
            //找出不存在数据的主机ids
            //主机分为两类,有数据的和没有数据的都需要进行状态更新
            //List<String> resultIds = kuIds.stream().filter(item -> !existKuIds.contains(item)).toList();
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int i = 0; i < existKuIds.size(); i++) {
                stringBuilder1.append("'").append(existKuIds.get(i).concat("'"));
                if (i != existKuIds.size() - 1) {
                    stringBuilder1.append(",");
                }
            }
            updateSql = "update mtc_ku_info set usability = 1 where id in (" + stringBuilder1 + ");";


            List<String> notKuIds = kuIds.stream().filter(item -> !historyHostIds.contains(item)).toList();

            if (!notKuIds.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < notKuIds.size(); i++) {
                    stringBuilder.append("'").append(notKuIds.get(i).concat("'"));
                    if (i != notKuIds.size() - 1) {
                        stringBuilder.append(",");
                    }
                }
                updateSql = updateSql.concat("\nupdate mtc_ku_info set usability = 0 where id in (" + stringBuilder + ");");
            }
        }
        return updateSql;
    }

    private String getDbSql(List<String> historyHostIds) {
        String updateSql;
        //过滤出存在的数据库ids
        //查询数据库列表
        List<DbInfo> dbInfoList = dbInfoService.findAll();
        if (dbInfoList.isEmpty()) {
            return "";
        }
        List<String> dbIds = dbInfoList.stream().map(DbInfo::getId).toList();
        List<String> existDbIds = dbIds.stream().filter(historyHostIds::contains).toList();
        //如果查询的数据库数据为空
        if (existDbIds.isEmpty()) {
            StringBuilder dbBuilder = new StringBuilder();
            for (int i = 0; i < dbIds.size(); i++) {
                dbBuilder.append("'").append(dbIds.get(i).concat("'"));
                if (i != dbIds.size() - 1) {
                    dbBuilder.append(",");
                }
            }
            updateSql = "\nupdate mtc_db_info set usability = 0 where id in (" + dbBuilder + ");";
        } else {
            //找出不存在数据的主机ids
            //主机分为两类,有数据的和没有数据的都需要进行状态更新
            //List<String> resultIds = dbIds.stream().filter(item -> !existDbIds.contains(item)).toList();
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int i = 0; i < existDbIds.size(); i++) {
                stringBuilder1.append("'").append(existDbIds.get(i).concat("'"));
                if (i != existDbIds.size() - 1) {
                    stringBuilder1.append(",");
                }
            }
            updateSql = "update mtc_db_info set usability = 1 where id in (" + stringBuilder1 + ");";

            List<String> notDbIds = dbIds.stream().filter(item -> !historyHostIds.contains(item)).toList();
            if (!notDbIds.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < notDbIds.size(); i++) {
                    stringBuilder.append("'").append(notDbIds.get(i).concat("'"));
                    if (i != notDbIds.size() - 1) {
                        stringBuilder.append(",");
                    }
                }
                updateSql = updateSql.concat("\nupdate mtc_db_info set usability = 0 where id in (" + stringBuilder + ");");
            }
        }
        return updateSql;
    }

    @NotNull
    private String getHostSql(List<String> historyHostIds) {
        String updateSql;
        //过滤出存在数据的主机ids
        //查询主机列表
        List<Host> allHost = hostService.findAllHost();
        List<String> hostIds = allHost.stream().map(Host::getId).toList();
        List<String> existHostIds = hostIds.stream().filter(historyHostIds::contains).toList();
        //如果查询的数据为空
        if (existHostIds.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < hostIds.size(); i++) {
                stringBuilder.append("'").append(hostIds.get(i).concat("'"));
                if (i != hostIds.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            updateSql = "update mtc_host set usability = 0 where id in (" + stringBuilder + ");";
        } else {
            //找出不存在数据的主机ids
            //主机分为两类,有数据的和没有数据的都需要进行状态更新
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int i = 0; i < existHostIds.size(); i++) {
                stringBuilder1.append("'").append(existHostIds.get(i).concat("'"));
                if (i != existHostIds.size() - 1) {
                    stringBuilder1.append(",");
                }
            }
            updateSql = "update mtc_host set usability = 1 where id in (" + stringBuilder1 + ");";


            List<String> notHostIds = hostIds.stream().filter(item -> !historyHostIds.contains(item)).toList();
            if (!notHostIds.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < notHostIds.size(); i++) {
                    stringBuilder.append("'").append(notHostIds.get(i).concat("'"));
                    if (i != notHostIds.size() - 1) {
                        stringBuilder.append(",");
                    }
                }
                updateSql = updateSql.concat("\nupdate mtc_host set usability = 0 where id in (" + stringBuilder + ");");
            }
        }
        return updateSql;
    }
}
