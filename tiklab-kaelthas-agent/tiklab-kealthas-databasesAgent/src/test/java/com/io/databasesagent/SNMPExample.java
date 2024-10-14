package com.io.databasesagent;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SNMPExample {

    public static void main(String[] args) {
        try {
            // 1. 创建 SNMP 会话
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();
            Snmp snmp = new Snmp(transport);

            // 2. 设置目标路由器的 IP 和 SNMP 社区
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("public"));  // 使用公共社区字符串
            target.setAddress(new UdpAddress("172.13.1.27/8080"));  // 路由器 IP 和端口
            target.setRetries(2);
            target.setTimeout(200);  // 超时时间
            target.setVersion(SnmpConstants.version2c);  // 使用 SNMP v2c

            // 3. 创建 PDU（Protocol Data Unit）对象，包含要请求的 OID
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.5.0")));  // OID: sysName（设备名称）
            pdu.setType(PDU.GET);  // 设置为 GET 请求

            // 4. 发送 SNMP 请求
            ResponseEvent responseEvent = snmp.send(pdu, target);

            // 5. 处理响应
            PDU responsePDU = responseEvent.getResponse();
            if (responsePDU != null) {
                String sysName = responsePDU.get(0).getVariable().toString();
                System.out.println("路由器名称: " + sysName);
            } else {
                System.out.println("请求超时或无响应");
            }

            // 6. 关闭 SNMP 会话
            snmp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
