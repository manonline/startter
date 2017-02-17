package com.manonline.examples.proxy.configsample.classic;

import com.manonline.examples.proxy.configsample.IApp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by davidqi on 2/16/17.
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext cx = new ClassPathXmlApplicationContext("config.xml");
        // 通过代理去调用
        IApp app = (IApp) cx.getBean("proxyFactoryBean");
        app.method();
        app.add();
    }
}
