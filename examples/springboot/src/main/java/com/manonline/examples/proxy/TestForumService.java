package com.manonline.examples.proxy;

import com.manonline.examples.proxy.cglibproxy.PerformanceProxy;
import com.manonline.examples.proxy.jdkproxy.PerformanceHandler;

import java.lang.reflect.Proxy;

/**
 * Created by davidqi on 2/13/17.
 * http://www.iteye.com/topic/1123081
 */
public class TestForumService {
    public static void main(String[] args) {

        /**
         * Directly Add-In - hardcode
         */
        ForumService monitoredForumService = new MonitoredForumServiceImpl();
        monitoredForumService.removeForum(10);
        monitoredForumService.removeTopic(1012);

        /**
         * Static Proxy
         */
        ForumService forumService = new RawForumServiceImpl();
        forumService.removeForum(10);
        forumService.removeTopic(1012);

        ForumService staticProxyForumService = new StaticProxyForumServiceImpl(forumService);
        staticProxyForumService.removeForum(10);
        staticProxyForumService.removeTopic(1012);

        /**
         * Dynamic Proxy - JDK；基于接口的代理实例，也即只能创建接口实例的代理对象，看第二个注释；
         */
        // 将业务和非业务编织在一起
        PerformanceHandler handler = new PerformanceHandler(forumService);

        // 创建一个实现了被代理类接口的Proxy对象，并将handler置于这个对象內，通过指定的ClassLoader去加载。
        ForumService jdkProxyForumService = (ForumService) Proxy.newProxyInstance(forumService.getClass().getClassLoader(),
                forumService.getClass().getInterfaces(), handler);

        // 通过Proxy调用
        jdkProxyForumService.removeForum(10);
        jdkProxyForumService.removeTopic(1012);

        /**
         * Dynamic Proxy - CGlib; 基于底层字节码技术实现子类，并用子类代理对父类的调用；
         */
        // 创建一个Proxy
        PerformanceProxy proxy = new PerformanceProxy();

        // 通过Proxy返回子类对象
        RawForumServiceImpl cglibProxyForumService = (RawForumServiceImpl) proxy.getProxy(RawForumServiceImpl.class);

        // 调用子类方法（切面已经在子类中织入)
        cglibProxyForumService.removeForum(10);
        cglibProxyForumService.removeTopic(1023);

    }
}