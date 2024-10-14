/*
 Navicat Premium Dump SQL

 Source Server         : 172.10.1.10
 Source Server Type    : PostgreSQL
 Source Server Version : 150000 (150000)
 Source Host           : 172.10.1.10:5432
 Source Catalog        : throughtware_xmonitor_ce
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150000 (150000)
 File Encoding         : 65001

 Date: 29/09/2024 17:38:00
*/


-- ----------------------------
-- Table structure for mtc_internet
-- ----------------------------

CREATE TABLE "mtc_internet" (
                                         "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                         "name" varchar(128) COLLATE "pg_catalog"."default",
                                         "ip" varchar(64) COLLATE "pg_catalog"."default",
                                         "type" int2,
                                         "status" int2,
                                         "usability" int2,
                                         "create_time" timestamp(6),
                                         "update_time" timestamp(6),
                                         "color" int2,
                                         "port" int4
)
;
COMMENT ON COLUMN "mtc_internet"."type" IS '1代表交换机,2代表路由器';
COMMENT ON COLUMN "mtc_internet"."status" IS '1.开启,0.关闭';
COMMENT ON COLUMN "mtc_internet"."usability" IS '是否可用';
COMMENT ON COLUMN "mtc_internet"."create_time" IS '创建时间';
COMMENT ON COLUMN "mtc_internet"."update_time" IS '修改时间';
COMMENT ON COLUMN "mtc_internet"."color" IS '颜色';
COMMENT ON COLUMN "mtc_internet"."port" IS '端口号';

-- ----------------------------
-- Records of mtc_internet
-- ----------------------------
INSERT INTO "mtc_internet" VALUES ('381c876a9675', '172.10.1.1交换机', '172.10.1.1', 1, 1, 1, '2024-09-26 15:51:27', '2024-09-29 17:30:17', 4, 161);

-- ----------------------------
-- Table structure for mtc_internet_graphics
-- ----------------------------

CREATE TABLE "mtc_internet_graphics" (
                                                  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                                  "name" varchar(64) COLLATE "pg_catalog"."default",
                                                  "describe" varchar(64) COLLATE "pg_catalog"."default",
                                                  "internet_id" varchar(32) COLLATE "pg_catalog"."default",
                                                  "source" int2
)
;
COMMENT ON COLUMN "mtc_internet_graphics"."id" IS 'id';
COMMENT ON COLUMN "mtc_internet_graphics"."name" IS '图形名称';
COMMENT ON COLUMN "mtc_internet_graphics"."describe" IS '描述';
COMMENT ON COLUMN "mtc_internet_graphics"."internet_id" IS '网络id';
COMMENT ON COLUMN "mtc_internet_graphics"."source" IS '1.来源于主机中监控项创建2.来源于模板中监控项创建';
COMMENT ON TABLE "mtc_internet_graphics" IS '根据监控项设置的图表';

-- ----------------------------
-- Records of mtc_internet_graphics
-- ----------------------------
INSERT INTO "mtc_internet_graphics" VALUES ('b7064f52b8b1', '上行流量', '上行', '381c876a9675', 1);
INSERT INTO "mtc_internet_graphics" VALUES ('a860cdb7cd59', '下行流量', '下行', '381c876a9675', 1);

-- ----------------------------
-- Table structure for mtc_internet_graphics_monitor
-- ----------------------------

