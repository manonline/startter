package com.manonline.examples.jdbc.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSourceFactory;
/**
 * Created by davidqi on 2/11/17.
 */
public class JdbcUtils {

    private static String user = "root";
    private static String password = "123456";
    private static String dbName = "test";
    private static String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + user + "&password=" + password + "&useUnicode=true&characterEncoding=gb2312";

    private static DataSource dataSource = null;

    /**
     * 加载驱动
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //dataSource = new MyDataSource();
            //dataSource = new MyDataSource2();
            //使用Apache的DBCP数据源
            Properties prop = new Properties();
            prop.load(JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties"));
            dataSource = BasicDataSourceFactory.createDataSource(prop);
            //可以查看BasicDataSourceFactory方法中的参数配置
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
        //return DriverManager.getConnection(url);
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
                        //conn.close();
                        //dataSource.free(conn);
                        //采用静态代理模式，编写了MyConnection类，在Connection的close方法中，这样我们就可以直接调用close方法
                        //调用close方法中还是对connection进行回收的
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
