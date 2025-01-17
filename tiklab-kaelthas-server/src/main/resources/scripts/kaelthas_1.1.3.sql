CREATE TABLE "mtc_db_history2501" (
                               "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                               "db_id" varchar(32) COLLATE "pg_catalog"."default",
                               "monitor_id" varchar(32) COLLATE "pg_catalog"."default",
                               "report_data" text COLLATE "pg_catalog"."default",
                               "gather_time" timestamp(6)
)

CREATE TABLE pack_storage(
                             id VARCHAR(12) PRIMARY KEY,
                             db_id varchar (12) NOT NULL,
                             db_monitor_id varchar(12),
                             report_data text,
                             gather_time timestamp
);