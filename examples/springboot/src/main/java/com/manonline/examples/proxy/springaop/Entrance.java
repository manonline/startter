package com.manonline.examples.proxy.springaop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by davidqi on 2/14/17.
 */
public class Entrance {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        Echo echo = (Echo) ctx.getBean("serviceToCglib");
        /**
         * 输出的是代理类对象Echo$$EnhancerByCGLIB$$bb9b6a7c.而且它的父类是我们的代理目标类(Echo)。说明是有CGLIB生成的
         */
        System.out.println(echo.getClass().getSimpleName());
        echo.say();

        /**
         * 输出的是$Proxy0。当拦截对象实现了接口时，生成方式是用JDK的Proxy类。当没有实现任何接口时用的是GCLIB开源项目生成的拦截类的子类.
         */
        Yell yell = (Yell) ctx.getBean("serviceToJdk");
        System.out.println(yell.getClass().getSimpleName());
        yell.say();
    }
}
