package com.io.databasesagent;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class DatabasesAgentApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testPgsql() {
        String connStr = "jdbc:postgresql://172.10.1.10:5432/postgres";
        String username = "postgres";
        String password = "darth2020";

        try (Connection connection = DriverManager.getConnection(connStr, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT 12 as item, deadlocks as count FROM pg_stat_database WHERE datname = 'postgres'\n" +
                     "union all\n" +
                     "SELECT 13 as item, deadlocks as count FROM pg_stat_database WHERE datname = 'postgres'");
             ResultSet resultSet = statement.executeQuery()) {
            List<Map<String, String>> list = new LinkedList<>();
            // 处理查询结果
            while (resultSet.next()) {
                // 假设查询结果包含一个名为 "column_name" 的列
                Map<String, String> map = new HashMap<>();
                map.put("item", resultSet.getString("item"));
                map.put("count", resultSet.getString("count"));
                list.add(map);
            }

            System.out.println("list = " + list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getVersion() {
        String version = "5.7.36";
        double i = parseVersionToNumber(version);
        System.out.println(i);
    }

    private static Double parseVersionToNumber(String versionString) {
        if (versionString == null || versionString.isEmpty()) {
            return 0.0;
        }

        // 提取主要和次要版本号
        String[] versionParts = versionString.split("\\.");
        if (versionParts.length < 2) {
            return 0.0;
        }

        try {
            int majorVersion = Integer.parseInt(versionParts[0]);
            int minorVersion = Integer.parseInt(versionParts[1]);

            // 使用主要版本和次要版本构造一个整数版本号，例如 8.0 -> 8000
            return majorVersion + minorVersion * 0.1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Test
    void testFalse() {

    }

}

