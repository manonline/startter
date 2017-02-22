package com.manonline.examples.jndi.rmi.jndiwrapper;

import com.manonline.examples.jndi.rmi.object.impl.RmiSampleObjectImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by davidqi on 2/22/17.
 */
public class RmiSampleServerJndi {

    public static void main(String[] args) throws Exception {

        startRmiServer();

        /**
         * 连接到RMI服务
         */
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        System.setProperty(Context.PROVIDER_URL, "rmi://localhost:8808");
        InitialContext ctx = new InitialContext();

        /**
         * 在JNDI的上下文中添加对象。也即将服务端接口实现对象与JNDI命名绑定，可以任意命名，但在J2EE开发中规范的写法是，
         * 绑定的名字要以java:comp/env/开头。但是此写法一般是用在JNDI容器中。
         */
        RmiSampleObjectImpl server = new RmiSampleObjectImpl();
        ctx.bind("java:comp/env/SampleDemo", server);

        System.out.println("RMI与JNDI集成服务启动.等待客户端调用...");
    }

    private static void startRmiServer() {
        /**
         * 启动RMI服务
         */
        try {
            LocateRegistry.createRegistry(8808);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}