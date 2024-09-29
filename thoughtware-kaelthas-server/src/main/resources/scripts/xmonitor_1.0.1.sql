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

 Date: 29/09/2024 16:21:29
*/


-- ----------------------------
-- Table structure for mtc_alarm
-- ----------------------------
CREATE TABLE "mtc_alarm" (
                             "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                             "host_id" varchar(128) COLLATE "pg_catalog"."default",
                             "status" int2,
                             "send_message" varchar(128) COLLATE "pg_catalog"."default",
                             "trigger_id" varchar(128) COLLATE "pg_catalog"."default",
                             "alert_time" timestamp(6),
                             "duration" varchar(128) COLLATE "pg_catalog"."default",
                             "is_send" int2,
                             "resolution_time" timestamp(6),
                             "machine_type" int2,
                             "ip" varchar(64) COLLATE "pg_catalog"."default",
                             "host_name" varchar(64) COLLATE "pg_catalog"."default",
                             "severity_level" int2
)
;
COMMENT ON COLUMN "mtc_alarm"."id" IS '告警id';
COMMENT ON COLUMN "mtc_alarm"."host_id" IS '主机id';
COMMENT ON COLUMN "mtc_alarm"."status" IS '状态（1，已解决  2，未解决）';
COMMENT ON COLUMN "mtc_alarm"."send_message" IS '发送的消息';
COMMENT ON COLUMN "mtc_alarm"."trigger_id" IS '触发器id';
COMMENT ON COLUMN "mtc_alarm"."alert_time" IS '告警的时间';
COMMENT ON COLUMN "mtc_alarm"."duration" IS '告警持续时间';
COMMENT ON COLUMN "mtc_alarm"."is_send" IS '消息是否发送成功（1，成功   2，失败）';
COMMENT ON COLUMN "mtc_alarm"."resolution_time" IS '解决告警的时间';
COMMENT ON COLUMN "mtc_alarm"."machine_type" IS '机器类型,(1,主机,2.数据库)';
COMMENT ON COLUMN "mtc_alarm"."ip" IS '主机ip';
COMMENT ON COLUMN "mtc_alarm"."host_name" IS '设备名称';
COMMENT ON COLUMN "mtc_alarm"."severity_level" IS '告警等级';
COMMENT ON TABLE "mtc_alarm" IS '告警表';

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
-- Table structure for mtc_db_dynamic
-- ----------------------------
CREATE TABLE "mtc_db_dynamic" (
                                  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                  "db_id" varchar(32) COLLATE "pg_catalog"."default",
                                  "name" varchar(128) COLLATE "pg_catalog"."default",
                                  "time" timestamp(6)
)
;
COMMENT ON COLUMN "mtc_db_dynamic"."db_id" IS '数据库id';
COMMENT ON COLUMN "mtc_db_dynamic"."name" IS '动态名称';
COMMENT ON COLUMN "mtc_db_dynamic"."time" IS '动态创建时间';
COMMENT ON TABLE "mtc_db_dynamic" IS '数据库的动态描述';

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
COMMENT ON COLUMN "mtc_db_trigger"."scheme" IS '1.最后一个值,2.平均值3.百分比,';
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
-- Table structure for mtc_dynamic
-- ----------------------------
CREATE TABLE "mtc_dynamic" (
                               "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                               "name" varchar(128) COLLATE "pg_catalog"."default",
                               "update_time" timestamp(6),
                               "host_id" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON TABLE "mtc_dynamic" IS '首页的动态表';

-- ----------------------------
-- Table structure for mtc_graphics
-- ----------------------------
CREATE TABLE "mtc_graphics" (
                                "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                "name" varchar(64) COLLATE "pg_catalog"."default",
                                "describe" varchar(64) COLLATE "pg_catalog"."default",
                                "width" varchar(32) COLLATE "pg_catalog"."default",
                                "height" varchar(32) COLLATE "pg_catalog"."default",
                                "host_id" varchar(32) COLLATE "pg_catalog"."default",
                                "source" int2
)
;
COMMENT ON COLUMN "mtc_graphics"."id" IS 'id';
COMMENT ON COLUMN "mtc_graphics"."name" IS '图形名称';
COMMENT ON COLUMN "mtc_graphics"."describe" IS '描述';
COMMENT ON COLUMN "mtc_graphics"."width" IS '宽度';
COMMENT ON COLUMN "mtc_graphics"."height" IS '高度';
COMMENT ON COLUMN "mtc_graphics"."host_id" IS '主机id';
COMMENT ON COLUMN "mtc_graphics"."source" IS '1.来源于主机中监控项创建2.来源于模板中监控项创建';
COMMENT ON TABLE "mtc_graphics" IS '根据监控项设置的图表';

-- ----------------------------
-- Table structure for mtc_graphics_monitor
-- ----------------------------
CREATE TABLE "mtc_graphics_monitor" (
                                        "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                        "monitor_id" varchar(128) COLLATE "pg_catalog"."default",
                                        "graphics_id" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_graphics_monitor"."monitor_id" IS '监控项id';
COMMENT ON COLUMN "mtc_graphics_monitor"."graphics_id" IS '图表id';
COMMENT ON TABLE "mtc_graphics_monitor" IS '监控项和图表的关联表';

-- ----------------------------
-- Table structure for mtc_history
-- ----------------------------
CREATE TABLE "mtc_history" (
                               "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                               "host_id" varchar(32) COLLATE "pg_catalog"."default",
                               "monitor_id" varchar(32) COLLATE "pg_catalog"."default",
                               "report_data" text COLLATE "pg_catalog"."default",
                               "gather_time" timestamp(6)
)
;
COMMENT ON COLUMN "mtc_history"."id" IS '历史数据id';
COMMENT ON COLUMN "mtc_history"."host_id" IS '主机id';
COMMENT ON COLUMN "mtc_history"."monitor_id" IS '监控项中间表id';
COMMENT ON COLUMN "mtc_history"."report_data" IS '当前数值';
COMMENT ON COLUMN "mtc_history"."gather_time" IS '检查时间';
COMMENT ON TABLE "mtc_history" IS '历史数据表';

-- ----------------------------
-- Table structure for mtc_history_fifteen_minute
-- ----------------------------
CREATE TABLE "mtc_history_fifteen_minute" (
                                              "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                              "host_id" varchar(32) COLLATE "pg_catalog"."default",
                                              "monitor_id" varchar(32) COLLATE "pg_catalog"."default",
                                              "report_data" text COLLATE "pg_catalog"."default",
                                              "gather_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for mtc_history_five_minute
-- ----------------------------
CREATE TABLE "mtc_history_five_minute" (
                                           "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                           "host_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "monitor_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "report_data" text COLLATE "pg_catalog"."default",
                                           "gather_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for mtc_history_one_minute
-- ----------------------------
CREATE TABLE "mtc_history_one_minute" (
                                          "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                          "host_id" varchar(32) COLLATE "pg_catalog"."default",
                                          "monitor_id" varchar(32) COLLATE "pg_catalog"."default",
                                          "report_data" text COLLATE "pg_catalog"."default",
                                          "gather_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for mtc_host
-- ----------------------------
CREATE TABLE "mtc_host" (
                            "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                            "name" varchar(128) COLLATE "pg_catalog"."default",
                            "ip" varchar(128) COLLATE "pg_catalog"."default",
                            "host_group_id" varchar(128) COLLATE "pg_catalog"."default",
                            "state" int2,
                            "usability" int2,
                            "describe" varchar(128) COLLATE "pg_catalog"."default",
                            "create_time" varchar(128) COLLATE "pg_catalog"."default",
                            "update_time" varchar(128) COLLATE "pg_catalog"."default",
                            "visibility" int2,
                            "color" int2
)
;
COMMENT ON COLUMN "mtc_host"."id" IS '主机id';
COMMENT ON COLUMN "mtc_host"."name" IS '主机名称';
COMMENT ON COLUMN "mtc_host"."ip" IS '主机的IP地址';
COMMENT ON COLUMN "mtc_host"."host_group_id" IS '主机组id';
COMMENT ON COLUMN "mtc_host"."state" IS '主机状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_host"."usability" IS '主机可用性（1. 可用，2. 不可用）';
COMMENT ON COLUMN "mtc_host"."describe" IS '主机描述';
COMMENT ON COLUMN "mtc_host"."create_time" IS '创建时间';
COMMENT ON COLUMN "mtc_host"."update_time" IS '最后一次编辑时间';
COMMENT ON COLUMN "mtc_host"."visibility" IS '1.公共  2.私密';
COMMENT ON COLUMN "mtc_host"."color" IS '颜色,随机定义(0,1,2,3,4,5)';
COMMENT ON TABLE "mtc_host" IS '主机表';

-- ----------------------------
-- Table structure for mtc_host_dynamic
-- ----------------------------
CREATE TABLE "mtc_host_dynamic" (
                                    "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                    "name" varchar(128) COLLATE "pg_catalog"."default",
                                    "time" timestamp(6),
                                    "host_id" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_host_dynamic"."id" IS '主键';
COMMENT ON COLUMN "mtc_host_dynamic"."name" IS '动态名称';
COMMENT ON COLUMN "mtc_host_dynamic"."time" IS '动态时间';
COMMENT ON COLUMN "mtc_host_dynamic"."host_id" IS '主机id';
COMMENT ON TABLE "mtc_host_dynamic" IS '主机动态表';

-- ----------------------------
-- Table structure for mtc_host_group
-- ----------------------------
CREATE TABLE "mtc_host_group" (
                                  "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                  "name" varchar(128) COLLATE "pg_catalog"."default",
                                  "describe" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_host_group"."id" IS '主机组id';
COMMENT ON COLUMN "mtc_host_group"."name" IS '名称';
COMMENT ON COLUMN "mtc_host_group"."describe" IS '描述';
COMMENT ON TABLE "mtc_host_group" IS '主机组';

-- ----------------------------
-- Table structure for mtc_host_monitor
-- ----------------------------
CREATE TABLE "mtc_host_monitor" (
                                    "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                    "host_id" varchar(128) COLLATE "pg_catalog"."default",
                                    "name" varchar(128) COLLATE "pg_catalog"."default",
                                    "monitor_item_id" varchar(128) COLLATE "pg_catalog"."default",
                                    "interval_time" varchar(64) COLLATE "pg_catalog"."default",
                                    "data_retention_time" varchar(64) COLLATE "pg_catalog"."default",
                                    "monitor_status" int2,
                                    "expression" text COLLATE "pg_catalog"."default",
                                    "information" varchar(128) COLLATE "pg_catalog"."default",
                                    "source" int2,
                                    "template_id" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_host_monitor"."id" IS '监控项id';
COMMENT ON COLUMN "mtc_host_monitor"."host_id" IS '主机id';
COMMENT ON COLUMN "mtc_host_monitor"."name" IS '监控项名称';
COMMENT ON COLUMN "mtc_host_monitor"."monitor_item_id" IS 'monitor_item的id';
COMMENT ON COLUMN "mtc_host_monitor"."interval_time" IS '间隔时间(s)';
COMMENT ON COLUMN "mtc_host_monitor"."data_retention_time" IS '数据保留时间(s)';
COMMENT ON COLUMN "mtc_host_monitor"."monitor_status" IS '监控项状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_host_monitor"."expression" IS '表达式';
COMMENT ON COLUMN "mtc_host_monitor"."information" IS '监控项是否监测成功后的信息';
COMMENT ON COLUMN "mtc_host_monitor"."source" IS '判断来源,(1,主机,2,模板)';
COMMENT ON COLUMN "mtc_host_monitor"."template_id" IS '模板id,为空的时候hostid为模板id,不为空的时候证明被主机引用了';
COMMENT ON TABLE "mtc_host_monitor" IS '监控项与主机关联表';

-- ----------------------------
-- Table structure for mtc_host_recent
-- ----------------------------
CREATE TABLE "mtc_host_recent" (
                                   "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                   "host_id" varchar(255) COLLATE "pg_catalog"."default",
                                   "user_id" varchar(255) COLLATE "pg_catalog"."default",
                                   "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "mtc_host_recent"."host_id" IS '主机id';
COMMENT ON COLUMN "mtc_host_recent"."user_id" IS '用户id';
COMMENT ON COLUMN "mtc_host_recent"."update_time" IS '修改时间';
COMMENT ON TABLE "mtc_host_recent" IS '主机最近使用表';

-- ----------------------------
-- Table structure for mtc_host_template
-- ----------------------------
CREATE TABLE "mtc_host_template" (
                                     "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                     "template_id" varchar(128) COLLATE "pg_catalog"."default",
                                     "host_id" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_host_template"."template_id" IS '模板表id';
COMMENT ON COLUMN "mtc_host_template"."host_id" IS '主机表id';
COMMENT ON TABLE "mtc_host_template" IS '主机模板中间表';

-- ----------------------------
-- Table structure for mtc_host_trigger
-- ----------------------------
CREATE TABLE "mtc_host_trigger" (
                                    "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                    "host_id" varchar(128) COLLATE "pg_catalog"."default",
                                    "trigger_id" varchar(128) COLLATE "pg_catalog"."default",
                                    "template_id" varchar(128) COLLATE "pg_catalog"."default",
                                    "source" int2
)
;
COMMENT ON COLUMN "mtc_host_trigger"."host_id" IS '主机id';
COMMENT ON COLUMN "mtc_host_trigger"."trigger_id" IS '触发器id';
COMMENT ON COLUMN "mtc_host_trigger"."template_id" IS '模板id';
COMMENT ON COLUMN "mtc_host_trigger"."source" IS '1.主机,2.模板';
COMMENT ON TABLE "mtc_host_trigger" IS '主机与触发器中间表';

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
-- Table structure for mtc_item
-- ----------------------------
CREATE TABLE "mtc_item" (
                            "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                            "name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                            "data_categories" varchar(128) COLLATE "pg_catalog"."default",
                            "data_type" int2,
                            "data_subclass" varchar(128) COLLATE "pg_catalog"."default",
                            "report_type" int2
)
;
COMMENT ON COLUMN "mtc_item"."id" IS 'monitor  item的id';
COMMENT ON COLUMN "mtc_item"."name" IS 'monitor的item名称';
COMMENT ON COLUMN "mtc_item"."data_categories" IS '监控大类';
COMMENT ON COLUMN "mtc_item"."data_type" IS '数据的小类';
COMMENT ON COLUMN "mtc_item"."data_subclass" IS '数据小类名称';
COMMENT ON COLUMN "mtc_item"."report_type" IS '数据类型(1.百分比,2.json,3.G,4.数值)';
COMMENT ON TABLE "mtc_item" IS '监控项表';

-- ----------------------------
-- Table structure for mtc_ku_graphics
-- ----------------------------
CREATE TABLE "mtc_ku_graphics" (
                                   "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                   "name" varchar(64) COLLATE "pg_catalog"."default",
                                   "describe" varchar(64) COLLATE "pg_catalog"."default",
                                   "ku_id" varchar(32) COLLATE "pg_catalog"."default",
                                   "source" int2
)
;
COMMENT ON COLUMN "mtc_ku_graphics"."id" IS 'id';
COMMENT ON COLUMN "mtc_ku_graphics"."name" IS '图形名称';
COMMENT ON COLUMN "mtc_ku_graphics"."describe" IS '描述';
COMMENT ON COLUMN "mtc_ku_graphics"."ku_id" IS '集群id';
COMMENT ON COLUMN "mtc_ku_graphics"."source" IS '1.来源于主机中监控项创建2.来源于模板中监控项创建';
COMMENT ON TABLE "mtc_ku_graphics" IS '根据监控项设置的图表';

-- ----------------------------
-- Table structure for mtc_ku_graphics_monitor
-- ----------------------------
CREATE TABLE "mtc_ku_graphics_monitor" (
                                           "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                           "monitor_id" varchar(128) COLLATE "pg_catalog"."default",
                                           "graphics_id" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_ku_graphics_monitor"."monitor_id" IS '监控项id';
COMMENT ON COLUMN "mtc_ku_graphics_monitor"."graphics_id" IS '图表id';
COMMENT ON TABLE "mtc_ku_graphics_monitor" IS '监控项和图表的关联表';

-- ----------------------------
-- Table structure for mtc_ku_info
-- ----------------------------
CREATE TABLE "mtc_ku_info" (
                               "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                               "name" varchar(64) COLLATE "pg_catalog"."default",
                               "ip" varchar(64) COLLATE "pg_catalog"."default",
                               "create_time" timestamp(6),
                               "update_time" timestamp(6),
                               "color" int2,
                               "port" int2,
                               "api_token" text COLLATE "pg_catalog"."default",
                               "status" int2,
                               "usability" int2
)
;
COMMENT ON COLUMN "mtc_ku_info"."id" IS 'id';
COMMENT ON COLUMN "mtc_ku_info"."name" IS '名称';
COMMENT ON COLUMN "mtc_ku_info"."ip" IS 'ip';
COMMENT ON COLUMN "mtc_ku_info"."create_time" IS '创建时间';
COMMENT ON COLUMN "mtc_ku_info"."update_time" IS '修改时间';
COMMENT ON COLUMN "mtc_ku_info"."color" IS '颜色类型';
COMMENT ON COLUMN "mtc_ku_info"."port" IS 'apiserver访问的端口';
COMMENT ON COLUMN "mtc_ku_info"."api_token" IS '用于访问apiserver的token';
COMMENT ON COLUMN "mtc_ku_info"."status" IS '是否开启使用';
COMMENT ON COLUMN "mtc_ku_info"."usability" IS '是否可用(1.可用,0.不可用)';

-- ----------------------------
-- Table structure for mtc_ku_item
-- ----------------------------
CREATE TABLE "mtc_ku_item" (
                               "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                               "expression" varchar(128) COLLATE "pg_catalog"."default",
                               "describe" varchar(128) COLLATE "pg_catalog"."default",
                               "kubernetes_type" varchar(32) COLLATE "pg_catalog"."default",
                               "report_type" int2,
                               "name" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON TABLE "mtc_ku_item" IS 'Kubernetes的监控指标表';

-- ----------------------------
-- Table structure for mtc_ku_monitor
-- ----------------------------
CREATE TABLE "mtc_ku_monitor" (
                                  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                  "ku_id" varchar(32) COLLATE "pg_catalog"."default",
                                  "name" varchar(64) COLLATE "pg_catalog"."default",
                                  "retention_time" int8,
                                  "status" int2,
                                  "ku_item_id" varchar(32) COLLATE "pg_catalog"."default",
                                  "monitor_id" varchar(32) COLLATE "pg_catalog"."default",
                                  "expression" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_ku_monitor"."id" IS 'id';
COMMENT ON COLUMN "mtc_ku_monitor"."ku_id" IS '数据源id';
COMMENT ON COLUMN "mtc_ku_monitor"."name" IS '监控项名称';
COMMENT ON COLUMN "mtc_ku_monitor"."retention_time" IS '数据保留时间';
COMMENT ON COLUMN "mtc_ku_monitor"."status" IS '监控项状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_ku_monitor"."ku_item_id" IS 'dbitem表的id';
COMMENT ON COLUMN "mtc_ku_monitor"."monitor_id" IS '模板复制的时候使用,将本表的监控项复制一份出来';
COMMENT ON COLUMN "mtc_ku_monitor"."expression" IS '表达式';

-- ----------------------------
-- Table structure for mtc_ku_trigger
-- ----------------------------
CREATE TABLE "mtc_ku_trigger" (
                                  "id" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
                                  "name" varchar(32) COLLATE "pg_catalog"."default",
                                  "describe" varchar(128) COLLATE "pg_catalog"."default",
                                  "state" int2,
                                  "severity_level" int2,
                                  "ku_id" varchar(20) COLLATE "pg_catalog"."default",
                                  "expression" text COLLATE "pg_catalog"."default",
                                  "scheme" int2,
                                  "range_time" int2,
                                  "percentage" int2
)
;
COMMENT ON COLUMN "mtc_ku_trigger"."id" IS '触发器主键';
COMMENT ON COLUMN "mtc_ku_trigger"."name" IS '触发器名称';
COMMENT ON COLUMN "mtc_ku_trigger"."describe" IS '描述（对触发器的描述）';
COMMENT ON COLUMN "mtc_ku_trigger"."state" IS '触发器状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_ku_trigger"."severity_level" IS '严重性（1，灾难 2，严重 3，一般严重 4，告警  5，信息  6，未分类）';
COMMENT ON COLUMN "mtc_ku_trigger"."ku_id" IS '数据库id';
COMMENT ON COLUMN "mtc_ku_trigger"."expression" IS '触发器关系表达式';
COMMENT ON COLUMN "mtc_ku_trigger"."scheme" IS '1.平均值2.百分比,3.最后一个值';
COMMENT ON COLUMN "mtc_ku_trigger"."range_time" IS '时间范畴为几分钟的数据';
COMMENT ON COLUMN "mtc_ku_trigger"."percentage" IS '触发器的百分比达到多少进行告警';
COMMENT ON TABLE "mtc_ku_trigger" IS '数据库的触发器表';

-- ----------------------------
-- Table structure for mtc_ku_trigger_medium
-- ----------------------------
CREATE TABLE "mtc_ku_trigger_medium" (
                                         "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                         "trigger_id" varchar(32) COLLATE "pg_catalog"."default",
                                         "medium_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_ku_trigger_medium"."trigger_id" IS '触发器id';
COMMENT ON COLUMN "mtc_ku_trigger_medium"."medium_id" IS '媒介id';
COMMENT ON TABLE "mtc_ku_trigger_medium" IS '触发器和媒介中间表';

-- ----------------------------
-- Table structure for mtc_medium
-- ----------------------------
CREATE TABLE "mtc_medium" (
                              "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                              "name" varchar(128) COLLATE "pg_catalog"."default",
                              "type" int2,
                              "status" int2,
                              "details" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_medium"."id" IS '媒介id';
COMMENT ON COLUMN "mtc_medium"."name" IS '媒介名称';
COMMENT ON COLUMN "mtc_medium"."type" IS '媒介类型';
COMMENT ON COLUMN "mtc_medium"."status" IS '媒介状态（1，开启   0，关闭）';
COMMENT ON COLUMN "mtc_medium"."details" IS '媒介详情，描述媒介的具体信息';
COMMENT ON TABLE "mtc_medium" IS '媒介表';

-- ----------------------------
-- Table structure for mtc_message
-- ----------------------------
CREATE TABLE "mtc_message" (
                               "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                               "medium_id" varchar(32) COLLATE "pg_catalog"."default",
                               "alarm_id" varchar(32) COLLATE "pg_catalog"."default",
                               "information" varchar(128) COLLATE "pg_catalog"."default",
                               "send_time" timestamp(6)
)
;
COMMENT ON COLUMN "mtc_message"."medium_id" IS '发送消息id';
COMMENT ON COLUMN "mtc_message"."alarm_id" IS '告警表id';
COMMENT ON COLUMN "mtc_message"."information" IS '告警信息';
COMMENT ON COLUMN "mtc_message"."send_time" IS '发送消息的时间';

-- ----------------------------
-- Table structure for mtc_template
-- ----------------------------
CREATE TABLE "mtc_template" (
                                "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                "name" varchar(128) COLLATE "pg_catalog"."default",
                                "is_open" int2,
                                "describe" varchar(128) COLLATE "pg_catalog"."default",
                                "superior_id" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_template"."id" IS '模板id';
COMMENT ON COLUMN "mtc_template"."name" IS '模板名称';
COMMENT ON COLUMN "mtc_template"."is_open" IS '模板状态（1，开启 0，关闭）';
COMMENT ON COLUMN "mtc_template"."describe" IS '描述';
COMMENT ON COLUMN "mtc_template"."superior_id" IS '上级模板id';
COMMENT ON TABLE "mtc_template" IS '模板表';

-- ----------------------------
-- Table structure for mtc_template_monitor
-- ----------------------------
CREATE TABLE "mtc_template_monitor" (
                                        "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                        "template_id" varchar(128) COLLATE "pg_catalog"."default",
                                        "name" varchar(128) COLLATE "pg_catalog"."default",
                                        "monitor_item_id" varchar(128) COLLATE "pg_catalog"."default",
                                        "interval_time" varchar(64) COLLATE "pg_catalog"."default",
                                        "data_retention_time" varchar(64) COLLATE "pg_catalog"."default",
                                        "monitor_status" int2,
                                        "information" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_template_monitor"."template_id" IS '模板id';
COMMENT ON COLUMN "mtc_template_monitor"."name" IS '监控项名称';
COMMENT ON COLUMN "mtc_template_monitor"."monitor_item_id" IS 'monitor_item的id';
COMMENT ON COLUMN "mtc_template_monitor"."interval_time" IS '间隔时间(s)';
COMMENT ON COLUMN "mtc_template_monitor"."data_retention_time" IS '数据保留时间(s)';
COMMENT ON COLUMN "mtc_template_monitor"."monitor_status" IS '监控项状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_template_monitor"."information" IS '监控项是否监测成功后的信息';
COMMENT ON TABLE "mtc_template_monitor" IS '模板下的监控项';

-- ----------------------------
-- Table structure for mtc_trigger
-- ----------------------------
CREATE TABLE "mtc_trigger" (
                               "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                               "name" varchar(128) COLLATE "pg_catalog"."default",
                               "describe" varchar(128) COLLATE "pg_catalog"."default",
                               "state" int2,
                               "severity_level" int2,
                               "monitor_id" varchar(128) COLLATE "pg_catalog"."default",
                               "host_id" varchar(128) COLLATE "pg_catalog"."default",
                               "medium_type" int2,
                               "source" int2,
                               "expression" text COLLATE "pg_catalog"."default",
                               "scheme" int2,
                               "range_time" int8,
                               "percentage" int4
)
;
COMMENT ON COLUMN "mtc_trigger"."id" IS '触发器主键';
COMMENT ON COLUMN "mtc_trigger"."name" IS '触发器名称';
COMMENT ON COLUMN "mtc_trigger"."describe" IS '描述（对触发器的描述）';
COMMENT ON COLUMN "mtc_trigger"."state" IS '触发器状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_trigger"."severity_level" IS '严重性（1，灾难 2，严重 3，一般严重 4，告警  5，信息  6，未分类）';
COMMENT ON COLUMN "mtc_trigger"."monitor_id" IS '监控项id';
COMMENT ON COLUMN "mtc_trigger"."host_id" IS '主机id';
COMMENT ON COLUMN "mtc_trigger"."medium_type" IS '发送消息方式(1.电子邮件,2.微信公众号,3.钉钉,4.短信)';
COMMENT ON COLUMN "mtc_trigger"."source" IS '创建来源（1，主机监控   2，模板监控）';
COMMENT ON COLUMN "mtc_trigger"."expression" IS '触发器关系表达式';
COMMENT ON COLUMN "mtc_trigger"."scheme" IS '1.平均值2.百分比,3.最后一个值';
COMMENT ON COLUMN "mtc_trigger"."range_time" IS '时间范畴为几分钟的数据';
COMMENT ON COLUMN "mtc_trigger"."percentage" IS '触发器的百分比达到多少进行告警';
COMMENT ON TABLE "mtc_trigger" IS '触发器表';

-- ----------------------------
-- Table structure for mtc_trigger_expression
-- ----------------------------
CREATE TABLE "mtc_trigger_expression" (
                                          "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                          "expression" varchar(128) COLLATE "pg_catalog"."default",
                                          "operator" int2,
                                          "numerical_value" int4,
                                          "trigger_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                          "source" int2,
                                          "monitor_id" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_trigger_expression"."id" IS '表达式id';
COMMENT ON COLUMN "mtc_trigger_expression"."expression" IS '表达式';
COMMENT ON COLUMN "mtc_trigger_expression"."operator" IS '运算符（1.>  2.<  3.=  4.>=  5.<=  6.<>)';
COMMENT ON COLUMN "mtc_trigger_expression"."numerical_value" IS '表达式的数值';
COMMENT ON COLUMN "mtc_trigger_expression"."trigger_id" IS '触发器主键';
COMMENT ON COLUMN "mtc_trigger_expression"."source" IS '来源（1，主机   2，模板）';
COMMENT ON COLUMN "mtc_trigger_expression"."monitor_id" IS '监控项id';
COMMENT ON TABLE "mtc_trigger_expression" IS '条件表达式的表';

-- ----------------------------
-- Table structure for mtc_trigger_medium
-- ----------------------------
CREATE TABLE "mtc_trigger_medium" (
                                      "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                      "trigger_id" varchar(32) COLLATE "pg_catalog"."default",
                                      "medium_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_trigger_medium"."trigger_id" IS '触发器id';
COMMENT ON COLUMN "mtc_trigger_medium"."medium_id" IS '媒介id';
COMMENT ON TABLE "mtc_trigger_medium" IS '触发器和媒介中间表';

-- ----------------------------
-- Primary Key structure for table mtc_alarm
-- ----------------------------
ALTER TABLE "mtc_alarm" ADD CONSTRAINT "alarm_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_customize_sql
-- ----------------------------
ALTER TABLE "mtc_customize_sql" ADD CONSTRAINT "customize_sql_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_db_dynamic
-- ----------------------------
ALTER TABLE "mtc_db_dynamic" ADD CONSTRAINT "mtc_db_dynamic_pkey" PRIMARY KEY ("id");

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

-- ----------------------------
-- Primary Key structure for table mtc_dynamic
-- ----------------------------
ALTER TABLE "mtc_dynamic" ADD CONSTRAINT "mtc__dynamic_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_graphics
-- ----------------------------
ALTER TABLE "mtc_graphics" ADD CONSTRAINT "graphics_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_graphics_monitor
-- ----------------------------
ALTER TABLE "mtc_graphics_monitor" ADD CONSTRAINT "graphics_list_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_history
-- ----------------------------
ALTER TABLE "mtc_history" ADD CONSTRAINT "historical_information_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_host
-- ----------------------------
ALTER TABLE "mtc_host" ADD CONSTRAINT "host_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_host_dynamic
-- ----------------------------
ALTER TABLE "mtc_host_dynamic" ADD CONSTRAINT "host_dynamic_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_host_group
-- ----------------------------
ALTER TABLE "mtc_host_group" ADD CONSTRAINT "hosts_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_host_monitor
-- ----------------------------
ALTER TABLE "mtc_host_monitor" ADD CONSTRAINT "monitor_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_host_recent
-- ----------------------------
ALTER TABLE "mtc_host_recent" ADD CONSTRAINT "host_recent_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_host_template
-- ----------------------------
ALTER TABLE "mtc_host_template" ADD CONSTRAINT "host_template_relation_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_host_trigger
-- ----------------------------
ALTER TABLE "mtc_host_trigger" ADD CONSTRAINT "host_trigger_relation_pkey" PRIMARY KEY ("id");

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

-- ----------------------------
-- Primary Key structure for table mtc_item
-- ----------------------------
ALTER TABLE "mtc_item" ADD CONSTRAINT "agent_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_graphics
-- ----------------------------
ALTER TABLE "mtc_ku_graphics" ADD CONSTRAINT "mtc_graphics_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_graphics_monitor
-- ----------------------------
ALTER TABLE "mtc_ku_graphics_monitor" ADD CONSTRAINT "mtc_graphics_monitor_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_info
-- ----------------------------
ALTER TABLE "mtc_ku_info" ADD CONSTRAINT "mtc_kubernetes_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_item
-- ----------------------------
ALTER TABLE "mtc_ku_item" ADD CONSTRAINT "mtc_kubernetes_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_monitor
-- ----------------------------
ALTER TABLE "mtc_ku_monitor" ADD CONSTRAINT "mtc_db_monitor_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_trigger
-- ----------------------------
ALTER TABLE "mtc_ku_trigger" ADD CONSTRAINT "mtc_db_trigger_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_trigger_medium
-- ----------------------------
ALTER TABLE "mtc_ku_trigger_medium" ADD CONSTRAINT "mtc_db_trigger_medium_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_medium
-- ----------------------------
ALTER TABLE "mtc_medium" ADD CONSTRAINT "medium_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_message
-- ----------------------------
ALTER TABLE "mtc_message" ADD CONSTRAINT "message_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_template
-- ----------------------------
ALTER TABLE "mtc_template" ADD CONSTRAINT "template_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_template_monitor
-- ----------------------------
ALTER TABLE "mtc_template_monitor" ADD CONSTRAINT "template_monitor_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_trigger
-- ----------------------------
ALTER TABLE "mtc_trigger" ADD CONSTRAINT "trigger_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_trigger_expression
-- ----------------------------
ALTER TABLE "mtc_trigger_expression" ADD CONSTRAINT "expression_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_trigger_medium
-- ----------------------------
ALTER TABLE "mtc_trigger_medium" ADD CONSTRAINT "trigger_medium_pkey" PRIMARY KEY ("id");
