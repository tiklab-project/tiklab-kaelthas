package io.thoughtware.kaelthas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJDBCSQL {
    public static void main(String[] args) {
        // 数据库连接参数
        String url = "jdbc:postgresql://172.10.1.10:5432/xmonitor_test5";
        String user = "postgres";
        String password = "darth2020";

        Connection connection = null;

        try {
            // 加载JDBC驱动（可选，依赖于驱动实现）
            Class.forName("org.postgresql.Driver"); // 替换为实际的JDBC驱动类名

            // 尝试建立连接
            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("连接成功！");
            } else {
                System.out.println("连接失败！");
            }
        } catch (SQLException e) {
            System.out.println("连接失败，SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("找不到JDBC驱动类: " + e.getMessage());
        } finally {
            // 关闭连接
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
