package com.io.thoughtware;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.smi.UdpAddress;

public class SnmpPortStatus {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            // 创建TransportMapping实例
            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
            transport.listen();

            // 设置目标（交换机的IP地址、端口号和社区名）
            Address targetAddress = GenericAddress.parse("udp:172.10.1.1/161");
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("public"));
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);
            target.setVersion(SnmpConstants.version1);  // SNMP版本号

            // 创建SNMP实例
            Snmp snmp = new Snmp(transport);

            // 创建PDU以获取端口状态
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.8"))); // ifOperStatus OID
            pdu.setType(PDU.GETNEXT);  // 使用 GETNEXT 逐个获取端口状态

            // 发送请求
            boolean hasNext = true;
            while (hasNext) {
                ResponseEvent event = snmp.send(pdu, target);
                PDU response = event.getResponse();

                if (response == null) {
                    System.out.println("SNMP 请求失败或超时");
                    break;
                } else {
                    VariableBinding vb = response.get(0);
                    OID nextOid = vb.getOid();

                    // 检查是否还有下一个 OID
                    if (nextOid.startsWith(new OID("1.3.6.1.2.1.2.2.1.8"))) {
                        System.out.println("接口 " + nextOid + " 状态: " + vb.getVariable());

                        // 检查状态值
                        int status = vb.getVariable().toInt();
                        if (status == 1) {
                            System.out.println("端口状态: UP");
                        } else if (status == 2) {
                            System.out.println("端口状态: DOWN");
                        } else {
                            System.out.println("端口状态未知");
                        }

                        // 更新 PDU 以获取下一个端口
                        pdu.setRequestID(null);  // 重置请求ID
                        pdu.set(0, new VariableBinding(nextOid));
                    } else {
                        hasNext = false;
                    }
                }
            }
            snmp.close();

            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
