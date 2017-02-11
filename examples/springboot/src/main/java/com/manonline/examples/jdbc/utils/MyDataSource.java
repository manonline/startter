package com.manonline.examples.jdbc.utils;

/**
 * Created by davidqi on 2/11/17.
 */

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * 大部分时间都是浪费在数据库连接这一块
 * 这个类是我们自己编写的一个数据源
 *
 * @author weijiang204321
 */
public class MyDataSource implements DataSource {

    private static String user = "root";
    private static String password = "123456";
    private static String dbName = "test";
    private static String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + user + "&password=" + password + "&useUnicode=true&characterEncoding=gb2312";

    // 初始化的连接数
    private static int initCount = 5;
    // 最大连接数
    private static int maxCount = 10;
    // 当前的连接数 ：这个程序中currentCount是用来记录有多少connection已经被创建；
    private static int currentCount = 0;

    // 可能频繁的取出连接和删除连接，所以用LinkedList
    private LinkedList<Connection> connectionsPool = new LinkedList<Connection>();

    public MyDataSource() {
        try {
            for (int i = 0; i < initCount; i++) {
                this.connectionsPool.addLast(createConnection());
                currentCount++;
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public Connection getConnection() throws SQLException {
        // 也有可能获取不到连接，而且这个方法也是可能被多线程访问的
        synchronized (connectionsPool) {
            /**
             * 如果有connection可用，则分发connection，并把已分发的connection从队列中去掉
             */
            if (connectionsPool.size() > 0) {
                return this.connectionsPool.removeFirst();
            }
            /**
             * 如果还没到maxConnection，则创建connection,并记录有多少connection已经被创建
             */
            if (currentCount < maxCount) {
                currentCount++;
                return createConnection();
            }

            /**
             * 如果没有connection，并且已经到了上限，在这里处理
             * 可以让当前线程等待，抛出异常，返回null都是可以的，要视情况而定
             */
            throw new SQLException("已经没有连接了");

            /**
             * 不能无限制的创建连接的，因为这样的话对数据库的压力很大，连接越多，最后数据库的运行速度就会变得很慢了（很硬件相关）
             * 如果内存够大，cpu给力的话，数据库可以建立的连接数也会增加的
             */

        }
    }

    public void free(Connection conn) throws SQLException {
        /**
         * 将connection返回到队列
         */
        this.connectionsPool.addLast(conn);
    }

    private Connection createConnection() throws SQLException {
        /**
         * 实际创建连接的地方，打开连向数据库的一个链接
         */

        return DriverManager.getConnection(url);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public void setLogWriter(PrintWriter arg0) throws SQLException {

    }

    public void setLoginTimeout(int arg0) throws SQLException {

    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}