package com.manonline.examples.jdbc.firstdemo;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by davidqi on 2/12/17.
 */
public class JdbcUtils {

    private static String dbName = "test";
    private static String user = "root";
    private static String password = "123456";
    private static String url = "jdbc:mysql://localhost:3306/" + dbName +
            "?user=" + user +
            "&password=" + password +
            "&useUnicode=true&characterEncoding=gb2312";

    // 这个实例中并没有用到DataSource，接下来会用DataSource来代表数据源，并封装连接池；
    private static DataSource dataSource = null;

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

    private JdbcUtils() {
    }

    /**
     * 获取连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
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