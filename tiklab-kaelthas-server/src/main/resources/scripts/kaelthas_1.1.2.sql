
-- ----------------------------
-- Records of mtc_internet
-- ----------------------------
INSERT INTO "mtc_internet" VALUES ('381c876a9675', '172.10.1.1交换机', '172.10.1.1', 1, 1, 1, '2024-09-26 15:51:27', '2024-09-29 17:30:17', 4, 161);


-- ----------------------------
-- Records of mtc_internet_graphics
-- ----------------------------
INSERT INTO "mtc_internet_graphics" VALUES ('b7064f52b8b1', '上行流量', '上行', '381c876a9675', 1);
INSERT INTO "mtc_internet_graphics" VALUES ('a860cdb7cd59', '下行流量', '下行', '381c876a9675', 1);


-- ----------------------------
-- Records of mtc_internet_graphics_monitor
-- ----------------------------
INSERT INTO "mtc_internet_graphics_monitor" VALUES ('GpJOLGmWXrGJ', 'e6773d4934e1', 'b7064f52b8b1');
INSERT INTO "mtc_internet_graphics_monitor" VALUES ('pzFpInJqJEdg', '7784c74810d1', 'a860cdb7cd59');


-- ----------------------------
-- Records of mtc_internet_item
-- ----------------------------
INSERT INTO "mtc_internet_item" VALUES ('304', 'system description', 'kaelthas.internet.desctiption', '系统描述', 1, 2, 0);
INSERT INTO "mtc_internet_item" VALUES ('305', 'Device model', 'kaelthas.internet.deviceModel', '设备型号', 1, 2, 0);
INSERT INTO "mtc_internet_item" VALUES ('306', 'running time', 'kaelthas.internet.runningTime', '运行时间', 1, 2, 0);


-- ----------------------------
-- Records of mtc_internet_monitor
-- ----------------------------
INSERT INTO "mtc_internet_monitor" VALUES ('e6773d4934e1', '381c876a9675', '上行流量', 1111, 1, '302', NULL, 'kaelthas.internet.inOctets');
INSERT INTO "mtc_internet_monitor" VALUES ('7784c74810d1', '381c876a9675', '下行流量', 222222, 1, '303', NULL, 'kaelthas.internet.outOctets');
INSERT INTO "mtc_internet_monitor" VALUES ('4921057f150c', '381c876a9675', '端口状态', 555555, 1, '301', NULL, 'kaelthas.internet.podStatus');


-- ----------------------------
-- Records of mtc_internet_trigger
-- ----------------------------
INSERT INTO "mtc_internet_trigger" VALUES ('e1488a75dadd', '上行流量触发百分比达到30', '上行流量触发百分比达到30', 1, 3, '381c876a9675', '{{kaelthas.internet.inOctets}}>[10]', 3, 10, 30);
INSERT INTO "mtc_internet_trigger" VALUES ('5c5726820175', '上行流量大于10', '上行流量大于10G', 1, 4, '381c876a9675', '{{kaelthas.internet.inOctets}}>[10]', 1, NULL, NULL);
INSERT INTO "mtc_internet_trigger" VALUES ('355a023af949', '上行流量平均大于5', '上行流量平均大于5', 1, 5, '381c876a9675', '{{kaelthas.internet.inOctets}}>[5]', 2, 10, NULL);

-- ----------------------------
-- Records of mtc_internet_trigger_medium
-- ----------------------------
INSERT INTO "mtc_internet_trigger_medium" VALUES ('JzFpOJiIQqzU', '5c5726820175', '2');
INSERT INTO "mtc_internet_trigger_medium" VALUES ('JSrYNqfjsVgf', '5c5726820175', '3');
INSERT INTO "mtc_internet_trigger_medium" VALUES ('nQyiKVDoUuvo', '355a023af949', '2');
INSERT INTO "mtc_internet_trigger_medium" VALUES ('cIWtkHTRclHy', '355a023af949', '3');
INSERT INTO "mtc_internet_trigger_medium" VALUES ('KjFIACkhUibQ', 'e1488a75dadd', '2');
INSERT INTO "mtc_internet_trigger_medium" VALUES ('kRIhnqjlzHpL', 'e1488a75dadd', '3');

