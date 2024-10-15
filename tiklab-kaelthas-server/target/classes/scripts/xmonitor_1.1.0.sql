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

 Date: 12/08/2024 20:38:36
*/


-- ----------------------------
-- Table structure for mtc_customize_sql
-- ----------------------------

CREATE TABLE "mtc_customize_sql" (
                                              "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                              "expression" varchar(64) COLLATE "pg_catalog"."default",
                                              "describe" varchar(64) COLLATE "pg_catalog"."default",
                                              "db_id" varchar(32) COLLATE "pg_catalog"."default",
                                              "statement_sql" text COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_customize_sql"."expression" IS '自定义监控项表达式';
COMMENT ON COLUMN "mtc_customize_sql"."describe" IS '自定义监控描述';
COMMENT ON COLUMN "mtc_customize_sql"."db_id" IS '数据源id';
COMMENT ON COLUMN "mtc_customize_sql"."statement_sql" IS 'SQL语句';

-- ----------------------------
-- Table structure for mtc_db_graphics
-- ----------------------------

CREATE TABLE "mtc_db_graphics" (
                                            "id" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
                                            "name" varchar(32) COLLATE "pg_catalog"."default",
                                            "describe" varchar(64) COLLATE "pg_catalog"."default",
                                            "db_id" varchar(20) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_db_graphics"."name" IS '图表名称';
COMMENT ON COLUMN "mtc_db_graphics"."describe" IS '描述';
COMMENT ON COLUMN "mtc_db_graphics"."db_id" IS 'dbid';

-- ----------------------------
-- Table structure for mtc_db_graphics_monitor
-- ----------------------------

CREATE TABLE "mtc_db_graphics_monitor" (
                                                    "id" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
                                                    "graphics_id" varchar(20) COLLATE "pg_catalog"."default",
                                                    "monitor_id" varchar(20) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for mtc_db_info
-- ----------------------------

CREATE TABLE "mtc_db_info" (
                                        "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                        "name" varchar(32) COLLATE "pg_catalog"."default",
                                        "ip" varchar(20) COLLATE "pg_catalog"."default",
                                        "username" varchar(32) COLLATE "pg_catalog"."default",
                                        "password" varchar(32) COLLATE "pg_catalog"."default",
                                        "db_type" varchar(32) COLLATE "pg_catalog"."default",
                                        "state" int2,
                                        "usability" int2,
                                        "create_time" timestamp(6),
                                        "update_time" timestamp(6),
                                        "visibility" int4,
                                        "color" int2,
                                        "db_port" int2,
                                        "db_name" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_db_info"."id" IS 'id';
COMMENT ON COLUMN "mtc_db_info"."name" IS '数据源名称';
COMMENT ON COLUMN "mtc_db_info"."ip" IS 'ip地址';
COMMENT ON COLUMN "mtc_db_info"."username" IS '用户名称';
COMMENT ON COLUMN "mtc_db_info"."password" IS '用户密码';
COMMENT ON COLUMN "mtc_db_info"."db_type" IS '数据库类型';
COMMENT ON COLUMN "mtc_db_info"."state" IS '状态(1,开启,2,关闭)';
COMMENT ON COLUMN "mtc_db_info"."usability" IS '是否可用(1,可用,0,不可用)';
COMMENT ON COLUMN "mtc_db_info"."create_time" IS '创建时间';
COMMENT ON COLUMN "mtc_db_info"."update_time" IS '修改时间';
COMMENT ON COLUMN "mtc_db_info"."visibility" IS '1.公共2,私密';
COMMENT ON COLUMN "mtc_db_info"."color" IS '颜色类型';
COMMENT ON COLUMN "mtc_db_info"."db_port" IS '数据库端口';
COMMENT ON COLUMN "mtc_db_info"."db_name" IS '数据库名称';

-- ----------------------------
-- Table structure for mtc_db_item
-- ----------------------------

CREATE TABLE "mtc_db_item" (
                                        "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                        "expression" varchar(64) COLLATE "pg_catalog"."default",
                                        "describe" varchar(64) COLLATE "pg_catalog"."default",
                                        "data_type" int2,
                                        "db_type" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_db_item"."expression" IS '键值';
COMMENT ON COLUMN "mtc_db_item"."describe" IS '监控项描述';
COMMENT ON COLUMN "mtc_db_item"."data_type" IS '数据大小类型,相同数据类型的可以构成一张图';
COMMENT ON COLUMN "mtc_db_item"."db_type" IS '数据库类型';

-- ----------------------------
-- Table structure for mtc_db_monitor
-- ----------------------------

CREATE TABLE "mtc_db_monitor" (
                                           "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                           "db_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "name" varchar(64) COLLATE "pg_catalog"."default",
                                           "retention_time" int8,
                                           "status" int2,
                                           "db_item_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "monitor_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "monitor_type" int2,
                                           "dat_name" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_db_monitor"."db_id" IS '数据源id';
COMMENT ON COLUMN "mtc_db_monitor"."name" IS '监控项名称';
COMMENT ON COLUMN "mtc_db_monitor"."retention_time" IS '数据保留时间';
COMMENT ON COLUMN "mtc_db_monitor"."status" IS '监控项状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_db_monitor"."db_item_id" IS 'dbitem表的id';
COMMENT ON COLUMN "mtc_db_monitor"."monitor_id" IS '模板复制的时候使用,将本表的监控项复制一份出来';
COMMENT ON COLUMN "mtc_db_monitor"."monitor_type" IS '监控项来源(1.系统指标,2.自定义SQL)';
COMMENT ON COLUMN "mtc_db_monitor"."dat_name" IS '指定采集数据库的名称';

-- ----------------------------
-- Table structure for mtc_db_trigger
-- ----------------------------

CREATE TABLE "mtc_db_trigger" (
                                           "id" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
                                           "name" varchar(32) COLLATE "pg_catalog"."default",
                                           "describe" varchar(128) COLLATE "pg_catalog"."default",
                                           "state" int2,
                                           "severity_level" int2,
                                           "db_id" varchar(20) COLLATE "pg_catalog"."default",
                                           "expression" text COLLATE "pg_catalog"."default",
                                           "scheme" int2,
                                           "range_time" int2,
                                           "percentage" int2
)
;
COMMENT ON COLUMN "mtc_db_trigger"."id" IS '触发器主键';
COMMENT ON COLUMN "mtc_db_trigger"."name" IS '触发器名称';
COMMENT ON COLUMN "mtc_db_trigger"."describe" IS '描述（对触发器的描述）';
COMMENT ON COLUMN "mtc_db_trigger"."state" IS '触发器状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_db_trigger"."severity_level" IS '严重性（1，灾难 2，严重 3，一般严重 4，告警  5，信息  6，未分类）';
COMMENT ON COLUMN "mtc_db_trigger"."db_id" IS '数据库id';
COMMENT ON COLUMN "mtc_db_trigger"."expression" IS '触发器关系表达式';
COMMENT ON COLUMN "mtc_db_trigger"."scheme" IS '1.平均值2.百分比,3.最后一个值';
COMMENT ON COLUMN "mtc_db_trigger"."range_time" IS '时间范畴为几分钟的数据';
COMMENT ON COLUMN "mtc_db_trigger"."percentage" IS '触发器的百分比达到多少进行告警';
COMMENT ON TABLE "mtc_db_trigger" IS '数据库的触发器表';

-- ----------------------------
-- Table structure for mtc_db_trigger_medium
-- ----------------------------

CREATE TABLE "mtc_db_trigger_medium" (
                                                  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                                  "trigger_id" varchar(32) COLLATE "pg_catalog"."default",
                                                  "medium_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_db_trigger_medium"."trigger_id" IS '触发器id';
COMMENT ON COLUMN "mtc_db_trigger_medium"."medium_id" IS '媒介id';
COMMENT ON TABLE "mtc_db_trigger_medium" IS '触发器和媒介中间表';

-- ----------------------------
-- Primary Key structure for table mtc_customize_sql
-- ----------------------------
ALTER TABLE "mtc_customize_sql" ADD CONSTRAINT "customize_sql_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_db_graphics
-- ----------------------------
ALTER TABLE "mtc_db_graphics" ADD CONSTRAINT "mtc_db_graphics_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_db_graphics_monitor
-- ----------------------------
ALTER TABLE "mtc_db_graphics_monitor" ADD CONSTRAINT "mtc_db_graphics_monitor_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_db_info
-- ----------------------------
ALTER TABLE "mtc_db_info" ADD CONSTRAINT "mtc_db_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_db_item
-- ----------------------------
ALTER TABLE "mtc_db_item" ADD CONSTRAINT "mtc_db_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_db_monitor
-- ----------------------------
ALTER TABLE "mtc_db_monitor" ADD CONSTRAINT "mtc_db_monitor_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_db_trigger
-- ----------------------------
ALTER TABLE "mtc_db_trigger" ADD CONSTRAINT "mtc_trigger_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_db_trigger_medium
-- ----------------------------
ALTER TABLE "mtc_db_trigger_medium" ADD CONSTRAINT "mtc_trigger_medium_copy1_pkey" PRIMARY KEY ("id");



INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('3', 'systems.db.UpdatedRowsTrend', '每秒修改行数', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('5', 'systems.db.ReturnedRowsTrend', '返回行数', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('6', 'systems.db.RolledBackTransactionsTrend', '每秒回滚事务数', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('7', 'systems.db.CommittedTransactionsTrend', '每秒提交事务数', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('8', 'systems.db.ConflictsTrend', '事务冲突数', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('9', 'systems.db.CacheHitRate', '缓存命中率', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('10', 'systems.db.ActiveSessions', '活动会话数', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('11', 'systems.db.IdleSessions', '空闲会话数', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('12', 'systems.db.DeadlocksTrend', '僵直趋势', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('13', 'systems.db.TemporaryFilesTrend', '临时文件趋势', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('14', 'systems.db.Locks', '锁数量', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('4', 'systems.db.FetchedRowsTrend', '每秒读取行数', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('2', 'systems.db.DeletedRowsTrend', '每秒删除行数', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('1', 'systems.db.InsertedRowsTrend', '每秒插入行数', 1, 'Postgresql');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('103', 'systems.mysql.UpdatedRowsTrend', '每秒修改行数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('105', 'systems.mysql.ReturnedRowsTrend', '返回行数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('106', 'systems.mysql.RolledBackTransactionsTrend', '每秒回滚事务数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('107', 'systems.mysql.CommittedTransactionsTrend', '每秒提交事务数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('108', 'systems.mysql.ConflictsTrend', '事务冲突数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('109', 'systems.mysql.CacheHitRate', '缓存命中率', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('110', 'systems.mysql.ActiveSessions', '活动会话数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('111', 'systems.mysql.IdleSessions', '空闲会话数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('112', 'systems.mysql.DeadlocksTrend', '僵直趋势', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('113', 'systems.mysql.TemporaryFilesTrend', '临时文件趋势', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('114', 'systems.mysql.Locks', '锁数量', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('104', 'systems.mysql.FetchedRowsTrend', '每秒读取行数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('102', 'systems.mysql.DeletedRowsTrend', '每秒删除行数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('101', 'systems.mysql.InsertedRowsTrend', '每秒插入行数', 1, 'MYSQL');
