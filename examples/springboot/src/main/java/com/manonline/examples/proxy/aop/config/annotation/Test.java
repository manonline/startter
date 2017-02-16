package com.manonline.examples.proxy.aop.config.annotation;

import com.manonline.examples.proxy.aop.config.App;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by davidqi on 2/16/17.
 */
public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext cx = new ClassPathXmlApplicationContext("config.xml");
        // 直接调用目标方法，其实Spring会返回代理对象；
        App app = (App) cx.getBean("app");
        app.method();
        app.add();

    }
}
