package com.manonline.examples.jdbc.datasource.dynamicproxy;

import com.manonline.examples.jdbc.datasource.staticproxy.StaticProxyConnection;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Created by davidqi on 2/15/17.
 */
public class PoolingDataSource implements DataSource {

    /**
     * 实际数据源连接参数设置
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
     * 连接池参数设置
     */
    // 初始化的连接数
    private static int initCount = 5;
    // 最大连接数
    private static int maxCount = 10;
    // 当前的连接数 ：这个程序中currentCount是用来记录有多少connection已经被创建；
    public static int currentCount = 0;

    // 可能频繁的取出连接和删除连接，所以用LinkedList
    private LinkedList<Connection> connectionsPool = new LinkedList<Connection>();

    /**
     * 构造函数
     */
    public PoolingDataSource() {
        try {
            // 初始化连接池
            for (int i = 0; i < initCount; i++) {
                this.connectionsPool.addLast(createConnection());
                currentCount++;
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 从连接池中获取链接
     */
    public Connection getConnection() throws SQLException {
        // 也有可能获取不到连接，而且这个方法也是可能被多线程访问的
        synchronized (connectionsPool) {

            // 如果有connection可用，则分发connection，并把已分发的connection从队列中去掉
            if (connectionsPool.size() > 0) {
                return this.connectionsPool.removeFirst();
            }
            // 如果还没到maxConnection，则创建connection,并记录有多少connection已经被创建
            if (currentCount < maxCount) {
                currentCount++;
                return createConnection();
            }

            // 如果没有connection，并且已经到了上限，在这里处理。可以让当前线程等待，抛出异常，返回null都是可以的，要视情况而定
            throw new SQLException("已经没有连接了");

            /**
             * 不能无限制的创建连接的，因为这样的话对数据库的压力很大，连接越多，最后数据库的运行速度就会变得很慢了（很硬件相关）
             * 如果内存够大，cpu给力的话，数据库可以建立的连接数也会增加的
             */

        }
    }

    private Connection createConnection() throws SQLException {
        /**
         * 实际创建连接的地方，打开连向数据库的一个链接
         */
        Connection realConn = DriverManager.getConnection(url);
        return new StaticProxyConnection(realConn, this);
    }

    /**
     * 释放连接
     */
    public void free(Connection conn) throws SQLException {
        /**
         * 将connection返回到队列
         */
        this.connectionsPool.addLast(conn);
    }

    /**
     * 实现父类的集成方法
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
