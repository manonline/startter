package com.manonline.examples.jmx.notification;

import com.manonline.examples.jmx.notification.beans.impl.Hello;
import com.manonline.examples.jmx.notification.beans.impl.Jack;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Created by davidqi on 2/21/17.
 * 1. Notification这个相当于一个信息包，封装了需要传递的信息
 * 2. Notification broadcaster这个相当于一个广播器，把消息广播出。
 * 3. Notification listener 这是一个监听器，用于监听广播出来的通知信息。
 * 4. Notification filter 这个一个过滤器，过滤掉不需要的通知。这个一般很少使用。
 * 这里我们使用日常打招呼的场景：jack与我偶遇，jack说：hi；我礼貌的回答：hello，jack。
 */
public class Test {
    public static void main(String[] args) throws JMException, Exception {
        /**
         * 开启MBean服务器
         */
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        /**
         * 创建并注册MBean
         */
        Hello hello = new Hello();
        server.registerMBean(hello, new ObjectName("yunge:name=Hello"));

        Jack jack = new Jack();
        server.registerMBean(jack, new ObjectName("jack:name=Jack"));

        /**
         * 注册监听器
         */
        jack.addNotificationListener(new HelloListener(), null, hello);
        Thread.sleep(500000);
    }
}