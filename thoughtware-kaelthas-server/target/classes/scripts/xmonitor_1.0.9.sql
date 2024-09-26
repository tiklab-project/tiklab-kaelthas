ALTER TABLE mtc_db_item ADD db_type varchar(64) null;
COMMENT ON COLUMN mtc_db_item.db_type IS '数据库类型';

INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('103', 'systems.mysql.UpdatedRowsTrend', '每秒修改行数', 1, 'MYSQL');

INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('106', 'systems.mysql.RolledBackTransactionsTrend', '每秒回滚事务数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('107', 'systems.mysql.CommittedTransactionsTrend', '每秒提交事务数', 1, 'MYSQL');

INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('110', 'systems.mysql.ActiveSessions', '活动会话数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('111', 'systems.mysql.IdleSessions', '空闲会话数', 1, 'MYSQL');

INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('114', 'systems.mysql.Locks', '锁数量', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('104', 'systems.mysql.FetchedRowsTrend', '每秒读取行数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('102', 'systems.mysql.DeletedRowsTrend', '每秒删除行数', 1, 'MYSQL');
INSERT INTO "mtc_db_item" ("id", "expression", "describe", "data_type", "db_type") VALUES ('101', 'systems.mysql.InsertedRowsTrend', '每秒插入行数', 1, 'MYSQL');


UPDATE "mtc_db_item" SET "expression" = 'systems.db.UpdatedRowsTrend', "describe" = '每秒修改行数', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '3';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.ReturnedRowsTrend', "describe" = '返回行数', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '5';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.RolledBackTransactionsTrend', "describe" = '每秒回滚事务数', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '6';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.CommittedTransactionsTrend', "describe" = '每秒提交事务数', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '7';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.ConflictsTrend', "describe" = '事务冲突数', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '8';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.CacheHitRate', "describe" = '缓存命中率', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '9';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.ActiveSessions', "describe" = '活动会话数', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '10';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.IdleSessions', "describe" = '空闲会话数', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '11';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.DeadlocksTrend', "describe" = '僵直趋势', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '12';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.TemporaryFilesTrend', "describe" = '临时文件趋势', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '13';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.Locks', "describe" = '锁数量', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '14';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.FetchedRowsTrend', "describe" = '每秒读取行数', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '4';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.DeletedRowsTrend', "describe" = '每秒删除行数', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '2';
UPDATE "mtc_db_item" SET "expression" = 'systems.db.InsertedRowsTrend', "describe" = '每秒插入行数', "data_type" = 1, "db_type" = 'Postgresql' WHERE "id" = '1';

ALTER TABLE mtc_alarm ADD ip VARCHAR;
ALTER TABLE mtc_alarm ADD host_name VARCHAR;
ALTER TABLE mtc_alarm ADD severity_level int2;