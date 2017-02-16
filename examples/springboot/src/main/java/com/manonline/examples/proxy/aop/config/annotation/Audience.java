package com.manonline.examples.proxy.aop.config.annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


/**
 * Created by davidqi on 2/16/17.
 * 一般框架里面提供给我们的注解  框架本身会提供解析的程序。配置文件中<aop:aspectj-autoproxy/>
 * 是由AspectJAutoProxyBeanDefinitionParser解析。可以看出它是属于spring框架里面的。也就是说我们只是用了aspect的表达式而已，
 * 底层并没有使用aspect的技术，只用了aspcet的注解本身,注解本身是aspectj项目的
 */
@Aspect
public class Audience {
    // 定义pointcut
    public static final String method = "execution(* com.manonline.examples.proxy.aop.config.App.add(..))";

    @Before(method)
    public void takeSeats() {
        System.out.println("The audience is taking their seats.");
    }
    @Before(method)
    public void turnOffCellphone() {
        System.out.println("The audience is turning off their cellphones");
    }
    @After(method)
    public void applaud() {
        System.out.println("CLAP CLAP CLAP CLAP CLAP");
    }

    public void demandRefund() {
        System.out.println("Boo! we want our money back!");
    }
}
