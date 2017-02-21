package com.manonline.examples.jmx;

/**
 * Created by davidqi on 2/21/17.
 *
 */

import java.lang.management.ManagementFactory;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class HelloAgentWebAdaptor {
    public static void main(String[] args) throws JMException, Exception {
        /**
         * 给MBean指定名称，并置于容器中
         */
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName helloName = new ObjectName("jmxBean:name=hello");
        server.registerMBean(new Hello(), helloName);

        /**
         * 生成WebAdaptor，本身也是一个MBean。并指定名称，置于容器中。
         */
        ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        server.registerMBean(adapter, adapterName);
        adapter.start();
    }
}