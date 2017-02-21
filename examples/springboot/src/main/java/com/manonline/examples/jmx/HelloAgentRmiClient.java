package com.manonline.examples.jmx;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

/**
 * Created by davidqi on 2/21/17.
 */
public class HelloAgentRmiClient {

    public static void main(String[] args) throws IOException, Exception, NullPointerException {
        /**
         * 指定连接字符串
         */
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        /**
         * 建立连接
         */
        MBeanServerConnection conn = jmxc.getMBeanServerConnection();

        /**
         * 显示服务器端设置
         */
        System.out.println("Domains ......");
        String[] domains = conn.getDomains();

        for (int i = 0; i < domains.length; i++) {
            System.out.println("doumain[" + i + "]=" + domains[i]);
        }

        System.out.println("MBean count = " + conn.getMBeanCount());

        /**
         * 操作MBean的属性 ：
         * 设置指定Mbean的特定属性值。这里的setAttribute、getAttribute操作只能针对bean的属性；例如对getName或者setName进行操作，
         * 只能使用Name，需要去除方法的前缀
         */
        //ObjectName的名称与前面注册时候的保持一致
        ObjectName mbeanName = new ObjectName("jmxBean:name=hello");

        conn.setAttribute(mbeanName, new Attribute("Name", "杭州"));
        conn.setAttribute(mbeanName, new Attribute("Age", "1990"));
        String age = (String) conn.getAttribute(mbeanName, "Age");
        String name = (String) conn.getAttribute(mbeanName, "Name");
        System.out.println("age=" + age + ";name=" + name);

        /**
         * 调用MBean的方法 ：方法一， 通过conn.invoke来调用
         * invoke调用bean的方法，只针对非设置属性的方法；例如invoke不能对getName方法进行调用
         */
        conn.invoke(mbeanName, "getTelephone", null, null);
        conn.invoke(mbeanName, "helloWorld", new String[]{"I'll connect to JMX Server via client2"}, new String[]{"java.lang.String"});
        conn.invoke(mbeanName, "helloWorld", null, null);

        /**
         * 调用MBean的方法 ：方法二，通过Proxy来调用
         */
        HelloMBean proxy = MBeanServerInvocationHandler.newProxyInstance(conn, mbeanName, HelloMBean.class, false);
        proxy.helloWorld();
        proxy.helloWorld("migu");
        proxy.getTelephone();
    }

}