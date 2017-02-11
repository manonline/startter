package com.manonline.examples.jdbc.datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * Created by davidqi on 2/11/17.
 */
public class MyConnectionHandler implements InvocationHandler {

    private Connection realConnection = null;
    private Connection warpedConnection = null;
    private MyDataSource2 dataSource = null;

    //当前连接的使用的次数
    private int maxUseCount = 5;
    private int currentUseCount = 0;

    public MyConnectionHandler(MyDataSource2 dataSource) {
        this.dataSource = dataSource;
    }

    public Connection bind(Connection conn) {
        this.realConnection = conn;
        warpedConnection = (Connection) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Connection.class}, this);
        return warpedConnection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("close".equals(method.getName())) {
            this.currentUseCount++;
            if (this.currentUseCount < this.maxUseCount) {
                this.dataSource.free(warpedConnection);
            } else {
                dataSource.currentCount--;
                this.realConnection.close();
            }
        }
        return method.invoke(this.realConnection, args);
    }
}