package com.manonline.examples.jmx;

/**
 * Created by davidqi on 2/21/17.
 * 可以通过JConsole : C:\Program Files (x86)\Java\jdk1.6.0_43\bin找到jconsole.exe来查看Bean的状态（属性），并调用方法
 */

import java.lang.management.ManagementFactory;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class HelloAgent {
    public static void main(String[] args) throws JMException, Exception {
        /**
         * 获取MBean的容器
         */
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        /**
         * 定义一个MBean名称，后面将会用这个名称来获取实现类;格式 (域名 : name={MBean名称})。
         * 域名和MBean名称可以随便取。
         */
        ObjectName helloName = new ObjectName("jmxBean:name=hello");
        /**
         * 注册MBean。并指定名称
         */
        server.registerMBean(new Hello(), helloName);
        Thread.sleep(60 * 60 * 1000);
    }
}