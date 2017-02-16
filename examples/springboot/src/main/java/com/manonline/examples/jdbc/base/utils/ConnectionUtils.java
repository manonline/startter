package com.manonline.examples.jdbc.base.utils;

import javax.sql.DataSource;
import java.sql.*;

public class ConnectionUtils {

    /**
     * 数据源，在这个例子中不需要
     */
    private static DataSource dataSource = null;

    /**
     * 连接参数
     */
    private static String user = "root";
    private static String password = "123456";
    private static String dbName = "test";
    private static String url = "jdbc:mysql://localhost:3306/" + dbName + "?" +
            "user=" + user + "&" +
            "password=" + password + "&" +
            "useUnicode=true" + "&" +
            "characterEncoding=gb2312";

    /**
     * 加载驱动
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage() + "");
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 构造函数
     */
    private ConnectionUtils() {
    }

    /**
     * 获取连接
     */
    public static Connection getConnection() throws SQLException {
        // 通过DriverManager直连数据源，获取连接
        return DriverManager.getConnection(url);
    }

    /**
     * 返回数据源供第三方使用
     */
    public static DataSource getDataSource() throws SQLException {
        return dataSource;
    }

    /**
     * 释放资源
     */
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}