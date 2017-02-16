package com.manonline.examples.jdbc.datasource.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * Created by davidqi on 2/11/17.
 */
public class MyConnectionHandler implements InvocationHandler {

    private Connection realConnection = null;
    private Connection wrapedConnection = null;
    private PoolingDataSource dataSource = null;

    //当前连接的使用的次数
    private int maxUseCount = 5;
    private int currentUseCount = 0;

    public MyConnectionHandler(PoolingDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection bind(Connection conn) {
        this.realConnection = conn;
        wrapedConnection = (Connection) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Connection.class}, this);
        return wrapedConnection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("close".equals(method.getName())) {
            this.currentUseCount++;
            if (this.currentUseCount < this.maxUseCount) {
                this.dataSource.free(wrapedConnection);
            } else {
                dataSource.currentCount--;
                this.realConnection.close();
            }
        }
        return method.invoke(this.realConnection, args);
    }
}