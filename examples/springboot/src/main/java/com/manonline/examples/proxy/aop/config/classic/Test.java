package com.manonline.examples.proxy.aop.config.classic;

import com.manonline.examples.proxy.aop.config.IApp;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by davidqi on 2/16/17.
 */
public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext cx = new ClassPathXmlApplicationContext("config.xml");
        // 通过代理去调用
        IApp app = (IApp) cx.getBean("proxyFactoryBean");
        app.method();
        app.add();

    }
}
