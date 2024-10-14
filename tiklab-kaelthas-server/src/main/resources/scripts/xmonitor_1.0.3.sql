-- 主机下的监控项
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('51d71cb2489c', '78e776b56aa49cfb', '剩余可用内存', '24', NULL, '100000', 1, 'systems.memory.available', NULL, 2, NULL);
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('68dd778efc17', '78e776b56aa49cfb', '已用内存', '23', NULL, '100000', 1, 'systems.memory.used', NULL, 2, NULL);
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('0dddfa6366b8', '78e776b56aa49cfb', '内存使用率', '10', NULL, '10000', 1, 'systems.memory.percent', NULL, 2, NULL);
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('7f71715b046c', '78e776b56aa49cfb', 'CPU利用率', '6', NULL, '100000', 1, 'systems.cpu.rate', NULL, 2, NULL);
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('03b60e4d2a4a', '78e776b56aa49cfb', '完全空闲内存', '25', NULL, '1000000', 1, 'systems.memory.free', NULL, 2, NULL);
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('524b65fdfa48', '78e776b56aa49cfb', '测试本机的8080端口是否启动', '27', NULL, '1000000', 1, 'systems.host(localhost,8080)', NULL, 2, NULL);
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('4cdc38131eac', '9b426a2acd9e', '剩余可用内存', '24', NULL, '100000', 1, 'systems.memory.available', NULL, 2, '51d71cb2489c');
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('8ebc59175e63', '9b426a2acd9e', '已用内存', '23', NULL, '100000', 1, 'systems.memory.used', NULL, 2, '68dd778efc17');
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('97b2d09093bf', '9b426a2acd9e', '内存使用率', '10', NULL, '10000', 1, 'systems.memory.percent', NULL, 2, '0dddfa6366b8');
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('cae8033b2aae', '9b426a2acd9e', 'CPU利用率', '6', NULL, '100000', 1, 'systems.cpu.rate', NULL, 2, '7f71715b046c');
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('42edaa37f802', '9b426a2acd9e', '完全空闲内存', '25', NULL, '1000000', 1, 'systems.memory.free', NULL, 2, '03b60e4d2a4a');
INSERT INTO "mtc_host_monitor" ("id", "host_id", "name", "monitor_item_id", "interval_time", "data_retention_time", "monitor_status", "expression", "information", "source", "template_id") VALUES ('7e5a32a01f54', '9b426a2acd9e', '测试本机的8080端口是否启动', '27', NULL, '1000000', 1, 'systems.host(localhost,8080)', NULL, 2, '524b65fdfa48');



-- 模板表
INSERT INTO "mtc_template" ("id", "name", "is_open", "describe", "superior_id") VALUES ('78e776b56aa49cfb', '默认模版', 1, '默认模板', NULL);

-- 主机和模板的关联表
INSERT INTO "mtc_host_template" ("id", "template_id", "host_id") VALUES ('215c0d1f0770', '78e776b56aa49cfb', '9b426a2acd9e');

