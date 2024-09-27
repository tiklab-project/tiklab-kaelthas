package io.thoughtware.kaelthas.switchs.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.history.service.HistoryService;
import io.thoughtware.kaelthas.switchs.dao.SwitchHostDao;
import io.thoughtware.kaelthas.switchs.model.SwitchMonitor;
import io.thoughtware.kaelthas.switchs.utils.ConversionAllTypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class SwitchOverviewService {

    @Autowired
    private SwitchHostDao switchHostDao;

    @Autowired
    private HistoryService historyService;

    private static final List<History> historyList = new LinkedList<>();

    @Scheduled(cron = "0/10 * * * * *")
    public void executeOverview() throws IOException {
        //查询出网络列表
        String dataTimeNow = ConversionAllTypeUtil.getDataTimeNow();

        List<SwitchMonitor> hostList = switchHostDao.findAllInternetList();

        for (SwitchMonitor switchMonitor : hostList) {

            findPortStatus(switchMonitor,dataTimeNow);

            findDescription(switchMonitor,dataTimeNow);
        }

        if (historyList.size() > 3) {
            List<History> list = new LinkedList<>(historyList);
            historyService.insertForList(list);
            historyList.clear();
        }

    }

    private void findPortStatus(SwitchMonitor switchMonitor, String dataTimeNow) {
        try {
            History history = new History();
            history.setHostId(switchMonitor.getId());
            history.setGatherTime(dataTimeNow);

            history.setMonitorId("301");
            // 创建 TransportMapping 实例
            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
            transport.listen();

            // 设置目标（交换机的 IP 地址、端口号和社区名）
            Address targetAddress = GenericAddress.parse("udp:" + switchMonitor.getIp() + "/" + switchMonitor.getPort());
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("public"));
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);
            target.setVersion(SnmpConstants.version1);  // SNMP 版本号

            // 创建 SNMP 实例
            Snmp snmp = new Snmp(transport);

            // 创建 PDU 以获取端口描述和状态
            PDU pduDescr = new PDU();
            pduDescr.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.2")));  // ifDescr OID
            pduDescr.setType(PDU.GETNEXT);

            PDU pduStatus = new PDU();
            pduStatus.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.8")));  // ifOperStatus OID
            pduStatus.setType(PDU.GETNEXT);

            // 创建 JSON 数组来存储所有端口的信息
            JSONArray portArray = new JSONArray();

            boolean hasNext = true;
            while (hasNext) {
                // 获取端口描述
                ResponseEvent eventDescr = snmp.send(pduDescr, target);
                PDU responseDescr = eventDescr.getResponse();

                // 获取端口状态
                ResponseEvent eventStatus = snmp.send(pduStatus, target);
                PDU responseStatus = eventStatus.getResponse();

                if (responseDescr == null || responseStatus == null) {
                    System.out.println("SNMP 请求失败或超时");
                    break;
                } else {
                    VariableBinding vbDescr = responseDescr.get(0);
                    VariableBinding vbStatus = responseStatus.get(0);

                    OID nextOidDescr = vbDescr.getOid();
                    OID nextOidStatus = vbStatus.getOid();

                    // 检查是否还有下一个 OID
                    if (nextOidDescr.startsWith(new OID("1.3.6.1.2.1.2.2.1.2")) &&
                            nextOidStatus.startsWith(new OID("1.3.6.1.2.1.2.2.1.8"))) {

                        // 获取端口名称和状态
                        String portName = vbDescr.getVariable().toString();
                        int status = vbStatus.getVariable().toInt();
                        //String statusStr = (status == 1) ? "UP" : "DOWN";

                        // 创建 JSON 对象存储当前端口信息
                        JSONObject portInfo = new JSONObject();
                        portInfo.put("portName", portName);
                        portInfo.put("status", status);

                        // 将当前端口信息加入 JSON 数组
                        portArray.add(portInfo);

                        // 更新 PDU 以获取下一个端口
                        pduDescr.setRequestID(null);
                        pduDescr.set(0, new VariableBinding(nextOidDescr));

                        pduStatus.setRequestID(null);
                        pduStatus.set(0, new VariableBinding(nextOidStatus));
                    } else {
                        hasNext = false;
                    }
                }
            }

            snmp.close();
            history.setReportData(portArray.toString());
            historyList.add(history);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String findRunningTime(Snmp snmp, CommunityTarget target) throws IOException {

        // 3. 发送 SNMP 请求并处理响应
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.3")));
        pdu.setType(PDU.GETNEXT);

        ResponseEvent event = snmp.send(pdu, target);
        PDU response = event.getResponse();

        if (response == null) {
            return null;
        } else {
            return String.valueOf(response.get(0).getVariable());
        }
    }

    private static String findDeviceModel(Snmp snmp, CommunityTarget target) throws IOException {

        // 3. 发送 SNMP 请求并处理响应
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.5")));
        pdu.setType(PDU.GETNEXT);

        ResponseEvent event = snmp.send(pdu, target);
        PDU response = event.getResponse();

        if (response == null) {
            return null;
        } else {
            return String.valueOf(response.get(0).getVariable());
        }

    }

    public static void findDescription(SwitchMonitor switchMonitor, String dataTimeNow) throws IOException {
        JSONObject jsonObject = new JSONObject();

        History history = new History();
        history.setHostId(switchMonitor.getId());
        history.setGatherTime(dataTimeNow);
        history.setMonitorId("304");

        // 1. 创建 SNMP 管理器
        TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
        transport.listen();

        Address targetAddress = GenericAddress.parse("udp:" + switchMonitor.getIp() + "/" + switchMonitor.getPort());
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version1);
        Snmp snmp = new Snmp(transport);


        // 3. 发送 SNMP 请求并处理响应
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.1")));
        pdu.setType(PDU.GETNEXT);

        ResponseEvent event = snmp.send(pdu, target);
        PDU response = event.getResponse();

        if (response == null) {
            return;
        } else {
            jsonObject.put("description",String.valueOf(response.get(0).getVariable()));
        }

        String deviceModel = findDeviceModel(snmp, target);

        if (StringUtils.isNotBlank(deviceModel)) {
            jsonObject.put("deviceModel",deviceModel);
        }

        String runningTime = findRunningTime(snmp, target);

        if (StringUtils.isNotBlank(runningTime)) {
            jsonObject.put("runningTime",runningTime);
        }

        if (!jsonObject.isEmpty()) {
            history.setReportData(jsonObject.toJSONString());
            historyList.add(history);
        }

        // 4. 关闭 SNMP 管理器
        snmp.close();
    }

}
