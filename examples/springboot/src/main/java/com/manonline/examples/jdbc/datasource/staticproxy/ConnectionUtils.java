package com.manonline.examples.jdbc.datasource.staticproxy;

import com.manonline.examples.jdbc.datasource.simple.PoolingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtils {
    // 此处只用自建的数据源，来管理连接；
    /**
     * 自定义的数据源是实现了JDBC中的DataSource接口的，这个接口很重要的，apache的数据源都是要实现这个接口的，这个接口统一了数据源的标准
     */
    private static PoolingDataSource dataSource = new PoolingDataSource();

    /**
     * 构造函数
     */
    private ConnectionUtils() {
    }

    /**
     * 获取连接
     */
    public static Connection getConnection() throws SQLException {
        // 通过数据源获取连接
        return dataSource.getConnection();
    }

    /**
     * 返回数据源，某些外部工具需要使用
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
                    /**
                     * 实现了connection, 不需要再dataSource.free(conn);
                     */
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