-- 触发器信息
INSERT INTO "mtc_trigger" ("id", "name", "describe", "state", "severity_level", "monitor_id", "host_id", "medium_type", "source", "expression", "scheme", "range_time", "percentage") VALUES ('61691e36d86e', '剩余可用内存小于1G', '剩余可用内存小于1G', 1, 2, NULL, '9b426a2acd9e', NULL, NULL, '{{systems.memory.available}}<[1]', 1, NULL, NULL);
INSERT INTO "mtc_trigger" ("id", "name", "describe", "state", "severity_level", "monitor_id", "host_id", "medium_type", "source", "expression", "scheme", "range_time", "percentage") VALUES ('e2d9d8c3c92d', '内存使用率大于60%', '内存使用率大于60%', 1, 3, NULL, '9b426a2acd9e', NULL, NULL, '{{systems.memory.percent}}>[60]', 2, 3, NULL);
INSERT INTO "mtc_trigger" ("id", "name", "describe", "state", "severity_level", "monitor_id", "host_id", "medium_type", "source", "expression", "scheme", "range_time", "percentage") VALUES ('6269aa4e99a2', '完全空闲中的内存小于1G', '完全空闲中的内存小于1G', 1, 3, NULL, '9b426a2acd9e', NULL, NULL, '{{systems.memory.free}}<[1]', 1, NULL, NULL);
INSERT INTO "mtc_trigger" ("id", "name", "describe", "state", "severity_level", "monitor_id", "host_id", "medium_type", "source", "expression", "scheme", "range_time", "percentage") VALUES ('8a13aece6769', '8080端口没有启动', '8080端口没有启动', 1, 4, NULL, '9b426a2acd9e', NULL, NULL, '{{systems.host(localhost,8080)}}==[0]', 1, NULL, NULL);
INSERT INTO "mtc_trigger" ("id", "name", "describe", "state", "severity_level", "monitor_id", "host_id", "medium_type", "source", "expression", "scheme", "range_time", "percentage") VALUES ('ec4319dc515c', '已用内存大于2G', '已用内存大于2G', 1, 2, NULL, '9b426a2acd9e', NULL, NULL, '{{systems.memory.used}}>[2]', 1, NULL, NULL);
INSERT INTO "mtc_trigger" ("id", "name", "describe", "state", "severity_level", "monitor_id", "host_id", "medium_type", "source", "expression", "scheme", "range_time", "percentage") VALUES ('03b88201e6e1', 'CPU使用率大于80%', '内存使用率大于80%', 1, 2, NULL, '9b426a2acd9e', NULL, NULL, '{{systems.cpu.rate}}>[80]', 1, 4, 50);

-- 触发器和发送消息方式中间表
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('a2765207d800', 'ec4319dc515c', '2');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('1e2d56aef465', '61691e36d86e', '2');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('716200d1bdb8', '61691e36d86e', '3');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('527d84fae08e', 'e2d9d8c3c92d', '2');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('bef5c43c3018', 'e2d9d8c3c92d', '3');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('6a114a039c83', '03b88201e6e1', '2');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('05adb1519897', '03b88201e6e1', '3');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('d0bcce33280e', '03b88201e6e1', '1');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('be37fae68d86', '6269aa4e99a2', '2');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('a9538eb089fc', '6269aa4e99a2', '3');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('6271f1f44a5b', '8a13aece6769', '2');
INSERT INTO "mtc_trigger_medium" ("id", "trigger_id", "medium_id") VALUES ('9a29147eed64', '8a13aece6769', '3');


-- 图形表
INSERT INTO "mtc_graphics" ("id", "name", "describe", "width", "height", "host_id", "source") VALUES ('2a9a0e1268ab', '使用率图形', NULL, NULL, NULL, '9b426a2acd9e', NULL);
INSERT INTO "mtc_graphics" ("id", "name", "describe", "width", "height", "host_id", "source") VALUES ('920fa6e3d052', '内存图形', NULL, NULL, NULL, '9b426a2acd9e', NULL);
INSERT INTO "mtc_graphics" ("id", "name", "describe", "width", "height", "host_id", "source") VALUES ('33079a8a71a6', '8080端口是否启动', '指定端口是否启动', NULL, NULL, '9b426a2acd9e', NULL);

-- 图形与监控项关联表
INSERT INTO "mtc_graphics_monitor" ("id", "monitor_id", "graphics_id") VALUES ('1952c63d90d1', '97b2d09093bf', '2a9a0e1268ab');
INSERT INTO "mtc_graphics_monitor" ("id", "monitor_id", "graphics_id") VALUES ('26299ada268b', 'cae8033b2aae', '2a9a0e1268ab');
INSERT INTO "mtc_graphics_monitor" ("id", "monitor_id", "graphics_id") VALUES ('b12670ea53cb', '4cdc38131eac', '920fa6e3d052');
INSERT INTO "mtc_graphics_monitor" ("id", "monitor_id", "graphics_id") VALUES ('130f9992c836', '8ebc59175e63', '920fa6e3d052');
INSERT INTO "mtc_graphics_monitor" ("id", "monitor_id", "graphics_id") VALUES ('eb2a8090265f', '42edaa37f802', '920fa6e3d052');
INSERT INTO "mtc_graphics_monitor" ("id", "monitor_id", "graphics_id") VALUES ('51e854023d4d', '7e5a32a01f54', '33079a8a71a6');
