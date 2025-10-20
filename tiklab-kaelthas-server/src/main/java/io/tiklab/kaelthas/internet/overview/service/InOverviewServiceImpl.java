package io.tiklab.kaelthas.internet.overview.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.exception.SystemException;
import io.tiklab.kaelthas.internet.agent.model.SwitchMonitor;
import io.tiklab.kaelthas.internet.history.model.InternetHistory;
import io.tiklab.kaelthas.internet.history.service.InternetHistoryService;
import io.tiklab.kaelthas.internet.internet.model.Internet;
import io.tiklab.kaelthas.internet.internet.service.InternetService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络监控概况页
 */
@Service
public class InOverviewServiceImpl implements InOverviewService{
    private static Logger logger = LoggerFactory.getLogger(InOverviewServiceImpl.class);

    @Autowired
    private InternetHistoryService internetHistoryService;

    @Autowired
    InternetService internetService;

    /**
     * 获取网络详情
     */
    @Override
    public Map<String, Object> findInternetOverview(String internetId) {
        Map<String, Object> map = new HashMap<>();

        Internet internet = internetService.findInternetById(internetId);

        //获取端口的状态信息
        List statuList = findPortStatus(internet);

        //获取网络设备的描述信息
        Map<String, Object> description = findDescription(internet);

        map.put("podInfo", statuList);
        map.put("systemInfo", description);

        //return map;
        return internetHistoryService.findInternetOverview(internetId);
    }



    /**
     * 获取端口的状态信息
     * @param internet 监控的网络信息
     */
    private List findPortStatus(Internet internet ) {
        try {
            InternetHistory internetHistory = new InternetHistory();

            internetHistory.setInternetId(internet.getId());


            internetHistory.setInternetMonitorId("301");
            // 创建 TransportMapping 实例
            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
            transport.listen();

            // 设置目标（交换机的 IP 地址、端口号和社区名）
            Address targetAddress = GenericAddress.parse("udp:" + internet.getIp() + "/" + internet.getPort());
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
            List portArray = new JSONArray();

            boolean hasNext = true;
            while (hasNext) {
                // 获取端口描述
                ResponseEvent eventDescr = snmp.send(pduDescr, target);
                PDU responseDescr = eventDescr.getResponse();

                // 获取端口状态
                ResponseEvent eventStatus = snmp.send(pduStatus, target);
                PDU responseStatus = eventStatus.getResponse();

                if (responseDescr == null || responseStatus == null) {
                    logger.info("SNMP 请求失败或超时");
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
            return portArray;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }


    //获取网络设备的描述信息
    public static Map<String, Object> findDescription(Internet internet)  {
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        InternetHistory internetHistory = new InternetHistory();
        internetHistory.setInternetId(internet.getId());
        internetHistory.setInternetMonitorId("304");

        try {
            // 1. 创建 SNMP 管理器
            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
            transport.listen();

            Address targetAddress = GenericAddress.parse("udp:" + internet.getIp() + "/" + internet.getPort());
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

            if (ObjectUtils.isNotEmpty(response)) {
                resultMap.put("description",String.valueOf(response.get(0).getVariable()));
            }

            String deviceModel = findDeviceModel(snmp, target);

            if (StringUtils.isNotBlank(deviceModel)) {
                resultMap.put("deviceModel",deviceModel);
            }

            String runningTime = findRunningTime(snmp, target);

            if (StringUtils.isNotBlank(runningTime)) {
                resultMap.put("runningTime",runningTime);
            }

            // 4. 关闭 SNMP 管理器
            snmp.close();
        }catch (Exception e){
            throw new SystemException(e.getMessage());
        }

        return resultMap;
    }

    //获取设备型号的信息
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

}
