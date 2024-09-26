package com.io.thoughtware;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

//用来获取设备信息
public class GetMessage {
    public static String getMessageByIpAndOid(String ip, String oid) throws IOException {
        String result = null;
        // 1. 创建 SNMP 管理器
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(new UdpAddress(ip + "/161"));
        target.setRetries(2);
        target.setTimeout(5000);
        target.setVersion(SnmpConstants.version1);

        TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
        Snmp snmp = new Snmp(transport);
        transport.listen();


        // 3. 发送 SNMP 请求并处理响应
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GETNEXT);

        ResponseEvent event = snmp.send(pdu, target);
        PDU response = event.getResponse();

        if (response == null) {
            System.out.println("没有得到响应");
        } else {
            result = String.valueOf(response.get(0).getVariable());
        }

        // 4. 关闭 SNMP 管理器
        snmp.close();
        return result;
    }
}