CREATE TABLE "mtc_internet_graphics_monitor" (
                                                          "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                                          "monitor_id" varchar(128) COLLATE "pg_catalog"."default",
                                                          "graphics_id" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_internet_graphics_monitor"."monitor_id" IS '监控项id';
COMMENT ON COLUMN "mtc_internet_graphics_monitor"."graphics_id" IS '图表id';
COMMENT ON TABLE "mtc_internet_graphics_monitor" IS '监控项和图表的关联表';

-- ----------------------------
-- Records of mtc_internet_graphics_monitor
-- ----------------------------
INSERT INTO "mtc_internet_graphics_monitor" VALUES ('GpJOLGmWXrGJ', 'e6773d4934e1', 'b7064f52b8b1');
INSERT INTO "mtc_internet_graphics_monitor" VALUES ('pzFpInJqJEdg', '7784c74810d1', 'a860cdb7cd59');

-- ----------------------------
-- Table structure for mtc_internet_item
-- ----------------------------

CREATE TABLE "mtc_internet_item" (
                                              "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                              "name" varchar(128) COLLATE "pg_catalog"."default",
                                              "expression" varchar(128) COLLATE "pg_catalog"."default",
                                              "describe" varchar(128) COLLATE "pg_catalog"."default",
                                              "internet_type" int2,
                                              "report_type" int2,
                                              "is_optional" int2
)
;
COMMENT ON COLUMN "mtc_internet_item"."id" IS 'id';
COMMENT ON COLUMN "mtc_internet_item"."name" IS '名称';
COMMENT ON COLUMN "mtc_internet_item"."expression" IS '表达式';
COMMENT ON COLUMN "mtc_internet_item"."describe" IS '描述';
COMMENT ON COLUMN "mtc_internet_item"."internet_type" IS '网络类型';
COMMENT ON COLUMN "mtc_internet_item"."report_type" IS '数据类型(1.数值,2.json)';
COMMENT ON COLUMN "mtc_internet_item"."is_optional" IS '是否可以选择(0.不能在监控项列表选择到,1.可以在监控项列表中选择到)';

-- ----------------------------
-- Records of mtc_internet_item
-- ----------------------------
INSERT INTO "mtc_internet_item" VALUES ('304', 'system description', 'kaelthas.internet.desctiption', '系统描述', 1, 2, 0);
INSERT INTO "mtc_internet_item" VALUES ('305', 'Device model', 'kaelthas.internet.deviceModel', '设备型号', 1, 2, 0);
INSERT INTO "mtc_internet_item" VALUES ('306', 'running time', 'kaelthas.internet.runningTime', '运行时间', 1, 2, 0);
INSERT INTO "mtc_internet_item" VALUES ('303', 'outOctets', 'kaelthas.internet.outOctets', '下行流量', 1, 1, 1);
INSERT INTO "mtc_internet_item" VALUES ('302', 'inOctets', 'kaelthas.internet.inOctets', '上行流量', 1, 1, 1);
INSERT INTO "mtc_internet_item" VALUES ('301', 'pod status', 'kaelthas.internet.podStatus', '端口状态', 1, 2, 1);

-- ----------------------------
-- Table structure for mtc_internet_monitor
-- ----------------------------

CREATE TABLE "mtc_internet_monitor" (
                                                 "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                                 "internet_id" varchar(32) COLLATE "pg_catalog"."default",
                                                 "name" varchar(64) COLLATE "pg_catalog"."default",
                                                 "retention_time" int8,
                                                 "status" int2,
                                                 "internet_item_id" varchar(32) COLLATE "pg_catalog"."default",
                                                 "monitor_id" varchar(32) COLLATE "pg_catalog"."default",
                                                 "expression" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_internet_monitor"."id" IS 'id';
COMMENT ON COLUMN "mtc_internet_monitor"."internet_id" IS '数据源id';
COMMENT ON COLUMN "mtc_internet_monitor"."name" IS '监控项名称';
COMMENT ON COLUMN "mtc_internet_monitor"."retention_time" IS '数据保留时间';
COMMENT ON COLUMN "mtc_internet_monitor"."status" IS '监控项状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_internet_monitor"."internet_item_id" IS 'internet_item表的id';
COMMENT ON COLUMN "mtc_internet_monitor"."monitor_id" IS '模板复制的时候使用,将本表的监控项复制一份出来';
COMMENT ON COLUMN "mtc_internet_monitor"."expression" IS '表达式';

-- ----------------------------
-- Records of mtc_internet_monitor
-- ----------------------------
INSERT INTO "mtc_internet_monitor" VALUES ('e6773d4934e1', '381c876a9675', '上行流量', 1111, 1, '302', NULL, 'kaelthas.internet.inOctets');
INSERT INTO "mtc_internet_monitor" VALUES ('7784c74810d1', '381c876a9675', '下行流量', 222222, 1, '303', NULL, 'kaelthas.internet.outOctets');
INSERT INTO "mtc_internet_monitor" VALUES ('4921057f150c', '381c876a9675', '端口状态', 555555, 1, '301', NULL, 'kaelthas.internet.podStatus');

-- ----------------------------
-- Table structure for mtc_internet_trigger
-- ----------------------------

CREATE TABLE "mtc_internet_trigger" (
                                                 "id" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
                                                 "name" varchar(32) COLLATE "pg_catalog"."default",
                                                 "describe" varchar(128) COLLATE "pg_catalog"."default",
                                                 "status" int2,
                                                 "severity_level" int2,
                                                 "internet_id" varchar(20) COLLATE "pg_catalog"."default",
                                                 "expression" text COLLATE "pg_catalog"."default",
                                                 "scheme" int2,
                                                 "range_time" int2,
                                                 "percentage" int2
)
;
COMMENT ON COLUMN "mtc_internet_trigger"."id" IS '触发器主键';
COMMENT ON COLUMN "mtc_internet_trigger"."name" IS '触发器名称';
COMMENT ON COLUMN "mtc_internet_trigger"."describe" IS '描述（对触发器的描述）';
COMMENT ON COLUMN "mtc_internet_trigger"."status" IS '触发器状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_internet_trigger"."severity_level" IS '严重性（1，灾难 2，严重 3，一般严重 4，告警  5，信息  6，未分类）';
COMMENT ON COLUMN "mtc_internet_trigger"."internet_id" IS '网络id';
COMMENT ON COLUMN "mtc_internet_trigger"."expression" IS '触发器关系表达式';
COMMENT ON COLUMN "mtc_internet_trigger"."scheme" IS '1.平均值2.百分比,3.最后一个值';
COMMENT ON COLUMN "mtc_internet_trigger"."range_time" IS '时间范畴为几分钟的数据';
COMMENT ON COLUMN "mtc_internet_trigger"."percentage" IS '触发器的百分比达到多少进行告警';
COMMENT ON TABLE "mtc_internet_trigger" IS '数据库的触发器表';

-- ----------------------------
-- Records of mtc_internet_trigger
-- ----------------------------
INSERT INTO "mtc_internet_trigger" VALUES ('e1488a75dadd', '上行流量触发百分比达到30', '上行流量触发百分比达到30', 1, 3, '381c876a9675', '{{kaelthas.internet.inOctets}}>[10]', 3, 10, 30);
INSERT INTO "mtc_internet_trigger" VALUES ('5c5726820175', '上行流量大于10', '上行流量大于10G', 1, 4, '381c876a9675', '{{kaelthas.internet.inOctets}}>[10]', 1, NULL, NULL);
INSERT INTO "mtc_internet_trigger" VALUES ('355a023af949', '上行流量平均大于5', '上行流量平均大于5', 1, 5, '381c876a9675', '{{kaelthas.internet.inOctets}}>[5]', 2, 10, NULL);

-- ----------------------------
-- Table structure for mtc_internet_trigger_medium
-- ----------------------------

CREATE TABLE "mtc_internet_trigger_medium" (
                                                        "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                                        "trigger_id" varchar(32) COLLATE "pg_catalog"."default",
                                                        "medium_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_internet_trigger_medium"."trigger_id" IS '触发器id';
COMMENT ON COLUMN "mtc_internet_trigger_medium"."medium_id" IS '媒介id';
COMMENT ON TABLE "mtc_internet_trigger_medium" IS '触发器和媒介中间表';

-- ----------------------------
-- Records of mtc_internet_trigger_medium
-- ----------------------------
INSERT INTO "mtc_internet_trigger_medium" VALUES ('JzFpOJiIQqzU', '5c5726820175', '2');
INSERT INTO "mtc_internet_trigger_medium" VALUES ('JSrYNqfjsVgf', '5c5726820175', '3');
INSERT INTO "mtc_internet_trigger_medium" VALUES ('nQyiKVDoUuvo', '355a023af949', '2');
INSERT INTO "mtc_internet_trigger_medium" VALUES ('cIWtkHTRclHy', '355a023af949', '3');
INSERT INTO "mtc_internet_trigger_medium" VALUES ('KjFIACkhUibQ', 'e1488a75dadd', '2');
INSERT INTO "mtc_internet_trigger_medium" VALUES ('kRIhnqjlzHpL', 'e1488a75dadd', '3');

-- ----------------------------
-- Primary Key structure for table mtc_internet
-- ----------------------------
ALTER TABLE "mtc_internet" ADD CONSTRAINT "mtc_internet_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_internet_graphics
-- ----------------------------
ALTER TABLE "mtc_internet_graphics" ADD CONSTRAINT "mtc_ku_graphics_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_internet_graphics_monitor
-- ----------------------------
ALTER TABLE "mtc_internet_graphics_monitor" ADD CONSTRAINT "mtc_ku_graphics_monitor_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_internet_item
-- ----------------------------
ALTER TABLE "mtc_internet_item" ADD CONSTRAINT "mtc_internet_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_internet_monitor
-- ----------------------------
ALTER TABLE "mtc_internet_monitor" ADD CONSTRAINT "mtc_ku_monitor_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_internet_trigger
-- ----------------------------
ALTER TABLE "mtc_internet_trigger" ADD CONSTRAINT "mtc_ku_trigger_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_internet_trigger_medium
-- ----------------------------
ALTER TABLE "mtc_internet_trigger_medium" ADD CONSTRAINT "mtc_ku_trigger_medium_copy1_pkey" PRIMARY KEY ("id");
