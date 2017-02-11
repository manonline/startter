package com.manonline.examples.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class JdbcUtils {

    private static String user = "root";
    private static String password = "123456";
    private static String dbName = "test";
    private static String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + user + "&password=" + password + "&useUnicode=true&characterEncoding=gb2312";

    // 此处只用自建的数据源，来管理连接；如果要使用原生态，直接声明DataSource；
    /**
     * 自定义的数据源是实现了JDBC中的DataSource接口的，这个接口很重要的，apache的数据源都是要实现这个接口的，这个接口统一了数据源的标准，
     */
    private static MyDataSource dataSource = null;

    /**
     * 加载驱动
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            /**
             * 此处使用一个自建的连接池
             */
            dataSource = new MyDataSource();
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage() + "");
            throw new ExceptionInInitializerError(e);
        }
    }

    private JdbcUtils() {
    }

    /**
     * 获取连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 释放资源
     *
     * @param rs
     * @param st
     * @param conn
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
                    /**
                     * 注意：此处如果没有使用自制的DataSource，则需要手动关闭connection对象。
                     * if (conn != null) {
                     *  conn.close();
                     * }
                     * 使用了自制的DataSource, 其实是一个连接池，则可以直接调用连接池的方法，管理连接就变成了连接池的职责。
                     */
                    dataSource.free(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}