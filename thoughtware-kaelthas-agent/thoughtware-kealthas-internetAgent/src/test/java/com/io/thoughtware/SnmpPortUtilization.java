package com.io.thoughtware;

import org.snmp4j.*;
import org.snmp4j.smi.*;
import org.snmp4j.event.*;
import org.snmp4j.mp.*;
import org.snmp4j.transport.*;
import java.io.IOException;

public class SnmpPortUtilization {

    public static void main(String[] args) throws IOException, InterruptedException {
        String ip = "172.10.1.1"; // 交换机 IP
        String community = "public"; // SNMP 社区字符串
        int portIndex = 1; // 指定端口的索引（根据交换机实际端口调整）
        
        String oidInOctets = "1.3.6.1.2.1.2.2.1.10." + portIndex; // ifInOctets OID for port
        String oidOutOctets = "1.3.6.1.2.1.2.2.1.16." + portIndex; // ifOutOctets OID for port
        String oidSpeed = "1.3.6.1.2.1.2.2.1.5." + portIndex; // ifSpeed OID for port

        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();

        // 设置 SNMP 目标
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(new UdpAddress(ip + "/161"));
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version1);

        // 轮询端口数据
        while (true) {
            long inOctets1 = getSnmpValue(snmp, target, oidInOctets);
            long outOctets1 = getSnmpValue(snmp, target, oidOutOctets);
            long speed = getSnmpValue(snmp, target, oidSpeed);

            // 等待一段时间 (1 秒)
            //Thread.sleep(1000);

            long inOctets2 = getSnmpValue(snmp, target, oidInOctets);
            long outOctets2 = getSnmpValue(snmp, target, oidOutOctets);

            // 计算带宽利用率
            long deltaInOctets = inOctets2 - inOctets1;
            long deltaOutOctets = outOctets2 - outOctets1;

            // 接收和发送带宽利用率
            double rxUtilization = (deltaInOctets * 8.0) / speed; // 接收带宽利用率
            double txUtilization = (deltaOutOctets * 8.0) / speed; // 发送带宽利用率

            // 打印结果
            System.out.printf("端口 %d 的接收带宽利用率: %.2f%%\n", portIndex, rxUtilization * 100);
            System.out.printf("端口 %d 的发送带宽利用率: %.2f%%\n", portIndex, txUtilization * 100);
        }
    }

    // 获取 SNMP 值
    private static long getSnmpValue(Snmp snmp, Target target, String oid) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);

        ResponseEvent event = snmp.send(pdu, target);
        if (event != null && event.getResponse() != null) {
            PDU responsePDU = event.getResponse();
            return responsePDU.get(0).getVariable().toLong();
        } else {
            System.out.println("SNMP 请求失败或超时");
            return 0;
        }
    }
}
