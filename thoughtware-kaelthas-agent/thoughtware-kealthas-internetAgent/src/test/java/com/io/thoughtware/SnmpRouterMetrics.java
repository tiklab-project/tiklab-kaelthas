package com.io.thoughtware;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpRouterMetrics {

    public static void main(String[] args) {

        // SNMP 配置
        String ipAddress = "172.10.1.1";  // 路由器的 IP 地址
        String ifInOctetsOID = ".1.3.6.1.2.1.2.2.1.10.1";

        extracted(ipAddress, ifInOctetsOID);

    }

    public static void extracted(String ipAddress, String ifInOctetsOID) {
        try {
            // 创建 SNMP 目标
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("public"));
            target.setAddress(new UdpAddress(ipAddress + "/161"));
            target.setRetries(5);
            target.setTimeout(2000);
            target.setVersion(SnmpConstants.version1);

            // 创建 Snmp 实例和 TransportMapping
            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
            Snmp snmp = new Snmp(transport);
            transport.listen();

            // 获取接口接收字节数 (ifInOctets)
            PDU pduIn = new PDU();
            pduIn.add(new VariableBinding(new OID(ifInOctetsOID)));
            pduIn.setType(PDU.GETNEXT);

            ResponseEvent eventIn = snmp.send(pduIn, target);
            PDU responseIn = eventIn.getResponse();
            if (responseIn != null) {
                System.out.println("参数信息: " + responseIn.get(0).getVariable());
            } else {
                System.out.println("获取 ifInOctets 失败，可能是超时或 SNMP 请求失败。");
            }

            // 关闭 SNMP
            snmp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

