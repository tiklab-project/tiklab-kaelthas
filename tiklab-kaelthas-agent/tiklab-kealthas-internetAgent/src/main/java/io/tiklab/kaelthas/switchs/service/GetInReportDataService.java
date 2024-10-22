package io.tiklab.kaelthas.switchs.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.history.service.HistoryService;
import io.tiklab.kaelthas.switchs.dao.SwitchHostDao;
import io.tiklab.kaelthas.switchs.model.SwitchMonitor;
import io.tiklab.kaelthas.switchs.utils.ConversionAllTypeUtil;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class GetInReportDataService {

    @Autowired
    private SwitchHostDao switchHostDao;

    @Autowired
    private HistoryService historyService;

    private static final List<History> historyList = new LinkedList<>();

    //使用定时任务获取配置信息,使用配置信息获取指标数据
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void executeSwitchHost() {
        //获取配置的信息,监控项的信息
        String dataTimeNow = ConversionAllTypeUtil.getDataTimeNow();

        List<SwitchMonitor> hostList = switchHostDao.findSwitchList();

        for (SwitchMonitor switchMonitor : hostList) {
            History history = new History();
            history.setHostId(switchMonitor.getId());
            history.setMonitorId(switchMonitor.getMonitorId());
            history.setGatherTime(dataTimeNow);

            switch (switchMonitor.getInternetItemId()){
                case "301":
                    findInternetStatus(switchMonitor,history);
                    break;
                case "302":
                    findInOctets(switchMonitor,history);
                    break;
                case "303":
                    findOutOctets(switchMonitor,history);
                    break;
            }
        }

        if (historyList.size() > 5) {
            List<History> list = new LinkedList<>(historyList);
            historyService.insertForList(list);
            historyList.clear();
        }

    }

    private void findOutOctets(SwitchMonitor switchMonitor, History history) {
        try {

            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
            transport.listen();

            Address targetAddress = GenericAddress.parse("udp:"+switchMonitor.getIp()+"/"+switchMonitor.getPort());
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("public"));
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);
            target.setVersion(SnmpConstants.version1);

            Snmp snmp = new Snmp(transport);

            long inOctetsSum = 0;

            List<Long> inOctetsList = getInOctets(snmp, target);

            for (int i = 0; i < inOctetsList.size(); i++) {
                long inOctets = inOctetsList.get(i);

                inOctetsSum += inOctets;

            }

            snmp.close();

            BigDecimal divide = new BigDecimal(inOctetsSum).divide(new BigDecimal(1000000000),2, RoundingMode.FLOOR);

            history.setReportData(divide.toString());
            historyList.add(history);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findInOctets(SwitchMonitor switchMonitor, History history) {
        try {

            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
            transport.listen();

            Address targetAddress = GenericAddress.parse("udp:"+switchMonitor.getIp()+"/"+switchMonitor.getPort());
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("public"));
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);
            target.setVersion(SnmpConstants.version1);

            Snmp snmp = new Snmp(transport);

            long outOctetsSum = 0;

            List<Long> outOctetsList = getOutOctets(snmp, target);

            for (int i = 0; i < outOctetsList.size(); i++) {

                long outOctets = outOctetsList.get(i);

                outOctetsSum += outOctets;

            }
            BigDecimal divide = new BigDecimal(outOctetsSum).divide(new BigDecimal(1000000000),2, RoundingMode.FLOOR);

            history.setReportData(divide.toString());

            snmp.close();
            historyList.add(history);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void findInternetStatus(SwitchMonitor switchMonitor, History history) {
        try {

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


    private static List<Long> getInOctets(Snmp snmp, CommunityTarget target) throws Exception {
        List<Long> inOctetsList = new ArrayList<>();
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.10"))); // ifInOctets OID
        pdu.setType(PDU.GETNEXT);

        boolean hasNext = true;
        while (hasNext) {
            ResponseEvent event = snmp.send(pdu, target);
            PDU response = event.getResponse();

            if (response == null) {
                break;
            } else {
                VariableBinding vb = response.get(0);
                OID nextOid = vb.getOid();

                if (nextOid.startsWith(new OID("1.3.6.1.2.1.2.2.1.10"))) {
                    inOctetsList.add(vb.getVariable().toLong());
                    pdu.set(0, new VariableBinding(nextOid));
                } else {
                    hasNext = false;
                }
            }
        }
        return inOctetsList;
    }

    private static List<Long> getOutOctets(Snmp snmp, CommunityTarget target) throws Exception {
        List<Long> outOctetsList = new ArrayList<>();
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.16"))); // ifOutOctets OID
        pdu.setType(PDU.GETNEXT);

        boolean hasNext = true;
        while (hasNext) {
            ResponseEvent event = snmp.send(pdu, target);
            PDU response = event.getResponse();

            if (response == null) {
                break;
            } else {
                VariableBinding vb = response.get(0);
                OID nextOid = vb.getOid();

                if (nextOid.startsWith(new OID("1.3.6.1.2.1.2.2.1.16"))) {
                    outOctetsList.add(vb.getVariable().toLong());
                    pdu.set(0, new VariableBinding(nextOid));
                } else {
                    hasNext = false;
                }
            }
        }
        return outOctetsList;
    }

}
