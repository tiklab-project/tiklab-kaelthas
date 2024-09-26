package io.thoughtware.kaelthas;

public class EntityConversion {
    public static void main(String[] args) {
        String sql = """
                systems.db.DeletedRowsTrend(每秒删除行数)
                systems.db.Fetched Rows Trend(每秒读取行数)
                systems.db.Updated Rows Trend(每秒修改行数)
                systems.db.Inserted Rows Trend(每秒插入行数)
                systems.db.Returned Rows Trend(返回行数)
                systems.db.Rolled Back Transactions Trend(每秒回滚事务数)
                systems.db.Committed Transactions Trend(每秒提交事务数)
                systems.db.Conflicts Trend(事务冲突数)
                systems.db.Cache Hit Rate(缓存命中率)
                systems.db.Active Sessions(活动会话数)
                systems.db.Idle Sessions(空闲会话数)
                systems.db.Deadlocks Trend(僵直趋势)
                systems.db.Temporary Files Trend(临时文件趋势)
                systems.db.Locks(锁数量)
                """;
        String replace = sql.replace(" ", "");
        System.out.println(replace);

    }

}


//        private String systems.db.UpdatedRowsTrend	每秒修改行数  √
//        private String systems.db.ReturnedRowsTrend	返回行数 √
//        private String systems.db.RolledBackTransactionsTrend	每秒回滚事务数 √
//        private String systems.db.CommittedTransactionsTrend	每秒提交事务数 √
//        private String systems.db.ConflictsTrend	事务冲突数
//        private String systems.db.CacheHitRate	缓存命中率 √
//        private String systems.db.ActiveSessions	活动会话数 √
//        private String systems.db.IdleSessions	空闲会话数 √
//        private String systems.db.DeadlocksTrend	僵直趋势
//        private String systems.db.TemporaryFilesTrend	临时文件趋势 √
//        private String systems.db.Locks	锁数量  √
//        private String systems.db.FetchedRowsTrend	每秒读取行数  √
//        private String systems.db.DeletedRowsTrend	每秒删除行数  √
//        private String systems.db.InsertedRowsTrend	每秒插入行数  √
