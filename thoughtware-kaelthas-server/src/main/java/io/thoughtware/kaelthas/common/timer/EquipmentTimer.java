package io.thoughtware.kaelthas.common.timer;

import io.thoughtware.kaelthas.common.util.ConversionDateUtil;
import io.thoughtware.kaelthas.db.database.model.DbInfo;
import io.thoughtware.kaelthas.db.database.service.DbInfoService;
import io.thoughtware.kaelthas.alarm.service.AlarmService;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.history.service.HistoryService;
import io.thoughtware.kaelthas.host.host.model.Host;
import io.thoughtware.kaelthas.host.host.service.HostService;
import io.thoughtware.kaelthas.internet.internet.model.Internet;
import io.thoughtware.kaelthas.internet.internet.service.InternetService;
import io.thoughtware.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.thoughtware.kaelthas.kubernetes.kubernetesInfo.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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

    //定时扫描主机的状态
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void siftHost() {
        List<Host> allHost = hostService.findAllHost();
        if (!allHost.isEmpty()) {
            for (Host host : allHost) {
                //查询五分钟内是否有数据
                History history = new History();
                history.setHostId(host.getId());
                historyService.findHistoryByCondition(history);
            }
        }
    }

    //定时扫描创建数据库的状态
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void siftDb(){
        List<DbInfo> dbInfoList = dbInfoService.findAll();
        if (!dbInfoList.isEmpty()) {
            for (DbInfo dbInfo : dbInfoList) {
                historyService.findHistoryByDb(dbInfo);
            }
        }
    }

    //定时扫描k8s的状态
    @Scheduled(cron = "0 0/5 * * * ?")
    public void scanK8s(){
        List<Kubernetes> kubernetesList = kubernetesService.findAll();
        if (kubernetesList.isEmpty()) {
            return;
        }
        for (Kubernetes kubernetes : kubernetesList) {
            String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);
            List<History> historyList = historyService.findHistoryByGatherTime(kubernetes.getId(),beforeTime);
            //如果查出的数据为空,则修改k8s的状态为
            if (historyList.isEmpty()) {
                kubernetes.setUsability(0);
            }else {
                kubernetes.setUsability(1);
            }
            kubernetesService.updateKbInfo(kubernetes);
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void scanInternet(){
        List<Internet> internetList = internetService.findAll();

        if (internetList.isEmpty()) {
            return;
        }

        String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);

        for (Internet internet : internetList) {

            List<History> historyList = historyService.findHistoryByHostId(internet.getId(),beforeTime);
            if (historyList.isEmpty()) {
                internet.setUsability(0);
            }else {
                internet.setUsability(1);
            }
            internetService.updateInternet(internet);
        }
    }
}
