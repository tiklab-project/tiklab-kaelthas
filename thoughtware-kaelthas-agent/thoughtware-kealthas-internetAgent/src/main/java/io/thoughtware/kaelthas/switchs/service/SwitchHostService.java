package io.thoughtware.kaelthas.switchs.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.switchs.dao.SwitchHostDao;
import io.thoughtware.kaelthas.switchs.model.SwitchMonitor;
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

import java.util.LinkedList;
import java.util.List;

@Component
public class SwitchHostService {

    @Autowired
    private SwitchHostDao switchHostDao;

    private static final List<History> historyList = new LinkedList<>();

    //使用定时任务获取配置信息,使用配置信息获取指标数据
    @Scheduled(cron = "0/10 * * * * * ")
    public void executeSwitchHost() {
        //获取配置的信息,监控项的信息
        List<SwitchMonitor> hostList = switchHostDao.findSwitchList();

        for (SwitchMonitor switchMonitor : hostList) {

            if ("301".equals(switchMonitor.getInternetItemId())) {
                findInternetStatus(switchMonitor);
            }
        }

    }

    private static void findInternetStatus(SwitchMonitor switchMonitor) {
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
            System.out.println(portArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
