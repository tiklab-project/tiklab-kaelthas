package com.io.thoughtware;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.util.ArrayList;
import java.util.List;

public class SnmpPortIO {
    private static final long INTERVAL = 1;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        try {
            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
            transport.listen();

            Address targetAddress = GenericAddress.parse("udp:172.10.1.1/161");
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("public"));
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);
            target.setVersion(SnmpConstants.version1);

            Snmp snmp = new Snmp(transport);

            List<String> portNames = getPortNames(snmp, target);
            List<Long> inOctetsList = getInOctets(snmp, target);
            List<Long> outOctetsList = getOutOctets(snmp, target);
            List<Long> speedList = getSpeed(snmp, target);

            for (int i = 0; i < portNames.size(); i++) {
                String portName = portNames.get(i);
                long inOctets = inOctetsList.get(i);
                long outOctets = outOctetsList.get(i);
                long speed = speedList.get(i);

                // 计算带宽利用率
                double inUtilization = (double) inOctets / (speed * INTERVAL) * 100;
                double outUtilization = (double) outOctets / (speed * INTERVAL) * 100;

                System.out.printf("端口: %s, 输入字节数: %d, 输出字节数: %d, 接口速率: %d bps, 输入利用率: %.2f%%, 输出利用率: %.2f%%\n",
                        portName, inOctets, outOctets, speed, inUtilization, outUtilization);
            }

            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static List<Long> getSpeed(Snmp snmp, CommunityTarget target) throws Exception {
        List<Long> speedList = new ArrayList<>();
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.5"))); // ifSpeed OID
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

                if (nextOid.startsWith(new OID("1.3.6.1.2.1.2.2.1.5"))) {
                    speedList.add(vb.getVariable().toLong());
                    pdu.set(0, new VariableBinding(nextOid));
                } else {
                    hasNext = false;
                }
            }
        }
        return speedList;
    }

    private static List<String> getPortNames(Snmp snmp, CommunityTarget target) throws Exception {
        List<String> portNames = new ArrayList<>();
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.2"))); // ifDescr OID
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

                if (nextOid.startsWith(new OID("1.3.6.1.2.1.2.2.1.2"))) {
                    portNames.add(vb.getVariable().toString());
                    pdu.set(0, new VariableBinding(nextOid));
                } else {
                    hasNext = false;
                }
            }
        }
        return portNames;
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
