package com.manonline.examples.jndi.rmi.jndiwrapper;

import com.manonline.examples.jndi.rmi.object.RmiSampleObject;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Created by davidqi on 2/22/17.
 */
public class RmiSampleClientJndi {

    public static void main(String[] args) throws Exception {
        /**
         * 初始化JNDI上下文
         */
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        System.setProperty(Context.PROVIDER_URL, "rmi://localhost:8808");
        InitialContext ctx = new InitialContext();

        /**
         * 用JNDI的命名获取指定对象
         */
        String url = "java:comp/env/SampleDemo";
        RmiSampleObject RmiObject = (RmiSampleObject) ctx.lookup(url);

        /**
         * 调用方法
         */
        System.out.println("  1 + 2 = " + RmiObject.sum(1, 2));
    }
}