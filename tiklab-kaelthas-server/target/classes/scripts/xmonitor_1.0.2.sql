INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('10', 'systems.memory.percent', 'memory', 1, '内存使用率', 1);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('22', 'systems.memory.info', 'memory', 2, '物理内存列表信息', 2);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('25', 'systems.memory.free', 'memory', 5, '完全空闲中的内存', 3);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('23', 'systems.memory.used', 'memory', 3, '已用内存', 3);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('24', 'systems.memory.available', 'memory', 4, '剩余可用内存', 3);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('28', 'systems.io.total(path)', 'IO', 3, '磁盘总量', 3);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('30', 'systems.io.avail(path)', 'IO', 5, '空闲磁盘', 3);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('4', 'systems.io.info', 'IO', 2, '所有磁盘的io信息', 2);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('5', 'systems.io.usedPercent(path)', 'IO', 1, '磁盘使用率', 1);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('29', 'systems.io.used(path)', 'IO', 4, '磁盘使用量', 3);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('20', 'systems.internet.connInfo', 'internet', 1, '网络连接的基本信息', 2);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('21', 'systems.internet.readPackage', 'internet', 2, '获取网络读写字节包个数', 2);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('15', 'systems.host.info', 'host', 1, '主机信息', 2);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('26', 'systems.host(host,isuse)', 'host', 5, '主机是否可用', 3);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('16', 'systems.host.beginTime', 'host', 2, '主机的开始运行的时间', 4);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('27', 'systems.host(ip,port)', 'host', 4, '测试端口是否启动', 3);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('11', 'systems.cpu.irqTime', 'CPU', 8, '处理硬中断的 CPU 时间', 4);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('18', 'systems.cpu.load5', 'CPU', 11, 'CPU五分钟内的平均负载', 6);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('19', 'systems.cpu.load15', 'CPU', 12, 'CPU十五分钟内的平均负载', 6);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('17', 'systems.cpu.load1', 'CPU', 10, 'CPU一分钟内的平均负载', 6);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('7', 'systems.cpu.idleTime', 'CPU', 5, 'CPU处于空闲状态时间', 5);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('8', 'systems.cpu.hard', 'CPU', 6, '用户空间进程的CPU的调度优先级', 4);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('13', 'systems.cpu.logicNumber', 'CPU', 13, 'CPU逻辑核数', 4);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('14', 'systems.cpu.physicsNumber', 'CPU', 14, 'CPU物理核数', 4);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('9', 'systems.cpu.readWriteTime', 'CPU', 7, 'IO读写操作导致CPU处于等待状态的时间', 4);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('6', 'systems.cpu.rate', 'CPU', 4, 'CPU利用率', 1);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('3', 'systems.cpu.systemTime', 'CPU', 3, 'CPU系统占用时间', 4);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('2', 'systems.cpu.userTime', 'CPU', 2, '用户态进程占用CPU时间', 4);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('1', 'systems.cpu.info', 'CPU', 1, 'CPU基本信息', 2);
INSERT INTO "mtc_item" ("id", "name", "data_categories", "data_type", "data_subclass", "report_type") VALUES ('12', 'systems.cpu.softirq', 'CPU', 9, '处理软中断的 CPU 时间', 4);

INSERT INTO "mtc_medium" ("id", "name", "type", "status", "details") VALUES ('1', 'APP', 1, 1, '通过APP告警');
INSERT INTO "mtc_medium" ("id", "name", "type", "status", "details") VALUES ('2', '企业微信', 2, 1, '通过企业微信发送告警信息');
INSERT INTO "mtc_medium" ("id", "name", "type", "status", "details") VALUES ('3', '站内信', 3, 1, '通过钉钉发送告警信息');
INSERT INTO "mtc_medium" ("id", "name", "type", "status", "details") VALUES ('4', '邮件通知', 4, 1, '通过邮件通知');

INSERT INTO "mtc_host_group" ("id", "name", "describe") VALUES ('977828517d01', '默认主机组', '默认主机组');

INSERT INTO "mtc_host" ("id", "name", "ip", "host_group_id", "state", "usability", "describe", "create_time", "update_time", "visibility", "color") VALUES ('9b426a2acd9e', '默认主机', '127.0.0.1', '977828517d01', 1, 2, NULL, '2024-06-24 16:43:12', NULL, NULL, 4);